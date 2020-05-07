package com.example.justnews.models


import com.example.justnews.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)