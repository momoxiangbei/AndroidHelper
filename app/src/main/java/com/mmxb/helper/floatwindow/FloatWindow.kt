package com.mmxb.helper.floatwindow

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.ImageView
import com.mmxb.helper.R
import com.mmxb.helper.main.ui.MainWindow
import com.mmxb.helper.util.ScreenUtil

/**
 * Created by mmxb on 2019/2/20
 */
class FloatWindow @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ImageView(context, attrs, defStyleAttr) {

    private var manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var params = WindowManager.LayoutParams()
    private var rawX = 0
    private var rawY = 0
    private var startRawX = 0
    private var startRawY = 0

    init {
        initView()
        initManager()
    }


    private fun initView() {
        setOnClickListener {
            remove()
            MainWindow(context).show()
        }
        setImageResource(R.drawable.ic_float_window)
    }

    @Suppress("DEPRECATION")
    private fun initManager() {
        // 默认的gravity  Gravity.NO_GRAVITY ，坐标的原点在屏幕中间
        params.x = ScreenUtil.getSreenHeight(context) / 2
        params.y = ScreenUtil.getSreenHeight(context) / 5
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        //  params.flags 这两个属性很重要，避免了悬浮球全屏显示
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        //设置效果为背景透明.
        params.format = PixelFormat.RGBA_8888
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE
        }
    }

    fun show() {
        manager.addView(this, params)
    }

    fun remove() {
        manager.removeView(this)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                rawX = event.rawX.toInt()
                rawY = event.rawY.toInt()
                startRawX = rawX
                startRawY = rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = (event.rawX - rawX).toInt()
                val moveY = (event.rawY - rawY).toInt()
                params.x = params.x + moveX
                params.y = params.y + moveY
                rawX = event.rawX.toInt()
                rawY = event.rawY.toInt()
                manager.updateViewLayout(this, params)
            }
            MotionEvent.ACTION_UP -> {
                if (Math.abs(startRawX - rawX) > 8 || Math.abs(startRawY - rawY) > 8) {
                    scrollToSide()
                    // 消费掉事件，也就不会调用到view的onclick方法
                    return true
                } else {
                    return super.dispatchTouchEvent(event)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun scrollToSide() {
        if (rawX > ScreenUtil.getSreenWidth(context) / 2) {
            params.x = ScreenUtil.getSreenWidth(context) / 2
        } else {
            params.x = -ScreenUtil.getSreenWidth(context) / 2
        }
        manager.updateViewLayout(this, params)
    }
}
