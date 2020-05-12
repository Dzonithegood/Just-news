package com.example.justnews.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justnews.models.NewsResponse
import com.example.justnews.repository.NewsRepository
import com.example.justnews.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    //Breaking page needs to be defined in the view model because if i set this in the fragment it will be resseted everytime configuration ( rotation ) changes, and view model survives these changes.
    val breakingNewsPage = 1

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
        //Now I can make a actual response, this is a suspend function i can see that because i have an arrow on the left next to the 30,
        // and when that function is finished the coroutine will just continue to the next line.
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        //Here i post respond success or error state
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
//This function will decide whether or not we want to emit the success state or the error state of the breakingNewsData.
    private fun handleBreakingNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let{ resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
    return Resource.Error(response.message())
    }

}