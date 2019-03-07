package com.mmxb.helper.util

import android.content.Context

object ScreenUtil {
    fun getSreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun getSreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }
}