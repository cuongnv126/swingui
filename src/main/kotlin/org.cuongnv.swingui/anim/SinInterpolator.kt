package org.cuongnv.swingui.anim

import kotlin.math.sin

class SinInterpolator : Interpolator {
    override fun onInterpolator(linear: Float): Float {
        return sin(linear * Math.PI / 2).toFloat()
    }
}