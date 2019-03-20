package com.mmxb.helper.accessibilityservice

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.mmxb.helper.main.ui.MainWindow


class HelperAccessibilityService : AccessibilityService() {

    override fun onCreate() {
        super.onCreate()
        Log.d("momotest", "onCreate")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("momotest", "onServiceConnected")
    }

    override fun onInterrupt() {
        Log.d("momotest", "onInterrupt")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (MainWindow.instance == null) {
            return
        }
        val type = event?.eventType
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Log.d("momotest", event.packageName.toString())
            MainWindow.instance?.updateUi(event.packageName.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("momotest", "onDestroy")
    }

}