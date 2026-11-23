package com.pklein.mariage.data.model

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class GameNamesTest {
    @Test
    fun `default names are Alice, Bob and Nox`() {
        assertEquals(GameNames("Alice", "Bob", "Nox"), GameNames())
    }

    @Test
    fun `of keeps the names that were given`() {
        assertEquals(GameNames("Léa", "Tom", "Zorg"), GameNames.of("Léa", "Tom", "Zorg"))
    }

    @Test
    fun `of trims the names`() {
        assertEquals(GameNames("Léa", "Tom", "Zorg"), GameNames.of("  Léa ", "Tom  ", " Zorg"))
    }

    @Test
    fun `of falls back to the default for a missing or blank name`() {
        assertEquals(GameNames("Alice", "Bob", "Nox"), GameNames.of(null, "", "   "))
        assertEquals(GameNames("Léa", "Bob", "Nox"), GameNames.of("Léa", null, null))
    }

    @Test
    fun `asFormatArgs lists bride, groom and villain in placeholder order`() {
        assertArrayEquals(arrayOf("Léa", "Tom", "Zorg"), GameNames("Léa", "Tom", "Zorg").asFormatArgs())
    }
}
