package com.example.assessment.retrofit

class ProductRepository(private val apiHelper: ApiHelper) {

    suspend fun getProductList() = apiHelper.getAllResult()

}
