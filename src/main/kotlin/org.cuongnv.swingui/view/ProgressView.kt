package org.cuongnv.swingui.view

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.Arc2D
import javax.swing.JComponent
import org.cuongnv.swingui.anim.LinearInterpolator
import org.cuongnv.swingui.anim.ValueAnimator
import org.cuongnv.swingui.res.Colors

class ProgressView : JComponent() {
    private var mStrokeWidth = 2
    private var mStrokeColor: Color
    private var mAnimator: ValueAnimator<Float>? = null
    private var mProgress = 0.9f
    private var mAngle = 0f
    fun setProgress(progress: Float) {
        mProgress = progress
        repaint()
    }

    fun setStroke(strokeWidth: Int) {
        mStrokeWidth = strokeWidth
        repaint()
    }

    fun setColor(color: Color) {
        mStrokeColor = color
    }

    fun startProgress() {
        isVisible = true
        if (mAnimator != null && mAnimator!!.isRunning) {
            mAnimator!!.cancel()
        }
        mAnimator = ValueAnimator.ofFloat(1f, 0f)
        mAnimator!!.setDuration(2000)
            .setInterpolator(LinearInterpolator())
            .setRepeat(ValueAnimator.REPEAT_ALL)
            .setUpdateListener { value: Float ->
                mAngle = value * 360
                repaint()
            }.start()
    }

    fun cancelProgress() {
        isVisible = false
        if (mAnimator != null && mAnimator!!.isRunning) {
            mAnimator!!.cancel()
        }
    }

    override fun removeNotify() {
        cancelProgress()
        super.removeNotify()
    }

    private var mW = 0.0
    private var mH = 0.0
    override fun setPreferredSize(preferredSize: Dimension) {
        super.setPreferredSize(preferredSize)
        mW = preferredSize.getWidth()
        mH = preferredSize.getHeight()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.color = mStrokeColor
        g2d.stroke = BasicStroke(mStrokeWidth.toFloat())
        g2d.draw(
            Arc2D.Double(
                mStrokeWidth.toDouble(),
                mStrokeWidth.toDouble(),
                mW - 2 * mStrokeWidth,
                mH - 2 * mStrokeWidth,
                mAngle.toDouble(),
                (mProgress * 360).toDouble(),
                Arc2D.OPEN
            )
        )
    }

    init {
        mStrokeColor = Colors.colorPrimary
        cancelProgress()
    }
}