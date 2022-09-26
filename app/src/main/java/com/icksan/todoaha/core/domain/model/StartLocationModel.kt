package com.icksan.todoaha.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StartLocationModel(
    val lat: Double? = null,
    val lng: Double? = null
) : Parcelable