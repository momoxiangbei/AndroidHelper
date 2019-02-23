package com.mmxb.helper.floatwindow

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

import com.mmxb.helper.main.ui.MainWindow
import com.mmxb.helper.R

/**
 * Created by mmxb on 2019/2/20
 */
class FloatWindow @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    // todo 弹性动画

    private var manager: WindowManager? = null
    private var params: WindowManager.LayoutParams? = null
    private var rawX: Int = 0
    private var rawY: Int = 0

    init {
        initView()
        initManager()
    }


    private fun initView() {
        val textView = TextView(context)
        textView.setText(R.string.helper)
        textView.setBackgroundColor(Color.GREEN)
        textView.setOnClickListener {
            remove()
            MainWindow(context).show()
        }
        addView(textView)
    }

    @Suppress("DEPRECATION")
    private fun initManager() {
        manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        params = WindowManager.LayoutParams()
        params!!.gravity = Gravity.END
        params!!.width = WindowManager.LayoutParams.WRAP_CONTENT
        params!!.height = WindowManager.LayoutParams.WRAP_CONTENT
        //  params.flags 这两个属性很重要，避免了悬浮球全屏显示
        params!!.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
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

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                rawX = event.rawX.toInt()
                rawY = event.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                params!!.x = params!!.x + (event.rawX - rawX).toInt()
                params!!.y = params!!.y + (event.rawY - rawY).toInt()
                rawX = event.rawX.toInt()
                rawY = event.rawY.toInt()
                manager!!.updateViewLayout(this, params)
            }
            else -> {
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
