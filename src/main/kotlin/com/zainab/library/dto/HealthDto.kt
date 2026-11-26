package com.zainab.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HealthDto(
    @SerialName("status") val status: HealthStatus,
) : Dto

@Serializable
enum class HealthStatus { UP, DOWN }
