package org.cuongnv.swingui.anim

class FloatAnimate : AnimateValue<Float> {
    override fun onAnimate(from: Float, to: Float, interpolator: Float): Float {
        return from + (to - from) * interpolator
    }
}