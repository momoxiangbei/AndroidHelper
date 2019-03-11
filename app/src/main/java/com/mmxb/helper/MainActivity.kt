package com.mmxb.helper

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mmxb.helper.floatwindow.FloatWindowService
import com.mmxb.helper.util.RootUtil
import com.mmxb.helper.util.ServiceUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val isShowingFloatWindow = ServiceUtil.isServiceRunning(this, FloatWindowService::class.java.canonicalName)
        setOpenText(!isShowingFloatWindow)
    }

    fun openFloatWindow(view: View) {
        val isShowingFloatWindow = ServiceUtil.isServiceRunning(this, FloatWindowService::class.java.canonicalName)
        if (isShowingFloatWindow) {  // 关闭
            // todo
            stopService(Intent(this@MainActivity, FloatWindowService::class.java))
            setOpenText(true)
        } else {  // 打开
            // 悬浮窗权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                switchFloatWindow.setViewChecked(false)
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                return
            }
            switchFloatWindow.setViewChecked(true)


            // 无障碍服务
            if (!ServiceUtil.isAccessibilitySettingsOn()) {
                switchAccessibilityService.setViewChecked(false)
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                return
            }
            switchAccessibilityService.setViewChecked(true)

            // root权限
            val isRoot = RootUtil.isRoot(this)
            switchRoot.setViewChecked(isRoot)

            startFloatWindowService()
            setOpenText(false)
        }
    }

    private fun startFloatWindowService() {
        startService(Intent(this@MainActivity, FloatWindowService::class.java))
    }

    private fun setOpenText(open: Boolean) {
        openBtn.text = if (open) getString(R.string.open_float_window) else getString(R.string.close_float_window)
    }
}
