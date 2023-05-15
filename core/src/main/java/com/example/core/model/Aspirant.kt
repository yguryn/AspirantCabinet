package com.example.core.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Aspirant(
    @Exclude @JvmField
    var id: String = "",
    var name: String = "",
    var surname: String = "",
    var middleName: String = "",
    var specialization: String = "",
    var department: String = "",
    var faculty: String = "",
    var educationForm: String = "",
    var diplomaNumber: String = "",
    var birthday: Long = 0L,
    var paymentType: String = "",
    var group: String = "",
    var supervisorId: String = "",
    var email: String = "",
    var phone: String = "",
    var isBudget: Boolean = false,
    var isConfirmed: Boolean = false,
    var researchId: String = ""
): Parcelable