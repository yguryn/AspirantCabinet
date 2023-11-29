package com.example.feature_schedule.schedule.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_schedule.schedule.diffUtils.DiffUtilsCalendar
import com.example.feature_schedule.schedule.model.DayOfMonthUI
import com.postgraduate.cabinet.feature_schedule.databinding.ItemCalendarBinding

class CalendarAdapter(private val callback: OnItemClickListener) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    private var listDayOfWeek = listOf<DayOfMonthUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(listDayOfWeek[position])
    }

    override fun getItemCount() = listDayOfWeek.size

    fun setDate(list: List<DayOfMonthUI>) {
        val diff = DiffUtilsCalendar(listDayOfWeek, list)
        val diffResult = DiffUtil.calculateDiff(diff)
        listDayOfWeek = list
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(dayOfMonthUI: DayOfMonthUI)
    }

    inner class ViewHolder(private val binding: ItemCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                callback.onClick(listDayOfWeek[adapterPosition])
            }
        }

        fun bind(dayOfMonth: DayOfMonthUI) {
            binding.apply {
                itemCalendar.text = dayOfMonth.day.toString()
                nameOfDay.text = dayOfMonth.nameOfDay

                if (dayOfMonth.isPressed) {
                    selectBorder.visibility = View.VISIBLE
                } else {
                    selectBorder.visibility = View.GONE
                }

                if (dayOfMonth.currentDay && dayOfMonth.currentMonth) {
                    currentDayBorder.visibility = View.VISIBLE
                } else {
                    currentDayBorder.visibility = View.GONE
                }
            }
        }
    }
}