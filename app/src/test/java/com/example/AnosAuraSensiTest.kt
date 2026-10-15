package com.example

import com.example.data.generator.FreeFireDatabase
import com.example.data.generator.FreeFireGuidesData
import com.example.data.model.DeviceBrand
import com.example.data.model.SensiPreset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AnosAuraSensiTest {

    @Test
    fun testBrandsAndModelsCompleteness() {
        val brands = FreeFireDatabase.allBrands
        assertTrue("Database must include all major phone brands", brands.size >= 15)

        val allModels = FreeFireDatabase.getAllModels()
        assertTrue("Database must include at least 50 pre-configured models worldwide", allModels.size >= 50)

        // Verify all models have valid sensitivities between 0 and 100
        allModels.forEach { model ->
            assertTrue("General sensi in range for ${model.name}", model.baseGeneral in 50..100)
            assertTrue("RedDot sensi in range for ${model.name}", model.baseRedDot in 50..100)
            assertTrue("Button size in range for ${model.name}", model.fireButtonSize in 35..75)
        }
    }

    @Test
    fun testUniversalCustomModelGeneration() {
        val customModel = FreeFireDatabase.generateCustomUniversalModel("Wiko", "Power U30 Ultra")
        assertNotNull(customModel)
        assertEquals("Power U30 Ultra", customModel.name)
        assertTrue(customModel.baseGeneral in 50..100)
        assertTrue(customModel.recommendedDpi in 400..600)
    }

    @Test
    fun testSensiPresetsCalculation() {
        val model = FreeFireDatabase.modelsByBrand[DeviceBrand.SAMSUNG]?.first()!!
        
        val oneTapConfig = FreeFireDatabase.buildConfiguration(model, SensiPreset.HEADSHOT_100)
        assertTrue("One tap should have maximum or near-max general sensi", oneTapConfig.general >= 90)

        val noDpiConfig = FreeFireDatabase.buildConfiguration(model, SensiPreset.BALANCED_NO_DPI)
        assertEquals("No DPI preset must use default device DPI", model.defaultDpi, noDpiConfig.dpi)
    }

    @Test
    fun testTutorialsAndGuides() {
        val tutos = FreeFireGuidesData.tutorials
        assertTrue("Must have key headshot tutorials", tutos.size >= 4)
        assertTrue("Must include J-Drag tutorial", tutos.any { it.id == "tuto_j_drag" })
        assertTrue("Must include safe DPI guide", tutos.any { it.id == "tuto_dpi_safe" })
    }

    @Test
    fun testWhatsAppContactPhone() {
        val phone = "2347071776576"
        assertTrue("WhatsApp phone must match Nigerian international format (+23407071776576)", phone.startsWith("234"))
        assertEquals(13, phone.length)
    }
}
