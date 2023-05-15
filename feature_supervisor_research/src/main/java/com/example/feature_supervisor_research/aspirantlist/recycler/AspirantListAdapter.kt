package com.example.feature_supervisor_research.aspirantlist.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.Aspirant
import com.example.feature_supervisor_research.databinding.ItemAspirantBinding

class AspirantListAdapter(val listener: (Aspirant) -> Unit) :
    RecyclerView.Adapter<AspirantListAdapter.ViewHolder>() {

    var listOfAspirants = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ItemAspirantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(aspirant: Aspirant) {
            binding.apply {
                nameTextView.text = "${aspirant.name} ${aspirant.surname}"
                facultyTextView.text = aspirant.faculty
                groupTextView.text = aspirant.group
                root.setOnClickListener {
                    listener.invoke(aspirant)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            ItemAspirantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount() = listOfAspirants.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfAspirants.currentList[position])
    }
}