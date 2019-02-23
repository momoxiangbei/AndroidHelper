package com.mmxb.helper.main.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.mmxb.helper.R

class AppSettingView : LinearLayout {
    lateinit var currentActivity: TextView

    constructor(context: Context?) : super(context) {
        // todo
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    private fun initView() {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_app_setting, this, false)
        addView(view)
    }

    public fun setAppInfo(activity: String) {
        // todo
        currentActivity?.text = activity
    }
}