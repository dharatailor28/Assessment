package com.example.assessment.retrofit


class ApiHelper (private val apiInterface: ApiInterface) {
    suspend fun getAllResult() = apiInterface.getAllResult()
}
