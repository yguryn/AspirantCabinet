package com.example.core.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import java.util.*

data class Research(
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
    var userId: String = "",
    var topic: String = "",
    var listOfArticles: MutableList<Article> = mutableListOf(),
    var listOfThesis: MutableList<Thesis> = mutableListOf()
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Research

        if (id != other.id) return false
        if (objectResearch != other.objectResearch) return false
        if (subjectResearch != other.subjectResearch) return false
        if (userId != other.userId) return false
        if (topic != other.topic) return false
        if (listOfArticles !== other.listOfArticles) return false
        if (listOfThesis !== other.listOfThesis) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + objectResearch.hashCode()
        result = 31 * result + subjectResearch.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + topic.hashCode()
        result = 31 * result + listOfArticles.hashCode()
        result = 31 * result + listOfThesis.hashCode()
        return result
    }
}