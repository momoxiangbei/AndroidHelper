package com.mmxb.helper.util

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable

object AppInfoUtil {
    fun getAppIcon(packageName: String, context: Context): Drawable? {
        return context.packageManager.getApplicationIcon(packageName)
    }
}