package com.example.feature_supervisor_research.indplan.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.postgraduate.cabinet.ui.R
import com.example.feature_supervisor_research.databinding.ItemIndPlanBinding
import com.example.feature_supervisor_research.formatToString

class IndPlanAdapter(private val context: Context, private val makeDoneListener: (Int) -> Unit) :
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
        holder.binding.apply {
            contentText.text = "${context.getString(R.string.content_work)}: ${indPlan.content}"
            deadlineText.text =
                "${context.getString(R.string.term)}: ${indPlan.deadline.formatToString()}"
            formText.text = "${context.getString(R.string.form)}: ${indPlan.form}"
            isDoneImageView.isVisible = indPlan.isDone
            root.setOnClickListener {
                makeDoneListener.invoke(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return indPlanList.currentList.size
    }
}