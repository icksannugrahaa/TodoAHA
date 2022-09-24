package com.icksan.todoaha.core.data

import com.icksan.todoaha.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class LocalOnlyBoundResources<ResultType, RequestType> {
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        emitAll(loadFromDB().map { Resource.Success(it, "true") })
    }
    protected abstract fun loadFromDB(): Flow<ResultType>
    fun asFlow(): Flow<Resource<ResultType>> = result
}