package com.mmxb.helper.util

import android.app.Application
import android.content.Context
import com.jaredrummler.android.shell.Shell
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

object RootUtil {

    /**
     * 判断app是否root，没有root会请求root权限
     */
    fun isRoot(context: Context): Boolean {
        val isRoot = Shell.SU.available()
        setRootCache(context, isRoot)
        return isRoot
    }


//    /**
//     * 判断手机是否root
//     */
//    fun isRoot(): Boolean {
//        val paths = arrayOf("/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su",
//                "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
//                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su")
//        for (path in paths) {
//            if (File(path).exists()) {
//                return true
//            }
//        }
//        return false
//    }

    fun setRootCache(context: Context, boolean: Boolean) {
        val edit = context.getSharedPreferences("helper", Context.MODE_PRIVATE).edit()
        edit.putBoolean("root", boolean)
        edit.apply()
    }

    fun getRootCache(applicationContext: Application): Boolean {
        val sp = applicationContext.getSharedPreferences("helper", Context.MODE_PRIVATE)
        return sp.getBoolean("root", false)
    }
}

