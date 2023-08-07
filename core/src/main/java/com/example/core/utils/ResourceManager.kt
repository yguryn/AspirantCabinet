package com.example.core.utils

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceManager @Inject constructor(
    private val context: Context
) {

    fun getString(@StringRes resId: Int, vararg format: Any) = context.getString(resId, *format)

    fun getContext() = context
}