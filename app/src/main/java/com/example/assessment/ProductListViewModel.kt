package com.example.assessment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.assessment.retrofit.ProductRepository
import com.example.assessment.retrofit.Resource

class ProductListViewModel (private val mProductRepository: ProductRepository): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is product list Fragment"
    }
    val text: LiveData<String> = _text

    fun getAllProduct() = liveData {
        emit(Resource.loading(null))
        try{
            emit(Resource.success(mProductRepository.getProductList()))
        } catch (e:Exception){
            emit(Resource.error(null,e.message.toString()))
        }
    }
}
