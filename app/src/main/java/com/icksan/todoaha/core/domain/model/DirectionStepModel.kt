package com.icksan.todoaha.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionStepModel(
    var distance: DirectionDistanceModel? = null,
    var duration: DirectionDurationModel? = null,
    var endLocation: EndLocationModel? = null,
    var htmlInstructions: String? = null,
    var polyline: DirectionPolylineModel? = null,
    var startLocation: StartLocationModel? = null,
    var travelMode: String? = null,
    var maneuver: String? = null
) : Parcelable
