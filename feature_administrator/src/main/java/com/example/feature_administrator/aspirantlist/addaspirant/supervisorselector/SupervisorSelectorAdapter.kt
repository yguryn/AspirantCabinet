package com.example.feature_administrator.aspirantlist.addaspirant.supervisorselector

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.Supervisor
import com.example.feature_administrator.databinding.SupervisorSelectorItemBinding

class SupervisorSelectorAdapter (
    val listener: (Supervisor) -> Unit
) : RecyclerView.Adapter<SupervisorSelectorAdapter.ViewHolder>() {

    var listOfSupervisors = listOf<Supervisor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val roomSelectorViewItem =
            SupervisorSelectorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(roomSelectorViewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfSupervisors[position])
    }

    override fun getItemCount() = listOfSupervisors.size

    inner class ViewHolder(private val binding: SupervisorSelectorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.invoke(listOfSupervisors[adapterPosition])
            }
        }

        fun bind(supervisor: Supervisor) {
            binding.roomSelectorName.text = "${supervisor.name} ${supervisor.surname} ${supervisor.middleName}"
        }
    }
}