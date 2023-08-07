package com.example.feature_supervisor_research.addnewtask.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.Task
import com.example.feature_supervisor_research.R
import com.example.feature_supervisor_research.databinding.ItemTaskBinding
import com.example.feature_supervisor_research.formatToString

class TaskAdapter(private val deleteTaskListener: (Task) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    var listOfTasks = AsyncListDiffer(this, differTasksCallback)

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                taskNameTextView.text = task.name
                doneDateTextView.text = task.date.formatToString()
                if (task.isDone) {
                    taskStatusImageView.setBackgroundResource(com.postgraduate.cabinet.ui.R.drawable.ic_check)
                } else {
                    taskStatusImageView.setBackgroundResource(com.postgraduate.cabinet.ui.R.drawable.ic_check_white)
                }
                root.setOnLongClickListener {
                    deleteTaskListener.invoke(task)
                    true
                }
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

    override fun getItemCount() = listOfTasks.currentList.size

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        holder.bind(listOfTasks.currentList[position])
    }
}