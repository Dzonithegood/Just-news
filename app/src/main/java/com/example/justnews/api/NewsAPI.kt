package com.example.justnews.api

import com.example.justnews.models.NewsResponse
import com.example.justnews.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
//This defines a single request that we can execute from code. First function of this interface will be used to get all the breaking news from our API. Whenever ew make an Http request we
// we need to specify the type of that request. We want to get data from our Api so here we need a GET request.
    @GET("v2/top-headlines")
    // Now we need a function that gets our breaking news. Because this is a network call function, we have to execute that function asynchronously and the best way to do that is using coroutines.
    //To be able to make that function to be used in a coroutine we need to make it a suspend function.
    suspend fun getBreakingNews(
    //We need some parameters to this function. If that is a request parameter we need to anotate that perimeter with @Query. Which parameters we need is defined within user info about API that we are using.
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey:String = API_KEY

    ) : Response<NewsResponse>
    //NewsResponse was generated from , we need to have a response in that form
//Now we want to make a search request , then change the value for everything so that if we search the news we search for all news.
    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey:String = API_KEY

    ) : Response<NewsResponse>

}