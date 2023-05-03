package com.example.core.model

import com.google.firebase.firestore.Exclude
import java.util.Date

data class Event(
    @Exclude @JvmField
    var id: String = "",
    val user_id: Int = 0,
    val description: String = "",
    val event_start: Date = Date(0),
    val event_end: Date = Date(0),
    val title: String = ""
)