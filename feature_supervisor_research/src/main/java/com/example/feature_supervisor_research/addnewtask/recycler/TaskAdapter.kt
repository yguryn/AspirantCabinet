package com.example.feature_supervisor_research.addnewtask.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.Task
import com.example.feature_supervisor_research.databinding.ItemTaskBinding

class TaskAdapter() : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    var listOfTasks = listOf<Task>()

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                taskNameTextView.text = task.a
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskAdapter.ViewHolder {
        val viewItem =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount() = listOfTasks.size

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        holder.bind(listOfTasks[position])
    }
}