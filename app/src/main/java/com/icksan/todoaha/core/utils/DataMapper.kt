package com.icksan.todoaha.core.utils

import com.icksan.todoaha.core.data.source.local.entitiy.TodoEntity
import com.icksan.todoaha.core.data.source.remote.response.*
import com.icksan.todoaha.core.domain.model.Direction
import com.icksan.todoaha.core.domain.model.Todo

object DataMapper {
    fun mapTodoToEntity(data: Todo) = TodoEntity(
        id = data.id,
        title = data.title,
        description = data.description,
        createdAt = data.createdAt,
        updatedAt = data.updatedAt
    )

    fun mapTodoToListModel(data: List<TodoEntity>): List<Todo> =
        data.map {
            Todo(
                id = it.id,
                title = it.title,
                description = it.description,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }


    fun mapDirectionResponsesToDomain(param: DirectionResponse): Direction = Direction(
        directionRouteModels = mapDirectionRouteResponsesToDomain(param.directionRouteModels),
        status = param.status
    )

    private fun mapDirectionRouteResponsesToDomain(directionRouteModels: List<DirectionRouteModel>?): List<com.icksan.todoaha.core.domain.model.DirectionRouteModel>? =
        directionRouteModels?.map {
            com.icksan.todoaha.core.domain.model.DirectionRouteModel(
                legs = mapDirectionLegsResponsesToDomain(it.legs),
                polylineModel = mapDirectionPolyLineResponsesToDomain(it.polylineModel),
                summary = it.summary
            )
        }

    private fun mapDirectionPolyLineResponsesToDomain(polylineModel: DirectionPolylineModel?): com.icksan.todoaha.core.domain.model.DirectionPolylineModel =
        com.icksan.todoaha.core.domain.model.DirectionPolylineModel(
            points = polylineModel?.points
        )

    private fun mapDirectionLegsResponsesToDomain(legs: List<DirectionLegModel>?): List<com.icksan.todoaha.core.domain.model.DirectionLegModel>? =
        legs?.map {
            com.icksan.todoaha.core.domain.model.DirectionLegModel(
                distance = mapDirectionDistanceResponsesToDomain(it.distance),
                duration = mapDirectionDurationResponsesToDomain(it.duration),
                endAddress = it.endAddress,
                endLocation = mapDirectionEndLocationResponsesToDomain(it.endLocation),
                startAddress = it.startAddress,
                startLocation = mapDirectionStartLocationResponsesToDomain(it.startLocation),
                steps = mapDirectionStepsResponsesToDomain(it.steps)
            )
        }

    private fun mapDirectionStepsResponsesToDomain(steps: List<DirectionStepModel>?): List<com.icksan.todoaha.core.domain.model.DirectionStepModel>? =
        steps?.map {
            com.icksan.todoaha.core.domain.model.DirectionStepModel(
                distance = mapDirectionDistanceResponsesToDomain(it.distance),
                duration = mapDirectionDurationResponsesToDomain(it.duration),
                endLocation = mapDirectionEndLocationResponsesToDomain(it.endLocation),
                htmlInstructions = it.htmlInstructions,
                polyline = mapDirectionPolyLineResponsesToDomain(it.polyline),
                startLocation = mapDirectionStartLocationResponsesToDomain(it.startLocation),
                travelMode = it.travelMode,
                maneuver = it.maneuver
            )
        }

    private fun mapDirectionStartLocationResponsesToDomain(startLocation: StartLocationModel?): com.icksan.todoaha.core.domain.model.StartLocationModel =
        com.icksan.todoaha.core.domain.model.StartLocationModel(
            lat = startLocation?.lat,
            lng = startLocation?.lng
        )

    private fun mapDirectionEndLocationResponsesToDomain(endLocation: EndLocationModel?): com.icksan.todoaha.core.domain.model.EndLocationModel =
        com.icksan.todoaha.core.domain.model.EndLocationModel(
            lat = endLocation?.lat,
            lng = endLocation?.lng
        )
    private fun mapDirectionDurationResponsesToDomain(duration: DirectionDurationModel?): com.icksan.todoaha.core.domain.model.DirectionDurationModel =
        com.icksan.todoaha.core.domain.model.DirectionDurationModel(
            text = duration?.text,
            value = duration?.value
        )

    private fun mapDirectionDistanceResponsesToDomain(distance: DirectionDistanceModel?): com.icksan.todoaha.core.domain.model.DirectionDistanceModel =
        com.icksan.todoaha.core.domain.model.DirectionDistanceModel(
            text = distance?.text,
            value = distance?.value
        )
}