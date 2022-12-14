package com.icksan.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo (
    val id: Int,
    val title: String?,
    val description: String?,
    val createdAt: String?,
    val updatedAt: String?
) : Parcelable