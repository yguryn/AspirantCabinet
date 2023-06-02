package com.example.feature_supervisor_research.addnewtask.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.core.model.Article
import com.example.core.model.Task

val differTasksCallback = object : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}