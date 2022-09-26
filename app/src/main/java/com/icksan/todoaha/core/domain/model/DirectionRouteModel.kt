package com.icksan.todoaha.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionRouteModel(
    var legs: List<DirectionLegModel>? = null,
    var polylineModel: DirectionPolylineModel? = null,
    var summary: String? = null
) : Parcelable
