package com.mmxb.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.Toast
import com.mmxb.helper.floatwindow.FloatWindowService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_switch_view.view.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
    }

    private fun initData() {
        val sp = this.getSharedPreferences("helper", Context.MODE_PRIVATE)
        val isOpenSwitchWindow = sp.getBoolean("switchFloatWindow", false)
        switchFloatWindow.setChecked(isOpenSwitchWindow)

        if (isOpenSwitchWindow) {
            applyFloatWindowPermission()
        }

        switchFloatWindow.setOnCheckedChangeListener(OnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                applyFloatWindowPermission()
            } else {
                switchFloatWindow.setChecked(true)
                Toast.makeText(this, getString(R.string.open_float_window_permission), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun applyFloatWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                startFloatWindowService()
            } else {
                startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")), 0)
            }
        } else {
            startFloatWindowService()
        }
    }

    private fun startFloatWindowService() {
        startService(Intent(this@MainActivity, FloatWindowService::class.java))
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (Settings.canDrawOverlays(this)) {
                startFloatWindowService()
                val edit = getSharedPreferences("helper", Context.MODE_PRIVATE).edit()
                edit.putBoolean("switchFloatWindow", true)
                edit.apply()
                switchFloatWindow.setChecked(true)
            } else {
                switchFloatWindow.setChecked(false)
                Toast.makeText(this, getString(R.string.please_oppen_f_w_permission), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun close(view: View) {
        stopService(Intent(this@MainActivity, FloatWindowService::class.java))
    }

}

