package com.icksan.todoaha.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Direction(
    val directionRouteModels: List<DirectionRouteModel>? = null,
    val status: String? = null
) : Parcelable