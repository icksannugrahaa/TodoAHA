package com.icksan.todoaha.main.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.icksan.todoaha.core.data.Resource
import com.icksan.todoaha.core.domain.model.Direction
import com.icksan.todoaha.core.domain.usecase.MapsUseCase

class MapsViewModel(private val useCase: MapsUseCase) : ViewModel() {
    fun getDirection(origin: String, destination: String, mode:String, key:String): LiveData<Resource<Direction>> = useCase.getDirection(origin, destination, mode, key).asLiveData()
}