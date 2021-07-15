package org.cuongnv.swingui.anim

import javax.swing.SwingUtilities

class ValueAnimator<T> private constructor() : Thread() {
    private var mFrom: T? = null
    private var mTo: T? = null
    private var mDuration: Long
    var isRunning: Boolean
        private set
    private var mListener: UpdateListener<T>? = null
    private var mInterpolator: Interpolator
    private var mAnimateValue: AnimateValue<T>? = null
    private var mRepeatCount: Int
    private var mCurrentLoop: Int

    fun interface UpdateListener<T> {
        fun onAnimatorUpdate(currentValue: T)
    }

    fun setDuration(duration: Long): ValueAnimator<T> {
        mDuration = duration
        return this
    }

    fun setUpdateListener(l: UpdateListener<T>?): ValueAnimator<T> {
        mListener = l
        return this
    }

    fun setInterpolator(i: Interpolator?): ValueAnimator<T> {
        if (i == null) {
            mInterpolator = LinearInterpolator()
        } else {
            mInterpolator = i
        }
        return this
    }

    fun setRepeat(repeat: Int): ValueAnimator<T> {
        mRepeatCount = repeat
        return this
    }

    @Synchronized
    override fun start() {
        isRunning = true
        mCurrentLoop = 0
        super.start()
    }

    fun cancel() {
        isRunning = false
        interrupt()
    }

    override fun run() {
        while (canContinue()) {
            var startTime = 0f
            val duration = mDuration.toFloat()
            updateValue(mFrom!!)
            var isInterupt = false
            while (startTime <= duration) {
                val interpolator = mInterpolator.onInterpolator(startTime / duration)
                val currentValue = mAnimateValue!!.onAnimate(mFrom!!, mTo!!, interpolator)
                updateValue(currentValue)
                try {
                    sleep(FRAMES_PER_SECOND)
                    startTime += FRAMES_PER_SECOND.toFloat()
                } catch (ignored: InterruptedException) {
                    isInterupt = true
                    break
                }
            }
            if (!isInterupt) updateValue(mTo!!)
            next()
        }
    }

    private operator fun next() {
        mCurrentLoop++
    }

    private fun canContinue(): Boolean {
        if (!isRunning) return false
        return if (mRepeatCount == REPEAT_ALL) true else mCurrentLoop < mRepeatCount
    }

    private fun updateValue(value: T) {
        if (mListener != null) {
            SwingUtilities.invokeLater { mListener!!.onAnimatorUpdate(value) }
        }
    }

    companion object {
        private const val FRAMES_PER_SECOND = (1000 / 60).toLong()
        const val REPEAT_ONCE = 1
        const val REPEAT_ALL = -1
        fun ofFloat(from: Float, to: Float): ValueAnimator<Float> {
            val animator = ValueAnimator<Float>()
            animator.mAnimateValue = FloatAnimate()
            animator.mFrom = from
            animator.mTo = to
            return animator
        }
    }

    init {
        mInterpolator = LinearInterpolator()
        mDuration = 300
        isRunning = false
        mCurrentLoop = 0
        mRepeatCount = REPEAT_ONCE
    }
}