package com.example.feature_schedule.schedule.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.feature_schedule.utils.toPx
import com.postgraduate.cabinet.feature_schedule.R
import kotlin.math.min

class RoomBookingCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rowsCount = resources.getStringArray(R.array.hours).size

    private val cellsCountVertically = rowsCount - 1
    private val verticalLineStopY = CELL_SIZE_HEIGHT_PX * cellsCountVertically + PADDING_TOP_PX

    var roomsCount = 1
        set(value) {
            field = value
            requestLayout()
        }

    init {
        paint.color = ContextCompat.getColor(context, R.color.grid_color)
        paint.strokeWidth = STROKE_WIDTH_PX
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val rowHeight = CELL_SIZE_HEIGHT_PX
        for (i in 0 until rowsCount) {
            val y = rowHeight * i.toFloat() + PADDING_TOP_PX
            canvas.drawLine(0f, y, width.toFloat(), y, paint)
        }

        val rowWidth = CELL_SIZE_WIDTH_PX
        var x: Float

        for (i in 0..roomsCount) {
            x = rowWidth * i.toFloat()
            canvas.drawLine(x, PADDING_TOP_PX, x, verticalLineStopY, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val requestedWidth = MeasureSpec.getSize(widthMeasureSpec)
        val requestedWidthMode = MeasureSpec.getMode(widthMeasureSpec)

        val requestedHeight = MeasureSpec.getSize(heightMeasureSpec)
        val requestedHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        val desiredWidth = (CELL_SIZE_WIDTH_PX * roomsCount).toInt()
        val desiredHeight = (CELL_SIZE_HEIGHT_PX * (rowsCount - 1) + PADDING_TOP_PX).toInt()

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

    companion object {
        private const val CELL_SIZE_HEIGHT_DP = 90
        private const val CELL_SIZE_WIDTH_DP = 360
        private const val PADDING_TOP_DP = 7
        private const val STROKE_WIDTH_DP = 2

        val CELL_SIZE_HEIGHT_PX = CELL_SIZE_HEIGHT_DP.toPx
        val CELL_SIZE_WIDTH_PX = CELL_SIZE_WIDTH_DP.toPx
        val PADDING_TOP_PX = PADDING_TOP_DP.toPx
        val STROKE_WIDTH_PX = STROKE_WIDTH_DP.toPx
    }
}
