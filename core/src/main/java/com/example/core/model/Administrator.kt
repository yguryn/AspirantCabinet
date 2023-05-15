package com.example.core.model

import com.google.firebase.firestore.Exclude

data class Administrator(
    @Exclude
    @JvmField
    var id: String = "",
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var phone: String = ""
)