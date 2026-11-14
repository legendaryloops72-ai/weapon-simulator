package com.weapon.simulator

import com.weapon.simulator.model.Section
import com.weapon.simulator.model.catalogItems
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ItemCatalogTest {
    @Test
    fun catalogContainsCoreSections() {
        assertTrue(
            "The app must expose weapons and barber sections only",
            Section.entries.containsAll(listOf(Section.Weapons, Section.Barber))
        )
        assertEquals("Energy section must be removed", 2, Section.entries.size)
    }

    @Test
    fun catalogItemCountsMatchRequirements() {
        val weapons = catalogItems.filter { it.section == Section.Weapons }
        val barber = catalogItems.filter { it.section == Section.Barber }

        assertEquals("Weapons section must contain exactly 23 items", 23, weapons.size)
        assertEquals("Barber Tools section must contain exactly 5 items", 5, barber.size)
        assertEquals("Total catalog must have exactly 28 items", 28, catalogItems.size)
    }

    @Test
    fun allItemsHaveValidProperties() {
        catalogItems.forEach { item ->
            assertTrue("Item name must not be blank: ${item.name}", item.name.isNotBlank())
            assertTrue("Item category must not be blank: ${item.name}", item.category.isNotBlank())
            assertTrue("Drawable resource must be non-zero: ${item.name}", item.drawableRes != 0)
            assertTrue("Asset path must not be blank: ${item.name}", item.assetPath.isNotBlank())
        }
    }
}
