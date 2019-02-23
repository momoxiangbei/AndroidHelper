package com.mmxb.helper.shell

import android.util.Log
import android.widget.Toast
import com.jaredrummler.android.shell.Shell
import com.mmxb.helper.HelperApplication


object ShellUtil {
    fun getTopActivity() {
        if (isRoot()) {
            val result = Shell.SU.run("dumpsys activity top")
            // todo
            Log.d("momolog", result.getStdout())
            if (!result.isSuccessful) {
                Toast.makeText(HelperApplication.instance, "execute shell command failed", Toast.LENGTH_LONG).show()
                return
            }
            ShellResultParse.getTopActivity(result.getStdout())
        }
    }

    fun isRoot(): Boolean {
        return if (Shell.SU.available()) {
            true
        } else {
            Toast.makeText(HelperApplication.instance, "not root", Toast.LENGTH_LONG).show()
            false
        }
    }
}