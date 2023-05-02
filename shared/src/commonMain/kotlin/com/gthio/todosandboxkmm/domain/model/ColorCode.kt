package com.gthio.todosandboxkmm.domain.model

import arrow.core.raise.Raise
import arrow.core.raise.ensure
import arrow.core.raise.recover

/**
 * ColorCode is a simple enum class that represents a color code.
 * It is used in the Todo model to represent the color of the Todo.
 * The color code is used to determine the color of the Todo in the UI.
 */
enum class ColorCode(val code: Int) {
    WHITE(0), BLUE(1), RED(2), YELLOW(3);

    fun toColorInt(): Int {
        return when (this) {
            WHITE -> 0xFFFFFFFF.toInt()
            BLUE -> 0xFF2196F3.toInt()
            RED -> 0xFFF44336.toInt()
            YELLOW -> 0xFFFFEB3B.toInt()
        }
    }

    companion object {
        /**
         * Returns a ColorCode from a given code.
         * @param code The code to convert to a ColorCode.
         * @return The ColorCode that corresponds to the given code.
         */
        fun fromCode(code: Int): ColorCode {
            return recover({ findColor(code) }) { WHITE }
        }

        private object CodeOutOfBound

        /**
         * Returns a ColorCode from a given code.
         * @param code The code to convert to a ColorCode.
         * @return The ColorCode that corresponds to the given code.
         * @throws CodeOutOfBound if the given code is not between 0 and 3.
         */
        private fun Raise<CodeOutOfBound>.findColor(code: Int): ColorCode {
            ensure(code >= 0) { CodeOutOfBound }
            ensure(code <= 3) { CodeOutOfBound }

            return when (code) {
                0 -> WHITE
                1 -> BLUE
                2 -> RED
                else -> YELLOW
            }
        }
    }
}