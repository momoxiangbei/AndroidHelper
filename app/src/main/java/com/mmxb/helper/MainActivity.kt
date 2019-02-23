package com.mmxb.helper

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.mmxb.helper.floatwindow.FloatWindowService
import com.mmxb.helper.shell.ShellUtil
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun test(view: View) {
        // todo test
        toast("tes")
    }

    fun floatWindow(view: View) {
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
            } else {
                Toast.makeText(this, "请打开悬浮窗权限", Toast.LENGTH_LONG).show()
            }
        }
    }


}

