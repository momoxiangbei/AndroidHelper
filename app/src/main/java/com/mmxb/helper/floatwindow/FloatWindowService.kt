package com.mmxb.helper.floatwindow

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

/**
 * Created by mmxb on 2019/2/20
 */
class FloatWindowService : Service() {

    // todo gc问题
    private var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        context = this
        // todo service？ lifecycle
        floatWindow = FloatWindow(this)
        floatWindow.show()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        // todo
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    companion object {
        lateinit var floatWindow: FloatWindow
        fun showFlotWindow() {
            floatWindow.show()
        }
    }

}
