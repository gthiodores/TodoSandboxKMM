package com.gthio.todosandboxkmm.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals

class ColorCodeTest {
    @Test
    fun getWhite() {
        val code = 0
        val colorCode = ColorCode.fromCode(code)
        assertEquals(colorCode, ColorCode.WHITE)
    }

    @Test
    fun getBlue() {
        val code = 1
        val colorCode = ColorCode.fromCode(code)
        assertEquals(colorCode, ColorCode.BLUE)
    }

    @Test
    fun getRed() {
        val code = 2
        val colorCode = ColorCode.fromCode(code)
        assertEquals(colorCode, ColorCode.RED)
    }

    @Test
    fun getYellow() {
        val code = 3
        val colorCode = ColorCode.fromCode(code)
        assertEquals(colorCode, ColorCode.YELLOW)
    }

    @Test
    fun getDefault() {
        val code = 4
        val colorCode = ColorCode.fromCode(code)
        assertEquals(colorCode, ColorCode.WHITE)
    }
}