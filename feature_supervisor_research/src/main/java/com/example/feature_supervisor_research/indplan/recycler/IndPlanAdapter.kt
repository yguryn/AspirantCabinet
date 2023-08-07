package com.example.feature_supervisor_research.indplan.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.IndividualPlan
import com.example.feature_supervisor_research.databinding.ItemIndPlanBinding
import com.example.feature_supervisor_research.formatToString

class IndPlanAdapter(private val makeDoneListener: (Int) -> Unit) :
    RecyclerView.Adapter<IndPlanAdapter.IndPlanViewHolder>() {

    var indPlanList = AsyncListDiffer(this, differIndPlanCallback)

    inner class IndPlanViewHolder(val binding: ItemIndPlanBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndPlanViewHolder {
        val binding =
            ItemIndPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndPlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndPlanViewHolder, position: Int) {
        val indPlan = indPlanList.currentList[position]
        holder.binding.contentText.text = "Зміст роботи: ${indPlan.content}"
        holder.binding.deadlineText.text = "Термін: ${indPlan.deadline.formatToString()}"
        holder.binding.formText.text = "Форма: ${indPlan.form}"
        holder.binding.isDoneImageView.isVisible = indPlan.isDone
        holder.binding.root.setOnClickListener {
            makeDoneListener.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return indPlanList.currentList.size
    }
}