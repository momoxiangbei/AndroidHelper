package com.mmxb.helper.main.ui

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup


class MainPagerAdapter(context: Context) : PagerAdapter() {
    var appInfoView = AppInfoView(context)
    var appSettingView = AppSettingView(context)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return if (position == 0) {
            container.addView(appInfoView)
            appInfoView
        } else {
            container.addView(appSettingView)
            appSettingView
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeViewAt(position)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return 2
    }
}