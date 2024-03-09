package com.cs4520.assignment4

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object Api {
    private const val BASE_URL: String = "https://kgtttq6tg9.execute-api.us-east-2.amazonaws.com/"
    private const val ENDPOINT: String = "prod/"

    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    interface ProductService {
        @GET(ENDPOINT)
        suspend fun getProducts(): Response<List<Product>>
    }
}

