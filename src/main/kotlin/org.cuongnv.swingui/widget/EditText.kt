package org.cuongnv.swingui.widget

import java.awt.Color
import java.awt.Graphics
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JTextField
import javax.swing.border.EmptyBorder
import org.cuongnv.swingui.anim.SinInterpolator
import org.cuongnv.swingui.anim.ValueAnimator
import org.cuongnv.swingui.res.Colors

class EditText : JTextField(), FocusListener {
    companion object {
        private val COLOR_LINE_UN_FOCUS = Color.decode("#eeeeee")
        private val COLOR_LINE_FOCUS = Colors.colorPrimary
        private val COLOR_HINT_TEXT = Colors.hintColor
    }

    private var hint: String? = null
    private var animator: ValueAnimator<Float>? = null
    private var focusLineWidth: Float = 0f

    init {
        addFocusListener(this)
        border = EmptyBorder(5, 5, 5, 5)
    }

    fun setHint(hint: String?) {
        if (this.hint != hint) {
            this.hint = hint
            repaint()
        }
        focusLost(null)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.color = COLOR_LINE_UN_FOCUS
        g.drawRect(0, height - 1, width, 1)
        if (focusLineWidth > 0) {
            g.color = COLOR_LINE_FOCUS
            g.drawRect(((width - focusLineWidth) / 2).toInt(), height - 1, focusLineWidth.toInt(), 1)
        }

        if (text.isEmpty() && !hint.isNullOrEmpty()) {
            g.font = font
            g.color = COLOR_HINT_TEXT
            g.drawString(hint!!, 5, getFontMetrics(font).height)
        }
    }

    override fun focusGained(e: FocusEvent) {
        showAnim()
    }

    override fun focusLost(e: FocusEvent?) {
        hideAnim()
    }

    private fun showAnim() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
        }
        animator = ValueAnimator.ofFloat(focusLineWidth, width.toFloat())
        animator!!.setDuration(250)
            .setInterpolator(SinInterpolator())
            .setUpdateListener { value: Float ->
                focusLineWidth = value
                revalidate()
                repaint()
            }
            .start()
    }

    private fun hideAnim() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
        }
        animator = ValueAnimator.ofFloat(focusLineWidth, 0f)
        animator!!.setDuration(250)
            .setInterpolator(SinInterpolator())
            .setUpdateListener { value: Float ->
                focusLineWidth = value
                revalidate()
                repaint()
            }
            .start()
    }
}