package com.example.feature_schedule.schedule.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.postgraduate.cabinet.feature_schedule.R
import kotlin.math.min

class ShadeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val stripeWidthDp = 8
    private val stripeWidthPx = stripeWidthDp.toPx

    private val cellWidthDp = 90
    private val cellWidthPx = cellWidthDp.toPx

    init {
        paint.color = ContextCompat.getColor(context, R.color.shade_stripe_color)
        paint.strokeWidth = stripeWidthPx
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in -15..width) {
            val x: Float = (cellWidthPx / 4 * i)
            canvas.drawLine(x + height.toFloat(), 0f, x, height.toFloat(), paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val requestedWidth = MeasureSpec.getSize(widthMeasureSpec)
        val requestedWidthMode = MeasureSpec.getMode(widthMeasureSpec)

        val requestedHeight = MeasureSpec.getSize(heightMeasureSpec)
        val requestedHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        val desiredWidth = cellWidthPx.toInt()
        val desiredHeight = cellWidthPx.toInt()

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
