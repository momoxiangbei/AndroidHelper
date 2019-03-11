package com.mmxb.helper.shell

import android.annotation.SuppressLint
import android.widget.Toast
import com.jaredrummler.android.shell.Shell
import com.mmxb.helper.HelperApplication
import com.mmxb.helper.util.RootUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


object ShellManager {

    @SuppressLint("CheckResult")
    fun run(shellCommand: String, callBack: CallBack) {
        if (!RootUtil.isRoot(HelperApplication.instance)) {
            Toast.makeText(HelperApplication.instance, "not root", Toast.LENGTH_LONG).show()
        }
        doAsync {
            val result = Shell.SU.run(shellCommand)
            if (result.isSuccessful) {
                uiThread {
                    callBack.success(result.getStdout())
                }
            }
        }
    }


}