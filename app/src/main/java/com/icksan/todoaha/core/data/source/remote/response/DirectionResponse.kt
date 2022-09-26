package com.icksan.todoaha.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionResponse(
    @field:SerializedName("routes")
    val directionRouteModels: List<DirectionRouteModel>? = null,
    @field:SerializedName("status")
    val status: String? = null
) : Parcelable