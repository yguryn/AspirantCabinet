package com.example.feature_supervisor_research.aspirantlist.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.Aspirant
import com.example.feature_supervisor_research.databinding.ItemAspirant2Binding

class AspirantListAdapter(
    val onAspirantClick: (Aspirant) -> Unit,
    val onNewTaskClick: (Aspirant) -> Unit,
    val onIndPlanClick: (Aspirant) -> Unit,
    val context: Context
) :
    RecyclerView.Adapter<AspirantListAdapter.ViewHolder>() {

    var listOfAspirants = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ItemAspirant2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(aspirant: Aspirant) {
            binding.apply {
                nameTextView.text = "${aspirant.name} ${aspirant.surname}"
                facultyTextView.text = aspirant.faculty
                groupTextView.text = aspirant.group
                reviewWorksTextView.setOnClickListener {
                    onAspirantClick.invoke(aspirant)
                }
                reviewTasksTextView.setOnClickListener {
                    onNewTaskClick.invoke(aspirant)
                }
                indPlanTextView.setOnClickListener {
                    onIndPlanClick.invoke(aspirant)
                }
                estimateTextView.text =
                    "${context.getString(com.postgraduate.cabinet.ui.R.string.overall_assessment)}: ${aspirant.grade}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            ItemAspirant2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount() = listOfAspirants.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfAspirants.currentList[position])
    }
}