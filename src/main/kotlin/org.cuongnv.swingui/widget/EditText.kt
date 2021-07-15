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
import org.cuongnv.swingui.utils.TextUtils

class EditText : JTextField(), FocusListener {
    private var mHint: String? = null
    private var mIsHint = false
    private var animator: ValueAnimator<Float>? = null
    private var mCurrentLineWidth: Float

    fun setHint(hint: String?) {
        mHint = hint
        focusLost(null)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.color = Color.decode("#eeeeee")
        g.drawRect(0, height - 1, width, 1)
        if (mCurrentLineWidth > 0) {
            g.color = Colors.colorPrimary
            g.drawRect(((width - mCurrentLineWidth) / 2).toInt(), height - 1, mCurrentLineWidth.toInt(), 1)
        }
    }

    override fun focusGained(e: FocusEvent) {
        showAnim()
        if (mIsHint) {
            text = ""
            foreground = Color.decode("#333333")
        }
    }

    override fun focusLost(e: FocusEvent?) {
        hideAnim()
        if (TextUtils.isEmpty(content)) {
            setText(mHint)
            foreground = Color.decode("#999999")
            mIsHint = true
        } else {
            mIsHint = false
        }
    }

    private val content: String
        get() = super.getText()

    override fun getText(): String {
        return if (mIsHint) "" else content
    }

    private fun showAnim() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
        }
        animator = ValueAnimator.ofFloat(mCurrentLineWidth, width.toFloat())
        animator!!.setDuration(300)
            .setInterpolator(SinInterpolator())
            .setUpdateListener { value: Float ->
                mCurrentLineWidth = value
                revalidate()
                repaint()
            }
            .start()
    }

    private fun hideAnim() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
        }
        animator = ValueAnimator.ofFloat(mCurrentLineWidth, 0f)
        animator!!.setDuration(300)
            .setInterpolator(SinInterpolator())
            .setUpdateListener { value: Float ->
                mCurrentLineWidth = value
                revalidate()
                repaint()
            }
            .start()
    }

    init {
        addFocusListener(this)
        mCurrentLineWidth = 0f
        border = EmptyBorder(5, 5, 5, 5)
    }
}