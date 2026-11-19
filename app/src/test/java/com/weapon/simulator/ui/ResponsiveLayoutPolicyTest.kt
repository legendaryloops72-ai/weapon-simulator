package com.weapon.simulator.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ResponsiveLayoutPolicyTest {
    @Test
    fun compactPhoneWidthsKeepTheExistingLayout() {
        assertFalse(ResponsiveLayoutPolicy.usesWideLayout(360))
        assertFalse(ResponsiveLayoutPolicy.usesWideLayout(599))
        assertEquals(140, ResponsiveLayoutPolicy.catalogCellMinimumWidthDp(360))
        assertEquals(110, ResponsiveLayoutPolicy.galleryCellMinimumWidthDp(360))
    }

    @Test
    fun wideWindowsUseReflowAndLargerGridCells() {
        assertTrue(ResponsiveLayoutPolicy.usesWideLayout(600))
        assertTrue(ResponsiveLayoutPolicy.usesWideLayout(840))
        assertEquals(190, ResponsiveLayoutPolicy.catalogCellMinimumWidthDp(840))
        assertEquals(176, ResponsiveLayoutPolicy.galleryCellMinimumWidthDp(840))
    }
}
