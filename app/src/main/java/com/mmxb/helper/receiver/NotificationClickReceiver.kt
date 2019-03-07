package com.mmxb.helper.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationClickReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val startIntent = context?.packageManager?.getLaunchIntentForPackage("com.mmxb.helper")
        startIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(startIntent)

    }
}