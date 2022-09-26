package com.icksan.todoaha.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionLegModel(
    @field:SerializedName("distance")
    val distance: DirectionDistanceModel? = null,
    @field:SerializedName("duration")
    val duration: DirectionDurationModel? = null,
    @field:SerializedName("end_address")
    val endAddress: String? = null,
    @field:SerializedName("end_location")
    val endLocation: EndLocationModel? = null,
    @field:SerializedName("start_address")
    val startAddress: String? = null,
    @field:SerializedName("start_location")
    val startLocation: StartLocationModel? = null,
    @field:SerializedName("steps")
    val steps: List<DirectionStepModel>? = null
) : Parcelable
