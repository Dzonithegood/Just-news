package com.example.justnews.api

import com.example.justnews.NewsResponse
import com.example.justnews.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey:String = API_KEY

    ) : Response<NewsResponse>
    //NewsResponse was generated from Json

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