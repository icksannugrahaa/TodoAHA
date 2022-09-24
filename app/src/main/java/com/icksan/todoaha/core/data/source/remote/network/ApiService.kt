package com.icksan.todoaha.core.data.source.remote.network

import com.icksan.todoaha.core.data.source.remote.response.DirectionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("https://maps.googleapis.com/maps/api/directions/json")
    suspend fun getDirection(@Query("origin") origin: String, @Query("destination") destination: String, @Query("mode") mode: String, @Query("key") key: String): DirectionResponse
}