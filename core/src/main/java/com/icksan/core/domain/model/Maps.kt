package com.icksan.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Maps(
    val id: Int,
    val current_location: String?,
    val destination_location: String?
) : Parcelable
