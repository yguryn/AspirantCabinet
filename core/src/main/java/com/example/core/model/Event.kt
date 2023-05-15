package com.example.core.model

import com.google.firebase.firestore.Exclude
import java.util.*

data class Event(
    @Exclude @JvmField
    var id: String = "",
    val user_id: String = "",
    val description: String = "",
    val event_start: Date = Date(0),
    val event_end: Date = Date(0),
    val title: String = ""
)