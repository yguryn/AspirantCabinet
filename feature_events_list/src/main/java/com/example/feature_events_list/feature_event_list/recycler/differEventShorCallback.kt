package com.example.feature_events_list.feature_event_list.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.core.model.Event

val differEventShorCallback  = object : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}