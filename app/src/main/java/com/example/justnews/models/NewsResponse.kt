package com.example.justnews.models


import com.example.justnews.models.Article

data class NewsResponse(
    //changed this to mutable list so we are able to add articles to that
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)