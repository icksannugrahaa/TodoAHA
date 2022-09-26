package com.icksan.todoaha.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionPolylineModel(
    @field:SerializedName("points")
    var points: String? = null
) : Parcelable
