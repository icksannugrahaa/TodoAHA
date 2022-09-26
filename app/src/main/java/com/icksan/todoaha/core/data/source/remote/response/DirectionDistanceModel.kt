package com.icksan.todoaha.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionDistanceModel(
    @field:SerializedName("text")
    val text: String? = null,
    @field:SerializedName("value")
    val value: Int? = null
) : Parcelable
