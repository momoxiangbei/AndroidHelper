package com.mmxb.helper.main.ui

import android.content.Context
import android.os.Build
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import com.mmxb.helper.R
import com.mmxb.helper.bean.AppInfoBean
import com.mmxb.helper.floatwindow.FloatWindowService
import com.mmxb.helper.shell.CallBack
import com.mmxb.helper.shell.ShellManager
import com.mmxb.helper.shell.ShellResultParse
import kotlinx.android.synthetic.main.layout_float_window.view.*

/**
 * Created by mmxb on 2019/2/20
 */
class MainWindow @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var manager: WindowManager
    private lateinit var params: WindowManager.LayoutParams
    private lateinit var viewpager: ViewPager
    private lateinit var pagerAdapter: MainPagerAdapter
    private var appInfo = AppInfoBean()

    init {
        initView()
        initManager()
        initData()
    }

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_float_window, this, false)
        viewpager = view.findViewById(R.id.viewpager)
        pagerAdapter = MainPagerAdapter(context)
        viewpager.adapter = pagerAdapter
        addView(view)
    }

    private fun initManager() {
        manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        params = WindowManager.LayoutParams()
        params.gravity = Gravity.TOP
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.flags = (
//                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or   //可使用FLAG_DISMISS_KEYGUARD选项直接解除非加锁的锁屏状态。此选项只用于最顶层的全屏幕窗口。
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL          //必须  设置窗口不拦截窗口范围之外事件
                        or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH  // 必须  设置在有FLAG_NOT_TOUCH_MODAL属性时，窗口之外事件发生时自己也获取事件
                        or WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE
        }
    }

    private fun initData() {
        ShellManager.run("dumpsys activity top", object : CallBack() {
            override fun success(result: String) {
                appInfo = ShellResultParse.getAppInfo(result)
                appIconIV.setImageDrawable(context.packageManager.getApplicationIcon(appInfo.packageName))
                appPackageTV.text = appInfo.packageName
//                appNameTV.text = context.packageManager.getPackageInfo(appInfo.packageName, 0)

                pagerAdapter.appInfoView.setAppInfo(appInfo.currentActivity)
                pagerAdapter.notifyDataSetChanged()
            }
        })


    }

    fun show() {
        manager.addView(this, params)
    }

    fun remove() {
        manager.removeView(this)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK || event.keyCode == KeyEvent.KEYCODE_SETTINGS) {
            remove()
            FloatWindowService.showFloatWindow()
        }
        return super.dispatchKeyEvent(event)
    }

    fun setWindowManager(manager: WindowManager) {
        this.manager = manager
    }
}






















