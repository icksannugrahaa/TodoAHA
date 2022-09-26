package com.icksan.todoaha.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionPolylineModel(
    var points: String? = null
) : Parcelable
