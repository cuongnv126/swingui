package org.cuongnv.swingui.material

import javax.swing.plaf.basic.BasicScrollBarUI
import java.awt.Graphics
import javax.swing.JComponent
import java.awt.Rectangle
import javax.swing.JButton
import java.awt.Dimension
import org.cuongnv.swingui.res.Colors

class FlatScrollBarUI : BasicScrollBarUI {
    private var mWidth: Int

    constructor() {
        mWidth = 5
    }

    constructor(width: Int) {
        mWidth = width
    }

    override fun paintThumb(g: Graphics, c: JComponent, thumbBounds: Rectangle) {
        g.color = Colors.colorPrimary
        g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height)
    }

    override fun createDecreaseButton(orientation: Int): JButton {
        val button = JButton()
        val zeroDim = Dimension(0, 0)
        button.preferredSize = zeroDim
        button.minimumSize = zeroDim
        button.maximumSize = zeroDim
        return button
    }

    override fun createIncreaseButton(orientation: Int): JButton {
        val button = JButton("zero button")
        val zeroDim = Dimension(0, 0)
        button.preferredSize = zeroDim
        button.minimumSize = zeroDim
        button.maximumSize = zeroDim
        return button
    }

    override fun getPreferredSize(c: JComponent): Dimension {
        return Dimension(mWidth, c.height)
    }

    override fun paintDecreaseHighlight(g: Graphics) {}
    override fun paintIncreaseHighlight(g: Graphics) {}
    override fun paintTrack(g: Graphics, c: JComponent, trackBounds: Rectangle) {}
}