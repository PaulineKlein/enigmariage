package com.pklein.mariage.presentation.combatFinal.resultat

import com.pklein.mariage.data.repository.PlayerRepository
import com.pklein.mariage.data.repository.ScoreRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class ResultsViewModelTest {
    private lateinit var scoreRepository: ScoreRepository
    private lateinit var playerRepository: PlayerRepository
    private lateinit var viewModel: ResultsViewModel

    @Before
    fun setUp() {
        scoreRepository = mockk()
        playerRepository = mockk()
        viewModel = ResultsViewModel(scoreRepository, playerRepository)
    }

    @Test
    fun `actualNbOfClue returns the value from the score repository`() {
        every { scoreRepository.actualNbOfClue } returns 4

        assertEquals(4, viewModel.actualNbOfClue)
    }

    @Test
    fun `calculateScore returns 100 when no error and no clue`() {
        givenErrorsAndClues(errors = 0, clues = 0)

        assertEquals(100, viewModel.calculateScore())
    }

    @Test
    fun `calculateScore removes 3 points per error`() {
        givenErrorsAndClues(errors = 4, clues = 0)

        assertEquals(88, viewModel.calculateScore())
    }

    @Test
    fun `calculateScore removes 5 points per clue`() {
        givenErrorsAndClues(errors = 0, clues = 3)

        assertEquals(85, viewModel.calculateScore())
    }

    @Test
    fun `calculateScore combines errors and clues`() {
        givenErrorsAndClues(errors = 2, clues = 3)

        assertEquals(79, viewModel.calculateScore())
    }

    @Test
    fun `calculateScore can be negative`() {
        givenErrorsAndClues(errors = 30, clues = 10)

        assertEquals(-40, viewModel.calculateScore())
    }

    @Test
    fun `calculateTime returns null when start time is null`() {
        givenTimes(start = null, end = "2000")

        assertNull(viewModel.calculateTime())
    }

    @Test
    fun `calculateTime returns null when start time is empty`() {
        givenTimes(start = "", end = "2000")

        assertNull(viewModel.calculateTime())
    }

    @Test
    fun `calculateTime returns null when end time is null`() {
        givenTimes(start = "1000", end = null)

        assertNull(viewModel.calculateTime())
    }

    @Test
    fun `calculateTime returns null when end time is empty`() {
        givenTimes(start = "1000", end = "")

        assertNull(viewModel.calculateTime())
    }

    @Test
    fun `calculateTime returns null when times are not numbers`() {
        givenTimes(start = "abc", end = "def")

        assertNull(viewModel.calculateTime())
    }

    @Test
    fun `calculateTime formats seconds only`() {
        givenTimes(start = "0", end = TimeUnit.SECONDS.toMillis(45).toString())

        assertEquals("45s", viewModel.calculateTime())
    }

    @Test
    fun `calculateTime formats minutes and seconds`() {
        val end = TimeUnit.MINUTES.toMillis(12) + TimeUnit.SECONDS.toMillis(30)
        givenTimes(start = "0", end = end.toString())

        assertEquals("12min 30s", viewModel.calculateTime())
    }

    @Test
    fun `calculateTime formats hours minutes and seconds`() {
        val end =
            TimeUnit.HOURS.toMillis(1) +
                TimeUnit.MINUTES.toMillis(5) +
                TimeUnit.SECONDS.toMillis(2)
        givenTimes(start = "1000", end = (1000 + end).toString())

        assertEquals("1h 5min 2s", viewModel.calculateTime())
    }

    @Test
    fun `calculateTime shows zero minutes when hours are present`() {
        val end = TimeUnit.HOURS.toMillis(2) + TimeUnit.SECONDS.toMillis(10)
        givenTimes(start = "0", end = end.toString())

        assertEquals("2h 0min 10s", viewModel.calculateTime())
    }

    @Test
    fun `isUnderThirtyMinutes is true for a short game`() {
        givenTimes(start = "0", end = TimeUnit.MINUTES.toMillis(10).toString())

        assertTrue(viewModel.isUnderThirtyMinutes())
    }

    @Test
    fun `isUnderThirtyMinutes is false for exactly thirty minutes`() {
        givenTimes(start = "0", end = TimeUnit.MINUTES.toMillis(30).toString())

        assertFalse(viewModel.isUnderThirtyMinutes())
    }

    @Test
    fun `isUnderThirtyMinutes is true one millisecond before thirty minutes`() {
        givenTimes(start = "0", end = (TimeUnit.MINUTES.toMillis(30) - 1).toString())

        assertTrue(viewModel.isUnderThirtyMinutes())
    }

    @Test
    fun `isUnderThirtyMinutes is false for a long game`() {
        givenTimes(start = "0", end = TimeUnit.HOURS.toMillis(1).toString())

        assertFalse(viewModel.isUnderThirtyMinutes())
    }

    @Test
    fun `isUnderThirtyMinutes is false when start time is null`() {
        givenTimes(start = null, end = "1000")

        assertFalse(viewModel.isUnderThirtyMinutes())
    }

    @Test
    fun `isUnderThirtyMinutes is false when end time is null`() {
        givenTimes(start = "1000", end = null)

        assertFalse(viewModel.isUnderThirtyMinutes())
    }

    @Test
    fun `isUnderThirtyMinutes is false when times are not numbers`() {
        givenTimes(start = "abc", end = "def")

        assertFalse(viewModel.isUnderThirtyMinutes())
    }

    @Test
    fun `isUnderThirtyMinutes is false when times are empty`() {
        givenTimes(start = "", end = "")

        assertFalse(viewModel.isUnderThirtyMinutes())
    }

    private fun givenErrorsAndClues(
        errors: Int,
        clues: Int,
    ) {
        every { scoreRepository.actualNbOfError } returns errors
        every { scoreRepository.actualNbOfClue } returns clues
    }

    private fun givenTimes(
        start: String?,
        end: String?,
    ) {
        every { scoreRepository.startTimeStr } returns start
        every { scoreRepository.endTimeStr } returns end
    }
}
