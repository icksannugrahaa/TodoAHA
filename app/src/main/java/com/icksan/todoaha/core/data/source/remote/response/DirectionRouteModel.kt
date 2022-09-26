package com.icksan.todoaha.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionRouteModel(
    @field:SerializedName("legs")
    var legs: List<DirectionLegModel>? = null,
    @field:SerializedName("overview_polyline")
    var polylineModel: DirectionPolylineModel? = null,
    @field:SerializedName("summary")
    var summary: String? = null
) : Parcelable
