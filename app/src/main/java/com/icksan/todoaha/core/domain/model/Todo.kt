package com.icksan.todoaha.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo (
    val id: Int?,
    val title: String?,
    val description: String?,
    val createdAt: Long?,
    val updatedAt: Long?
) : Parcelable