package com.example.justnews.repository

import com.example.justnews.api.RetrofitInstance
import com.example.justnews.db.ArticleDatabase
import com.example.justnews.models.Article

class NewsRepository (
    val db : ArticleDatabase
    ){
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
    RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

  //add all functions like in ArticleDao, then we go to NewsViewModel and add functions there.

    suspend fun upsert(article : Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)


}