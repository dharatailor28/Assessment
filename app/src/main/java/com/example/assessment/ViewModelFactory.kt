package com.example.assessment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assessment.retrofit.ApiHelper
import com.example.assessment.retrofit.ProductRepository

@Suppress("UNCHECKED_CAST") class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductListViewModel::class.java)){
            return ProductListViewModel(ProductRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}
