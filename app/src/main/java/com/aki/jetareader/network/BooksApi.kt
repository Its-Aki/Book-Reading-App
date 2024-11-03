package com.aki.jetareader.network

import com.aki.jetareader.model.Book
import com.aki.jetareader.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {
    @GET("volumes")
    suspend fun getAllBooks(@Query(value = "q") query:String):Book

    @GET(value = "volumes/{bookId}")
    suspend fun getBookInfo(@Path(value ="bookId") bookId:String):Item

    
}