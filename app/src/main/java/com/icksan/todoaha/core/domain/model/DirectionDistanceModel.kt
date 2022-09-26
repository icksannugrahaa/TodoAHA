package com.icksan.todoaha.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionDistanceModel(
    val text: String? = null,
    val value: Int? = null
) : Parcelable
