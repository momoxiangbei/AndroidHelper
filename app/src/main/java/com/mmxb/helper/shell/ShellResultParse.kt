package com.mmxb.helper.shell

import com.mmxb.helper.bean.AppInfoBean
import java.util.regex.Pattern

object ShellResultParse {
    fun getAppInfo(result: String): AppInfoBean {
        val pattern = Pattern.compile("ACTIVITY .*.pid.*")
        val matcher = pattern.matcher(result)
        var activityInfo = ""
        while (matcher.find()) {
            activityInfo = matcher.group()
        }
        val appInfo = AppInfoBean()
        appInfo.packageName = activityInfo.substringAfter("ACTIVITY ").substringBefore("/")
        appInfo.currrentActivity = activityInfo.substringAfter("ACTIVITY ").substringBefore(" ").replace("/", ".")
        appInfo.pid = activityInfo.substringAfter("pid=")
        return appInfo
    }
}
