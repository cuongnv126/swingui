package org.cuongnv.swingui.utils

import javax.swing.ImageIcon
import java.util.Objects

object ViewUtils {
    fun getImageIcon(path: String?): ImageIcon {
        return ImageIcon(Objects.requireNonNull(this::class.java.classLoader.getResource(path)))
    }
}