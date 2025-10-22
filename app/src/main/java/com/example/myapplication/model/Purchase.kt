package com.example.myapplication.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Purchase(
    val itemId: Int = 0, // Ajustado de String para Int
    val itemName: String = "",
    val itemValue: Double = 0.0,
    val itemPhoto: String = "",
    val userId: String = "",
    @ServerTimestamp
    val timestamp: Date? = null
)