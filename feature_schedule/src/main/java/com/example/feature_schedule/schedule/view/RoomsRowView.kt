package com.example.feature_schedule.schedule.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.postgraduate.cabinet.feature_schedule.R
import kotlin.math.min

class RoomsRowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val fontSizeSp = 14
    private val heightPx = fontSizeSp.toPx

    private val cellInnerPaddingDp = 10
    private val cellInnerPaddingPx = cellInnerPaddingDp.toPx

    private val widthDp = 90
    private val widthPx = widthDp.toPx

    private val roomColoredRectHeightDp = 4f
    private val roomColoredRectHeightPx = roomColoredRectHeightDp.toPx

    var rooms: Array<String> = arrayOf()
        set(value) {
            field = value
            requestLayout()
        }
    private var roomsCount = rooms.size

    var colors: List<Int> = List(rooms.size) { Color.TRANSPARENT }

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paintColored = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.color = ContextCompat.getColor(context, R.color.text_color_grid_city)
        paint.textSize = heightPx
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val rowWidth = widthPx
        var x: Float

        for (i in rooms.indices) {
            x = rowWidth * i.toFloat()
            canvas.drawText(rooms[i], x + cellInnerPaddingPx, heightPx, paint)

            paintColored.color = colors[i]

            canvas.drawRect(
                x + cellInnerPaddingPx,
                heightPx + roomColoredRectHeightPx,
                x + rowWidth - cellInnerPaddingPx,
                heightPx + 2 * roomColoredRectHeightPx,
                paintColored
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val requestedWidth = MeasureSpec.getSize(widthMeasureSpec)
        val requestedWidthMode = MeasureSpec.getMode(widthMeasureSpec)

        val requestedHeight = MeasureSpec.getSize(heightMeasureSpec)
        val requestedHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        val desiredWidth: Int = (widthPx * rooms.size).toInt()
        val desiredHeight: Int = (heightPx + 2 * roomColoredRectHeightPx).toInt()

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
