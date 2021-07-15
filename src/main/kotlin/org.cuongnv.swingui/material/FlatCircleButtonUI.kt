package org.cuongnv.swingui.material

import java.awt.Color
import java.awt.Graphics
import javax.swing.AbstractButton
import javax.swing.plaf.basic.BasicButtonUI
import org.cuongnv.swingui.res.Colors

class FlatCircleButtonUI : BasicButtonUI {
    private var mHoverColor: Color? = null

    constructor() {
        light()
    }

    constructor(hoverColor: Color?) {
        mHoverColor = hoverColor
    }

    fun dark(): FlatCircleButtonUI {
        mHoverColor = Colors.hoverDark
        return this
    }

    fun light(): FlatCircleButtonUI {
        mHoverColor = Colors.hoverLight
        return this
    }

    override fun paintButtonPressed(g: Graphics, b: AbstractButton) {
        if (mHoverColor == null) {
            mHoverColor = Colors.hoverLight
        }
        g.color = mHoverColor
        g.fillOval(0, 0, b.width, b.height)
        super.paintButtonPressed(g, b)
    }
}