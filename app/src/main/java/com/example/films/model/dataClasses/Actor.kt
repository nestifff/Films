package com.example.films.model.dataClasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    var imageUrl: String?
)
