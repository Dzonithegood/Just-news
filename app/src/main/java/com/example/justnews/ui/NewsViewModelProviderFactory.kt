package com.example.justnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.justnews.repository.NewsRepository

class NewsViewModelProviderFactory (
    val newsRepository: NewsRepository
): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return NewsViewModel(newsRepository) as T
    }
}