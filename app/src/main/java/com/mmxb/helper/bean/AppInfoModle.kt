package com.mmxb.helper.bean

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

object AppInfoModle : ViewModel() {

    // todo by lazy {}
    val packageName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}