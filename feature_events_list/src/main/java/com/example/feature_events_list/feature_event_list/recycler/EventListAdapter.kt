package com.example.feature_events_list.feature_event_list.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.Event
import com.example.feature_events_list.databinding.ItemEventShortBinding
import java.text.SimpleDateFormat
import java.util.*

class EventListAdapter(private val listener: (String) -> Unit) :
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    var listOfEvents = AsyncListDiffer(this, differEventShorCallback)

    inner class ViewHolder(private val binding: ItemEventShortBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.apply {
                val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
                val dateFormatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

                timeStartTextView.text = timeFormatter.format(event.event_start)
                timeEndTextView.text = timeFormatter.format(event.event_end)

                eventDateTextView.text = dateFormatter.format(event.event_start)

                eventTitleTextView.text = event.title
                descriptionTextView.text = event.description
                root.setOnClickListener {
                    listener.invoke(event.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventListAdapter.ViewHolder {
        val viewItem =
            ItemEventShortBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount() = listOfEvents.currentList.size

    override fun onBindViewHolder(holder: EventListAdapter.ViewHolder, position: Int) {
        holder.bind(listOfEvents.currentList[position])
    }
}