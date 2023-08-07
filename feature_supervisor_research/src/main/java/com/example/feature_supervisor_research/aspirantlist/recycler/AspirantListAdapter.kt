package com.example.feature_supervisor_research.aspirantlist.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.Aspirant
import com.example.feature_supervisor_research.aspirantlist.ChangeGradeDialog
import com.example.feature_supervisor_research.databinding.ItemAspirant2Binding

class AspirantListAdapter(
    val listener: (Aspirant) -> Unit,
    val taskListener: (Aspirant) -> Unit,
    val gradeChangedListener: (Aspirant) -> Unit,
    val indPlanListener: (Aspirant) -> Unit,
    val fragManager: FragmentManager
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
                    listener.invoke(aspirant)
                }
                reviewTasksTextView.setOnClickListener {
                    taskListener.invoke(aspirant)
                }
                indPlanTextView.setOnClickListener {
                    indPlanListener.invoke(aspirant)
                }
                estimateTextView.text = "Загальна оцінка: ${aspirant.grade}"
                estimateTextView.setOnClickListener {
                    ChangeGradeDialog(
                        aspirant.grade
                    ) {
                        aspirant.grade = it
                        estimateTextView.text = "Загальна оцінка: $it"
                        gradeChangedListener.invoke(aspirant)
                    }.show(
                        fragManager,
                        null
                    )
                }
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