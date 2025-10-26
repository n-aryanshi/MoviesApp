package com.example.moviesapp.network

import com.example.moviesapp.model.remote.Details
import com.example.moviesapp.view.Shows
import com.example.moviesapp.model.remote.TitlesResponse
import com.example.moviesapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("list-titles/")

    suspend fun  getTitles(
        @Query("apiKey") apiKey:String = API_KEY,
        @Query("limit") limit: Int = 20,
        @Query("types") types: String? = null  // movie,tv_series
    ): Response<TitlesResponse>

    @GET("title/{id}/details/")
    suspend fun getTitleDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<Details>

}
