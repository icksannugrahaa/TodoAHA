package com.icksan.todoaha.core.data.source.remote

import com.icksan.todoaha.core.data.source.remote.network.ApiResponse
import com.icksan.todoaha.core.data.source.remote.network.ApiService
import com.icksan.todoaha.core.data.source.remote.response.DirectionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getDirection(origin: String, destination: String, mode:String, key:String): Flow<ApiResponse<DirectionResponse>> =
        flow {
            try {
                val response = apiService.getDirection(origin, destination, mode, key)
                if (response.status == "OK") {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error("Internal Server Error"))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
}