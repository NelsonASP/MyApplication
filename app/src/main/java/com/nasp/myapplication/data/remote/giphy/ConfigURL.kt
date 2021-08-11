package com.nasp.myapplication.data.remote.giphy

import com.nasp.myapplication.data.remote.modelData.GifResult
import com.nasp.myapplication.utils.ApiKey

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigURL {

    @GET("v1/gifs/search") //https://api.giphy.com/v1/gifs/search
    suspend fun search(
        @Query("api_key") apiKey: String = ApiKey.KEY,
        @Query("q") query: String?,
        @Query("limit") limit: String = LIMIT_GIF,
        @Query("rating") rating: String = GENERAL
    ): Response<GifResult>

    @GET("v1/gifs/trending") //https://api.giphy.com/v1/gifs/random
    suspend fun getTrending(
        @Query("api_key") apiKey: String = ApiKey.KEY,
        @Query("limit") limit: String = LIMIT_GIF,
        @Query("rating") rating: String = GENERAL
    ): Response<GifResult>

    companion object {
        const val LIMIT_GIF = "32" //Limit Gif
        const val GENERAL = "g" //general audiences
    }

}