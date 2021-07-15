package org.cuongnv.swingui.utils

object TextUtils {
    fun isEmpty(text: String?): Boolean {
        return text == null || text.isEmpty()
    }

    fun standardPath(path: String): String {
        return if (isEmpty(path)) path else path.replace("\\", "\\\\")
    }
}