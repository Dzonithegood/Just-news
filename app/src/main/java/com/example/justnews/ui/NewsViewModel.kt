package com.example.justnews.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justnews.models.Article
import com.example.justnews.models.NewsResponse
import com.example.justnews.repository.NewsRepository
import com.example.justnews.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    //We cannot use constructor parameters by default for our view models, if we want to do that, here we need that because we need our newsrepository in our view model then
    //we need to create a view model provider factory how our own view model should be created.
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    //Breaking page needs to be defined in the view model because if i set this in the fragment it will be resseted everytime configuration ( rotation ) changes, and view model survives these changes.
    var breakingNewsPage = 1
    //////
    var breakingNewsResponse : NewsResponse?= null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    //////
    var searchNewsResponse : NewsResponse?= null

    init {
        getBreakingNews("us")
    }

    //Function that executes Api call from the repository,because it is a suspend fun in the repository we need to start a Coroutine here.
// viewModelScope makes sure that this stays alive as long the viewModel is alive.
// That is something that i should always use in a viewModel in general.
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {

        //Before making the network call i want to emit the loading state to live data,
        // because we are about to make the network call so we should emit that loading state so our fragment can handle that.
        breakingNews.postValue(Resource.Loading())
        //Now I can make a actual response, this is a suspend function i can see that because i have an arrow on the left next to the 44,
        // and when that function is finished the coroutine will just continue to the next line.
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        //Here i post respond success or error state
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

//This function will decide whether or not we want to emit the success state or the error state of the breakingNewsData.
    private fun handleBreakingNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let{ resultResponse ->
     //Now we are modifying this response so that the first thing we do when we get this we want to increase the response, increasing the page number by one every time we get a response
                breakingNewsPage++
     //Because it is set to null initially we have to check this, if it is our first response ever then we will set our breaking news response to a result response
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
     //If we loaded more than one page already then we simply want to get more articles
                } else {
                    //because initially we have set article list to be just a list we cannot add new articles, but we can change this when we go back in newsResponse and set it to mutable list
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }

                return Resource.Success(breakingNewsResponse?: resultResponse)
            }
        }
    return Resource.Error(response.message())
    }
//Copied the whole function from private fun handleBreakingNewsResponse
    private fun handleSearchNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let{ resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

//Adding functions here so that fragments can actually call these. So simply we took functions from article dao and implemented them in news repository and news viewModel

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}