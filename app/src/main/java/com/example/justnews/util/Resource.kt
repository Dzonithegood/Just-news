package com.example.justnews.util

//Class that will be used as a wrapper around our network responses, this is Google advised, and it is very useful to differentiate between successful and error responses,
//also helps us to handle the loading states, so when we make a response that we can show on a progress bar while that response is processing and when we get the answer then
//we can use that recourse class to tell us whether that answer was successful or an error and depending on that we can handle that error or show that successful response.
//This will be a generic class and it will have a generic type T

//This will also be a sealed class, similar to abstract class, but with this we can define witch classes can inherit from that resource.
// Here i will define 3 diff classes and only those are allowed to inherit from resource
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data : T) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    class Loading<T> : Resource<T> ()

}