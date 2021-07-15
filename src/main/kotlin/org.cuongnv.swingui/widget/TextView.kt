package org.cuongnv.swingui.widget

import java.awt.FlowLayout
import javax.swing.SwingConstants
import java.awt.Font
import java.awt.Color
import java.awt.Container
import javax.swing.JLabel
import javax.swing.border.Border

class TextView : Container {
    private var mLabel: JLabel

    constructor() {
        mLabel = JLabel()
        initTextView()
    }

    constructor(content: String?) {
        mLabel = JLabel(content)
        initTextView()
    }

    private fun initTextView() {
        layout = FlowLayout(FlowLayout.LEFT, 0, 0)
        mLabel.verticalAlignment = SwingConstants.CENTER
        add(mLabel)
    }

    override fun setFont(font: Font) {
        mLabel.font = font
    }

    var text: String?
        get() = mLabel.text
        set(content) {
            mLabel.text = content
        }

    override fun setForeground(color: Color) {
        mLabel.foreground = color
    }

    fun setBorder(border: Border?) {
        mLabel.border = border
    }
}