package com.mkulima.app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Farmer(
    val id: String,
    val name: String,
    val location: String,
    val county: String,
    val mainCrop: String,
    val farmSizeHectares: Double,
    val phoneNumber: String,
    val createdAt: String = ""
)

@Serializable
data class WeatherAlert(
    val id: String,
    val county: String,
    val temperature: Int,
    val condition: String,
    val description: String,
    val riskLevel: String,
    val timestamp: String = ""
)

@Serializable
data class PestAlert(
    val id: String,
    val county: String,
    val pestName: String,
    val severity: String,
    val recommendation: String,
    val timestamp: String = ""
)

@Serializable
data class DailyTask(
    val id: String,
    val farmerId: String,
    val title: String,
    val description: String,
    val completed: Boolean = false,
    val dueDate: String = ""
)

@Serializable
data class CropPrice(
    val id: String,
    val cropName: String,
    val location: String,
    val price: Double,
    val currency: String = "KES",
    val timestamp: String = ""
)

@Serializable
data class DiagnosisResult(
    val id: String,
    val farmerId: String,
    val photoUrl: String,
    val diagnosis: String,
    val confidence: Float,
    val recommendations: List<String>,
    val timestamp: String = ""
)
