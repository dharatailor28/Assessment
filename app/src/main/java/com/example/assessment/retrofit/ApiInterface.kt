package com.example.assessment.retrofit
import com.example.assessment.Response
import retrofit2.http.GET


interface ApiInterface {
    @GET("/v3/2f06b453-8375-43cf-861a-06e95a951328")
    suspend fun getAllResult(): Response
}
