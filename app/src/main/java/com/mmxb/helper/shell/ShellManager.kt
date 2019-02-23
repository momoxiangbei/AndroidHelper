package com.mmxb.helper.shell

import android.annotation.SuppressLint
import com.jaredrummler.android.shell.Shell
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Consumer


class ShellManager private constructor() {
    companion object {
        val instance: ShellManager = ShellManager()
    }

    @SuppressLint("CheckResult")
    fun run(shellCommand: String, callBack: CallBack) {
        Observable.create(ObservableOnSubscribe<String> {
            val result = Shell.SU.run(shellCommand)
            if (result.isSuccessful) {
                ShellResultParse.getTopActivity(result.getStdout())
            }
        }).subscribe(Consumer {
            callBack.success(it)
        })
    }

}