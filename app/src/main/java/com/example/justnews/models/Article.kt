package com.example.justnews.models


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles"
)
data class Article(
    //We dont need to worry about the implementation of id if we set autoGenerate to true, it will tell room that it should automaticaly generate them
    @PrimaryKey(autoGenerate = true )
    var id: Int? = null,

    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)