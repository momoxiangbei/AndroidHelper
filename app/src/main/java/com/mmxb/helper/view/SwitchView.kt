package com.mmxb.helper.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.FrameLayout
import com.mmxb.helper.R
import kotlinx.android.synthetic.main.layout_switch_view.view.*

class SwitchView : FrameLayout {

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchView)
        permissionTitle.text = typedArray.getString(R.styleable.SwitchView_text_title)
        permissionInfo.text = typedArray.getString(R.styleable.SwitchView_text_info)
        typedArray.recycle()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_switch_view, this, true)
        // todo status
        permissionButton.isChecked = true


    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener) {
        permissionButton.setOnCheckedChangeListener(listener)
    }

    fun setChecked(checked: Boolean) {
        permissionButton.isChecked = checked
    }
}
