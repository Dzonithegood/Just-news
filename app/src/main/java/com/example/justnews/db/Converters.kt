package com.example.justnews.db

import androidx.room.TypeConverter
import com.example.justnews.models.Source


//Here we are defining how to handle different data types, because inside our article class we have int, string, and here we have a specific source class that has its own two variables inside.
class Converters {
    //To tell room that this is a converter function we need to annotate this with TypeConverters
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String) : Source {
        return Source(name, name)
    }

    //Now i need to go and define in my database that i want to add these type converters to that

}