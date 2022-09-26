package com.icksan.todoaha.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StartLocationModel(
    @field:SerializedName("lat")
    val lat: Double? = null,
    @field:SerializedName("lng")
    val lng: Double? = null
) : Parcelable