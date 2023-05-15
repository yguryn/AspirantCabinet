package com.example.feature_supervisor_research.aspirantlist.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.core.model.Aspirant

val differCallback = object : DiffUtil.ItemCallback<Aspirant>() {
    override fun areItemsTheSame(oldItem: Aspirant, newItem: Aspirant): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Aspirant, newItem: Aspirant): Boolean {
        return oldItem.name == newItem.name
    }
}
