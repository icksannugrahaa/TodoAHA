package com.icksan.todoaha.core.domain.usecase

import com.icksan.todoaha.core.data.Resource
import com.icksan.todoaha.core.domain.model.Direction
import com.icksan.todoaha.core.domain.repository.IMapsRepository
import kotlinx.coroutines.flow.Flow

class MapsIterator(private val repository: IMapsRepository) : MapsUseCase {
    override fun getDirection(origin: String, destination: String, mode:String, key:String): Flow<Resource<Direction>> = repository.getDirection(origin, destination, mode, key)
}