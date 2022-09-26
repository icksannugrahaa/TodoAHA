package com.icksan.todoaha.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EndLocationModel(
    val lat: Double? = null,
    val lng: Double? = null
) : Parcelable