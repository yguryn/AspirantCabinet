package com.example.feature_administrator.aspirantlist.supervisorlist.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.Supervisor
import com.example.feature_administrator.databinding.ItemSupervisorBinding

class SupervisorAdapter(private val listener: (Supervisor)->Unit) :
    RecyclerView.Adapter<SupervisorAdapter.ViewHolder>() {

    var listOfAspirants = AsyncListDiffer(this, differSupervisorCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            ItemSupervisorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount() = listOfAspirants.currentList.size

    inner class ViewHolder(private val binding: ItemSupervisorBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(supervisor: Supervisor) {
            binding.apply {
                nameTextView.text = "${supervisor.name} ${supervisor.surname} ${supervisor.middleName}"
                facultyTextView.text = supervisor.faculty
                root.setOnClickListener {
                    listener.invoke(supervisor)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfAspirants.currentList[position])
    }
}