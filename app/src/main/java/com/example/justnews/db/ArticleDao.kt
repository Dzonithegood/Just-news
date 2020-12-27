package com.example.justnews.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.justnews.models.Article
//We have to anotate this interface with Dao so that Room knows that these are all our functions are defined within this interface.
@Dao
interface ArticleDao {

//Onconflict solves the situation of if the article that we want to insert in the database already exists in the database, and in that case we want to replace that article
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun upsert(article: Article):Long
//here we cannot use suspend fun because we are passing livedata, and it is not working with suspend. Now whenever data inside Article changes, LiveData will notify all of its observers for that changes.
@Query("SELECT * FROM articles" )
fun getAllArticles():LiveData<List<Article>>
//delete function doesnt return anything, only need to pass on the article that i want to delete
@Delete
suspend fun deleteArticle(article: Article)
}