package com.example.feature_schedule.schedule.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.example.feature_schedule.schedule.ScheduleFragment.Companion.MINUTES_IN_HOUR
import com.example.feature_schedule.schedule.ScheduleFragment.Companion.ONE_HOUR_GRID_CELL_IN_PX
import com.example.feature_schedule.schedule.ScheduleFragment.Companion.QUARTER_HOUR
import com.example.feature_schedule.schedule.model.HourUI
import com.example.feature_schedule.utils.parseToLocalTime
import com.example.feature_schedule.utils.toPx
import com.example.feature_schedule.schedule.view.RoomBookingCalendarView.Companion.CELL_SIZE_HEIGHT_PX
import com.postgraduate.cabinet.feature_schedule.R
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.roundToInt

class HoursColumnView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val hours: MutableList<HourUI> = mutableListOf()
    private lateinit var upperEdgeTime: HourUI
    private lateinit var lowerEdgeTime: HourUI

    private val rowsCount = hours.size
    private var timeWidthPx = 0f

    companion object {
        private const val COLON_DELIMITER = ":"
        private const val DEFAULT_MINUTES = "00"
        private const val PADDING_TOP_DP = 9
        private const val TIME_SPREAD_AREA_DP = 3

        private val PADDING_TOP_PX = PADDING_TOP_DP.toPx
        private val TIME_SPREAD_AREA_PX = TIME_SPREAD_AREA_DP.toPx

        const val TIMELINE_START_AT: Int = 6
    }

    init {
        val pixel = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            12f, resources.displayMetrics
        ).toInt()
        paint.color = getColorTimeDefault()
        paint.textSize = pixel.toFloat()
        timeWidthPx = paint.measureText("00:00", 0, 5)

        initTimeline()
    }

    fun getEdgesTime() = Pair(upperEdgeTime, lowerEdgeTime)

    private fun initTimeline() {
        val defHoursValues = resources.getStringArray(R.array.hours)

        for (i in defHoursValues.indices) {
            val hourPositionY = CELL_SIZE_HEIGHT_PX * i.toFloat() + PADDING_TOP_PX
            val hourTime = defHoursValues[i]
            val hourColor = getColorTimeDefault()

            val hour = HourUI(id = i).apply {
                this.y = hourPositionY
                this.time = hourTime
                this.color = hourColor
            }
            hours.add(i, hour)
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        for (hour in hours) {
            paint.color = hour.color
            canvas.drawText(hour.time, hour.x, hour.y, paint)
        }
        super.onDraw(canvas)
    }

    private fun updateEdgeTimePrevColorOrDelete(timePositionY: Float, isUpperEdge: Boolean) {
        var currentEdge = HourUI(-1)
        var anotherEdge = HourUI(-1)
        val isCurrentEdgeInitialized: Boolean
        val isAnotherEdgeInitialized: Boolean

        if (isUpperEdge) {
            isCurrentEdgeInitialized = ::upperEdgeTime.isInitialized
            isAnotherEdgeInitialized = ::lowerEdgeTime.isInitialized
            if (isCurrentEdgeInitialized) {
                currentEdge = upperEdgeTime
                anotherEdge = lowerEdgeTime
            }
        } else {
            isCurrentEdgeInitialized = ::lowerEdgeTime.isInitialized
            isAnotherEdgeInitialized = ::upperEdgeTime.isInitialized
            if (isCurrentEdgeInitialized) {
                currentEdge = lowerEdgeTime
                anotherEdge = upperEdgeTime
            }
        }

        if (isCurrentEdgeInitialized) {
            val isTimeHourRounded = currentEdge.time.split(COLON_DELIMITER)[1] == DEFAULT_MINUTES

            if (isTimeHourRounded) {
                if (isAnotherEdgeInitialized && timePositionY != currentEdge.y && currentEdge.id != anotherEdge.id) {
                    hours[currentEdge.id].color = getColorTimeDefault()
                }
            } else {
                hours.removeAt(currentEdge.id)
                if (currentEdge.id == hours.size - 1) anotherEdge.id = hours.size - 1
            }
        }
    }

    fun updateHours(timePositionY: Float, isUpperEdge: Boolean) {
        updateEdgeTimePrevColorOrDelete(timePositionY, isUpperEdge)

        var hour: HourUI? = null

        hours.forEach { hourUI ->
            if ((hourUI.y - TIME_SPREAD_AREA_PX < timePositionY) && (timePositionY < hourUI.y + TIME_SPREAD_AREA_PX)) {
                hour = hourUI
            }
        }

        val noMatches = hour == null
        if (noMatches) {
            val timeValue = calculateTime(timePositionY, isUpperEdge)

            val bottomTime = timeValue.parseToLocalTime()
            val bottomHour = bottomTime.hour
            val bottomMinutes = bottomTime.minute

            val bottomTimeInHours = bottomHour.toFloat() + bottomMinutes.toFloat() / MINUTES_IN_HOUR
            val positionY = CELL_SIZE_HEIGHT_PX * (bottomTimeInHours - TIMELINE_START_AT) + PADDING_TOP_PX

            val edgeTime = HourUI(
                id = hours.size,
                time = timeValue,
                y = positionY,
                color = getColorTimeSelected()
            )

            hours.add(edgeTime)

            if (isUpperEdge)
                upperEdgeTime = edgeTime
            else
                lowerEdgeTime = edgeTime
        } else {
            hour?.let {
                it.color = getColorTimeSelected()
                hours[it.id] = it

                if (isUpperEdge)
                    upperEdgeTime = it
                else
                    lowerEdgeTime = it
            }
        }

        invalidate()
    }

    private fun calculateTime(timePositionY: Float, upperEdge: Boolean): String {
        var edgeLastPositionY: Float = timePositionY
        var edgeTimeMinutes = DEFAULT_MINUTES
        var edgeTimeHours = DEFAULT_MINUTES

        if (upperEdge && !::upperEdgeTime.isInitialized) {
            val timeRoundedByHourPx =
                ceil(timePositionY / ONE_HOUR_GRID_CELL_IN_PX) * ONE_HOUR_GRID_CELL_IN_PX
            val timeRoundedByHour =
                (ceil(timePositionY / ONE_HOUR_GRID_CELL_IN_PX) + TIMELINE_START_AT).roundToInt()

            edgeLastPositionY = timeRoundedByHourPx
            edgeTimeHours = timeRoundedByHour.toString()
        } else if (upperEdge) {
            edgeLastPositionY = upperEdgeTime.y
            edgeTimeHours = upperEdgeTime.time.split(COLON_DELIMITER)[0]
            edgeTimeMinutes = upperEdgeTime.time.split(COLON_DELIMITER)[1]
        }

        if (!upperEdge && !::lowerEdgeTime.isInitialized) {
            val timeRoundedByHourPx =
                ceil(timePositionY / ONE_HOUR_GRID_CELL_IN_PX) * ONE_HOUR_GRID_CELL_IN_PX
            val timeRoundedByHour =
                (ceil(timePositionY / ONE_HOUR_GRID_CELL_IN_PX) + TIMELINE_START_AT).roundToInt()

            edgeLastPositionY = timeRoundedByHourPx
            edgeTimeHours = timeRoundedByHour.toString()
        } else if (!upperEdge) {
            edgeLastPositionY = lowerEdgeTime.y
            edgeTimeHours = lowerEdgeTime.time.split(COLON_DELIMITER)[0]
            edgeTimeMinutes = lowerEdgeTime.time.split(COLON_DELIMITER)[1]
        }

        val additionFactor =
            when {
                timePositionY > edgeLastPositionY -> 1
                timePositionY < edgeLastPositionY -> -1
                else -> 0
            }

        val minutesToBeChanged = QUARTER_HOUR * additionFactor

        edgeTimeMinutes =
            if (edgeTimeMinutes == DEFAULT_MINUTES) {
                val newMinutes = edgeTimeMinutes.toInt() + minutesToBeChanged

                when {
                    newMinutes < 0 -> {
                        val newHours = "${edgeTimeHours.toInt() - 1}"
                        edgeTimeHours = if (newHours.length == 2) newHours else "0$newHours"
                        "${MINUTES_IN_HOUR + newMinutes}"
                    }
                    else -> "$newMinutes"
                }
            } else {
                val newMinutes = edgeTimeMinutes.toInt() + minutesToBeChanged
                when {
                    newMinutes == 0 -> DEFAULT_MINUTES
                    newMinutes == MINUTES_IN_HOUR -> DEFAULT_MINUTES
                    newMinutes > MINUTES_IN_HOUR -> {
                        edgeTimeHours = "${edgeTimeHours.toInt() + 1}"
                        "${newMinutes - MINUTES_IN_HOUR}"
                    }
                    else -> "$newMinutes"
                }
            }

        return "$edgeTimeHours:$edgeTimeMinutes"
    }

    private fun getColorTimeDefault() =
        ContextCompat.getColor(context, R.color.color_default_time_events_grid)

    private fun getColorTimeSelected() =
        ContextCompat.getColor(context, R.color.color_selected_time_events_grid)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val requestedWidth = MeasureSpec.getSize(widthMeasureSpec)
        val requestedWidthMode = MeasureSpec.getMode(widthMeasureSpec)

        val requestedHeight = MeasureSpec.getSize(heightMeasureSpec)
        val requestedHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        val desiredWidth: Int = timeWidthPx.toInt()
        val desiredHeight: Int = (CELL_SIZE_HEIGHT_PX * (rowsCount - 1) + PADDING_TOP_PX).toInt()

        val width = when (requestedWidthMode) {
            MeasureSpec.EXACTLY -> requestedWidth
            MeasureSpec.UNSPECIFIED -> desiredWidth
            else -> min(requestedWidth, desiredWidth)
        }

        val height = when (requestedHeightMode) {
            MeasureSpec.EXACTLY -> requestedHeight
            MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> min(requestedHeight, desiredHeight)
        }

        setMeasuredDimension(width, height)
    }
}
