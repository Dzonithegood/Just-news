package com.example.justnews.db

import android.content.Context
import androidx.room.*
import com.example.justnews.models.Article

//Data classes for Room always have to be abstract, and we annotate it with database to tell room that this is our database class.
@Database(
    //we only have one entity because we have one single table ( article table ), also define a version, is used to update our database later on.
    entities = [Article::class],
    version = 1
)
    //Here is how to tell my database to use converters of data that i defined in Converters class
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    //we need to create a function that returns an article Dao
    abstract fun getArticleDao(): ArticleDao

    // to be able to create database, i need a companion object here, volatile annotation means that other threats can instantly see when a threat changes this instance
    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null

        // Will use this to sync our instance, to be sure that we have a single instance at once
        private val LOCK = Any()
        // in a sync block we create an instance and if it is null i call createDatabase function and set instance to the result of createDatabase
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
        //Instance of this database class will then be used to access getArticleDao, witch is used to access actual database functions
    }
}