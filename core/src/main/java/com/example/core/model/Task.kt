package com.example.core.model

import java.util.*

data class Task(
    var name: String = "",
    var date: Date = Date(0),
    var isDone: Boolean = false
)
