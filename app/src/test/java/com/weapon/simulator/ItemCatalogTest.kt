package com.weapon.simulator

import org.junit.Assert.assertTrue
import org.junit.Test

class ItemCatalogTest {
    @Test
    fun catalogContainsCoreSections() {
        assertTrue("The initial app must expose all three product sections", Section.entries.containsAll(listOf(Section.Weapons, Section.Energy, Section.Barber)))
    }
}
