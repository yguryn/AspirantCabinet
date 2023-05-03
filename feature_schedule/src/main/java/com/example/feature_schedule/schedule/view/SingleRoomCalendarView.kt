package com.example.feature_schedule.schedule.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.postgraduate.cabinet.feature_schedule.R
import kotlin.math.min

class SingleRoomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rowsCount = resources.getStringArray(R.array.hours).size

    private val paddingTopDp = 7
    private val paddingTopPx = paddingTopDp.toPx

    private val rowHeightDp = 90
    private val rowHeightPx = rowHeightDp.toPx

    init {
        paint.color = ContextCompat.getColor(context, R.color.grid_color)
        paint.strokeWidth = (2).toPx
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val rowHeight = rowHeightPx
        for (i in 0 until rowsCount) {
            val y = rowHeight * i.toFloat() + paddingTopPx
            canvas.drawLine(0f, y, width.toFloat(), y, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val requestedWidth = MeasureSpec.getSize(widthMeasureSpec)
        val requestedWidthMode = MeasureSpec.getMode(widthMeasureSpec)

        val requestedHeight = MeasureSpec.getSize(heightMeasureSpec)
        val requestedHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        val desiredWidth = rowHeightPx.toInt()
        val desiredHeight = (rowHeightPx * (rowsCount - 1) + paddingTopPx).toInt()

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

    private val Number.toPx
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
        )
}
