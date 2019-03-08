package com.mmxb.helper.util

import android.provider.Settings
import android.text.TextUtils
import com.mmxb.helper.HelperApplication
import com.mmxb.helper.service.MyAccessibilityService

object AccessibilityServiceUtil {
    fun isAccessibilitySettingsOn(): Boolean {
        // todo
        var accessibilityEnabled = 0
        val service = "com.mmxb.mhelper/" + MyAccessibilityService::class.java.canonicalName
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