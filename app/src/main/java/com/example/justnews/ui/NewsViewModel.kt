package com.example.justnews.ui

import androidx.lifecycle.ViewModel
import com.example.justnews.repository.NewsRepository

class NewsViewModel(
    val newsRepository : NewsRepository
) : ViewModel() {

}