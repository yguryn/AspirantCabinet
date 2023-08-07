package com.example.core.model

import java.util.*

data class IndividualPlan(
    val content: String = "",
    val deadline: Date = Date(0),
    val form: String = "",
    var isDone: Boolean = false
)
