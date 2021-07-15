package org.cuongnv.swingui.anim

interface AnimateValue<T> {
    fun onAnimate(from: T, to: T, interpolator: Float): T
}