package com.icksan.todoaha.core.data.repository

import com.icksan.todoaha.core.data.NetworkOnlyResource
import com.icksan.todoaha.core.data.Resource
import com.icksan.todoaha.core.data.source.remote.RemoteDataSource
import com.icksan.todoaha.core.data.source.remote.network.ApiResponse
import com.icksan.todoaha.core.data.source.remote.response.DirectionResponse
import com.icksan.todoaha.core.domain.model.Direction
import com.icksan.todoaha.core.domain.repository.IMapsRepository
import com.icksan.todoaha.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MapsRepository(
    private val remoteDataSource: RemoteDataSource
) : IMapsRepository {
    override fun getDirection(origin: String, destination: String, mode:String, key:String): Flow<Resource<Direction>> =
        object: NetworkOnlyResource<Direction, DirectionResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<DirectionResponse>> = remoteDataSource.getDirection(origin, destination, mode, key)
            override fun transformData(param: DirectionResponse): Flow<Resource<Direction>> = flow {
                emit(Resource.Success(DataMapper.mapDirectionResponsesToDomain(param), param.status.toString()))
            }
        }.asFlow()
}