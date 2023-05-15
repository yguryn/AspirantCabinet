package com.example.feature_supervisor_research.aspirantdetails.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.core.model.Article

val differDetailsCallback = object : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}