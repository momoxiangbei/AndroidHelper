package com.mmxb.helper.shell

import java.util.regex.Pattern

object ShellResultParse {
    fun getTopActivity(result: String): String {
        val pattern = Pattern.compile("ACTIVITY .*.pid.*")
        val matcher = pattern.matcher(result)
        return matcher.group().split("")[1]
    }
}
