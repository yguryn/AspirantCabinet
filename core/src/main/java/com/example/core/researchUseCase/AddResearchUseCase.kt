package com.example.core.researchUseCase

import com.example.core.di.ResearchCollection
import com.example.core.model.Research
import com.example.core.utils.SharedPreferencesHelper
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class AddResearchUseCase @Inject constructor(
    @ResearchCollection
    private val researchesRef: CollectionReference,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) {

    fun execute(research: Research) {
        researchesRef.add(research)
    }
}
