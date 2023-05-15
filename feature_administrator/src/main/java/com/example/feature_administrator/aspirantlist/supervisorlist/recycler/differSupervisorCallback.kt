package com.example.feature_administrator.aspirantlist.supervisorlist.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.core.model.Aspirant
import com.example.core.model.Supervisor

val differSupervisorCallback = object : DiffUtil.ItemCallback<Supervisor>() {
    override fun areItemsTheSame(oldItem: Supervisor, newItem: Supervisor): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Supervisor, newItem: Supervisor): Boolean {
        return oldItem == newItem
    }
}