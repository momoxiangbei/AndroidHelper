package com.mmxb.helper.util

import android.app.ActivityManager
import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import com.mmxb.helper.HelperApplication
import com.mmxb.helper.accessibilityservice.HelperAccessibilityService

object ServiceUtil {
    /**
     * 判断服务是否开启
     *
     * @return
     */
    fun isServiceRunning(context: Context, className: String?): Boolean {
        if (className == null) {
            return false
        }
        var isRunning: Boolean = false
        val activityManager: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        // todo
        val serviceList: List<ActivityManager.RunningServiceInfo> = activityManager.getRunningServices(30)
        if (serviceList.isEmpty()) {
            return false
        }
        serviceList.forEach { item ->
            if (item.service.className == className) {
                isRunning = true
                // todo
                return@forEach
            }
        }
        return isRunning
    }

    fun isAccessibilitySettingsOn(): Boolean {
        // todo
        var accessibilityEnabled = 0
        val service = "com.mmxb.mhelper/" + HelperAccessibilityService::class.java.canonicalName
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    HelperApplication.instance.applicationContext.contentResolver,
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Settings.SettingNotFoundException) {
        }

        val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')

        if (accessibilityEnabled == 1) {
            val settingValue = Settings.Secure.getString(
                    HelperApplication.instance.applicationContext.contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue)
                while (mStringColonSplitter.hasNext()) {
                    val accessibilityService = mStringColonSplitter.next()
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}