package com.example.justnews.api

import com.example.justnews.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Retrofit singleton class that enables me to make requests from everywhere in my code

class RetrofitInstance {

    companion object{
        //  lazy means that i initialize this affected by curly brackets only once
        private val retrofit by lazy {
        // for logging responses of retrofit, very usefull for debuging
        // Attaching this to a retrofit object to be able to see with requests i am actualy making and what the responses are
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        //Logging interceptor that i defined i now need to pass on to a client that i will in next line pass to a Retrofit instance
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        // Passing it on to a Retrofit instance
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        // Now i am finally getting that API instance from retrofit builder. API instance i got as a interface NewsAPI
        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}