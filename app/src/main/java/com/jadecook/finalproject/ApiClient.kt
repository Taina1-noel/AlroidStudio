package com.jadecook.finalproject

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Quote(
    val text: String?,
    val author: String?
)

interface QuoteApi {
    @GET("api/quotes")
    suspend fun getQuotes(): List<Quote>
}

object ApiClient {
    private const val BASE_URL = "https://type.fit/"

    val apiService: QuoteApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteApi::class.java)
    }
}
