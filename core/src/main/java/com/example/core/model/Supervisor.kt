package com.example.core.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize

data class Supervisor(
    @Exclude @JvmField
    var id: String = "",
    var name: String = "",
    var surname: String = "",
    var middleName: String = "",
    var faculty: String = "",
    var email: String = "",
    var phone: String = "",
    var listOfAspirants: MutableList<String> = mutableListOf(),
): Parcelable
