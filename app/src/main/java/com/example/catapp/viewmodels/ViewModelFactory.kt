package com.example.catapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catapp.repositories.CommentRepository

class ViewModelFactory(private val repository: CommentRepository, context: Context) :
    ViewModelProvider.Factory {

    val cont = context

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository, cont) as T
    }

}