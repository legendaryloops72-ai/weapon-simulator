package com.weapon.simulator.ui

/** Shared width thresholds for reflowing layouts without changing the compact phone design. */
internal object ResponsiveLayoutPolicy {
    const val WideWindowBreakpointDp = 600
    const val MaxContentWidthDp = 1200
    const val MaxSingleColumnWidthDp = 960

    fun usesWideLayout(widthDp: Int): Boolean = widthDp >= WideWindowBreakpointDp

    fun catalogCellMinimumWidthDp(widthDp: Int): Int =
        if (usesWideLayout(widthDp)) 190 else 140

    fun galleryCellMinimumWidthDp(widthDp: Int): Int =
        if (usesWideLayout(widthDp)) 176 else 110
}
