package com.pklein.mariage.data.model

const val GAME_NAME_MAX_LENGTH = 20

private const val DEFAULT_BRIDE = "Alice"
private const val DEFAULT_GROOM = "Bob"
private const val DEFAULT_VILLAIN = "Nox"

data class GameNames(
    val bride: String = DEFAULT_BRIDE,
    val groom: String = DEFAULT_GROOM,
    val villain: String = DEFAULT_VILLAIN,
) {
    /** The values of the %1$s, %2$s and %3$s placeholders every story string uses for the names. */
    fun asFormatArgs(): Array<String> = arrayOf(bride, groom, villain)

    companion object {
        fun of(
            bride: String?,
            groom: String?,
            villain: String?,
        ): GameNames =
            GameNames(
                bride = bride?.trim().orEmpty().ifEmpty { DEFAULT_BRIDE },
                groom = groom?.trim().orEmpty().ifEmpty { DEFAULT_GROOM },
                villain = villain?.trim().orEmpty().ifEmpty { DEFAULT_VILLAIN },
            )
    }
}
