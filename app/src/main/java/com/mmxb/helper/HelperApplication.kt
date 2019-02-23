package com.mmxb.helper

import android.app.Application

class HelperApplication : Application(){
    companion object {
        lateinit var instance: HelperApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}