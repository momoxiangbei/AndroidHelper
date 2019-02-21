package com.mmxb.mhelper

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout

import com.mmxb.mhelper.floatwindow.FloatWindowService

/**
 * Created by mmxb on 2019/2/20
 */
class MainWindow @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private var manager: WindowManager? = null
    private var params: WindowManager.LayoutParams? = null

    init {
        initView()
        initManager()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.float_window_layout, this)
    }

    @Suppress("DEPRECATION")
    private fun initManager() {
        manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        params = WindowManager.LayoutParams()
        params!!.gravity = Gravity.TOP
        params!!.width = WindowManager.LayoutParams.MATCH_PARENT
        params!!.height = WindowManager.LayoutParams.WRAP_CONTENT
        //        // 设置Window flag
        params!!.flags = (
//                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or   //可使用FLAG_DISMISS_KEYGUARD选项直接解除非加锁的锁屏状态。此选项只用于最顶层的全屏幕窗口。
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL   //必须  设置窗口不拦截窗口范围之外事件
                        or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH  // 必须  设置在有FLAG_NOT_TOUCH_MODAL属性时，窗口之外事件发生时自己也获取事件
                        or WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params!!.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            params!!.type = WindowManager.LayoutParams.TYPE_PHONE
        }
    }

    fun show() {
        manager!!.addView(this, params)
    }

    fun remove() {
        manager!!.removeView(this)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK || event.keyCode == KeyEvent.KEYCODE_SETTINGS) {
            if (manager != null) {
                remove()
                FloatWindowService.showFloatWindow()
            }
        }
        return super.dispatchKeyEvent(event)
    }

    fun setWindowManager(manager: WindowManager) {
        this.manager = manager
    }
}






















