package com.example.justnews.repository

import com.example.justnews.api.RetrofitInstance
import com.example.justnews.db.ArticleDatabase

class NewsRepository (
    val db : ArticleDatabase
){
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
    RetrofitInstance.api.searchForNews(searchQuery, pageNumber)
}