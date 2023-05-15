package com.example.feature_administrator.aspirantlist.listOfAspirants.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.Aspirant
import com.example.feature_administrator.databinding.ItemAspirantBinding

class AspirantAdapter() :
    RecyclerView.Adapter<AspirantAdapter.ViewHolder>() {

    var listOfAspirants = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            ItemAspirantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount() = listOfAspirants.currentList.size

    inner class ViewHolder(private val binding: ItemAspirantBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(aspirant: Aspirant) {
            binding.apply {
                nameTextView.text = "${aspirant.name} ${aspirant.surname} ${aspirant.middleName}"
                facultyTextView.text = aspirant.faculty
                groupTextView.text = aspirant.group
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfAspirants.currentList[position])
    }
}