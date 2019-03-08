package com.mmxb.helper.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent


class MyAccessibilityService : AccessibilityService() {


    override fun onCreate() {
        super.onCreate()
        Log.d("momotest", "onCreate")

    }

    override fun onInterrupt() {
        Log.d("momotest", "onInterrupt")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        var type = event?.eventType
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)

            Log.d("momotest", event?.packageName.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("momotest", "onDestroy")
    }
}