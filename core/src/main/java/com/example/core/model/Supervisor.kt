package com.example.core.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class Supervisor(
    @Exclude @JvmField
    var id: String = "",
    @set:PropertyName("object_research")
    @get:PropertyName("object_research")
    var objectResearch: String = "",
    @set:PropertyName("subject_research")
    @get:PropertyName("subject_research")
    var subjectResearch: String = "",
    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    var userId: Int = 1,
    var topic: String = "",
)
