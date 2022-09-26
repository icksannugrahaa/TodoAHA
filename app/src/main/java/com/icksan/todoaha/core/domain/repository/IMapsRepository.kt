package com.icksan.todoaha.core.domain.repository

import com.icksan.todoaha.core.data.Resource
import com.icksan.todoaha.core.domain.model.Direction
import kotlinx.coroutines.flow.Flow

interface IMapsRepository {
    fun getDirection(origin: String, destination: String, mode:String, key:String): Flow<Resource<Direction>>
}