package com.example.feature_supervisor_research.indplan.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.core.model.Article
import com.example.core.model.IndividualPlan

val differIndPlanCallback = object : DiffUtil.ItemCallback<IndividualPlan>() {
    override fun areItemsTheSame(oldItem: IndividualPlan, newItem: IndividualPlan): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: IndividualPlan, newItem: IndividualPlan): Boolean {
        return oldItem == newItem
    }
}