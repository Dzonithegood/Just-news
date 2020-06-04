package com.example.justnews.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable



@Entity(
    tableName = "articles"
)
data class Article(
    //We dont need to worry about the implementation of id if we set autoGenerate to true, it will tell room that it should automaticaly generate them
    @PrimaryKey(autoGenerate = true )
    var id: Int? = null,

    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable
//then we need to mark that class inherit  as serializable, witch then tells kotlin that we want to be able to pass this class between several fragments with the navigation components.
//after this we have to declare this article in the nav graph ( res folder ), click on article fragment and there is a tab arguments. we add argument, give it a name and define a type serializable,
// then this class will appear
//because we have previously defined it here as serializable.
