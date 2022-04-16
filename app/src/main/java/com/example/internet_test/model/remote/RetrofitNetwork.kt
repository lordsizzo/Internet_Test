package com.example.internet_test.model.remote

import com.example.internet_test.model.BooksResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface BooksApi {
    @GET(END_POINT)
    fun findBooksByTitle(
        @Query(PARAM_Q) bookTitle: String,
        @Query(PARAM_RESULT) maxResults: Int,
        @Query(PARAM_PRINT_TYPE) printType: String,
    ): Call <BooksResponse>

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/"
        private const val END_POINT = "books/v1/volumes"
        private const val PARAM_Q = "q"
        private const val PARAM_RESULT = "maxResults"
        private const val PARAM_PRINT_TYPE = "printType"


        fun initRetrofit(): BooksApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(BooksApi::class.java)
        }
    }
}