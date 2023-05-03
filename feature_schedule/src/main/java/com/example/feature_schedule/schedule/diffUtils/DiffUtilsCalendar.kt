package com.example.feature_schedule.schedule.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.feature_schedule.schedule.model.DayOfMonthUI

class DiffUtilsCalendar(
    private val oldList: List<DayOfMonthUI>,
    private val newList: List<DayOfMonthUI>
) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                return false
            }
            oldList[oldItemPosition].day != newList[newItemPosition].day -> {
                return false
            }
            else -> true
        }
    }
}