package com.icksan.todoaha.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionStepModel(
    @field:SerializedName("distance")
    var distance: DirectionDistanceModel? = null,
    @field:SerializedName("duration")
    var duration: DirectionDurationModel? = null,
    @field:SerializedName("end_location")
    var endLocation: EndLocationModel? = null,
    @field:SerializedName("html_instructions")
    var htmlInstructions: String? = null,
    @field:SerializedName("polyline")
    var polyline: DirectionPolylineModel? = null,
    @field:SerializedName("start_location")
    var startLocation: StartLocationModel? = null,
    @field:SerializedName("travel_mode")
    var travelMode: String? = null,
    @field:SerializedName("maneuver")
    var maneuver: String? = null
) : Parcelable
