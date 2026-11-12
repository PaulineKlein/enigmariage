package com.pklein.mariage.presentation.carnetBord

import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.data.repository.PlayerRepository
import com.pklein.mariage.data.repository.TrophyRepository
import com.pklein.mariage.data.repository.UniversRepository
import com.pklein.mariage.data.repository.UniversStatus
import com.pklein.mariage.domain.IInspector
import com.pklein.mariage.domain.Univers2Path
import com.pklein.mariage.presentation.GameStep
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CarnetBordViewModelTest {
    private lateinit var universRepository: UniversRepository
    private lateinit var playerRepository: PlayerRepository
    private lateinit var trophyRepository: TrophyRepository
    private lateinit var inspector: IInspector
    private lateinit var viewModel: CarnetBordViewModel

    @Before
    fun setUp() {
        universRepository = mockk()
        playerRepository = mockk()
        trophyRepository = mockk()
        inspector = mockk()
        viewModel =
            CarnetBordViewModel(
                universRepository,
                playerRepository,
                trophyRepository,
                inspector,
            )
    }

    @Test
    fun `universPath returns the path from the inspector`() {
        val path = mockk<Univers2Path>()
        every { inspector.universPath } returns path

        assertSame(path, viewModel.universPath)
    }

    @Test
    fun `playerName returns the name from the player repository`() {
        every { playerRepository.name } returns "Alice"

        assertEquals("Alice", viewModel.playerName)
    }

    @Test
    fun `playerName returns null when no name is stored`() {
        every { playerRepository.name } returns null

        assertNull(viewModel.playerName)
    }

    @Test
    fun `storePage delegates to the player repository`() {
        val step = mockk<GameStep>()
        every { playerRepository.storePage(step) } just runs

        viewModel.storePage(step)

        verify(exactly = 1) { playerRepository.storePage(step) }
    }

    @Test
    fun `getUniversStatus returns the status from the univers repository`() {
        UniversStatus.entries.forEach { status ->
            every { universRepository.getUniversStatus(SharedPreferenceKey.UNIVERS_2_CANADA) } returns status

            assertEquals(status, viewModel.getUniversStatus(SharedPreferenceKey.UNIVERS_2_CANADA))
        }
    }

    @Test
    fun `isCheckShown returns true when the check was already shown`() {
        every { universRepository.isCheckShown("canada") } returns true

        assertTrue(viewModel.isCheckShown("canada"))
    }

    @Test
    fun `isCheckShown returns false when the check was not shown yet`() {
        every { universRepository.isCheckShown("canada") } returns false

        assertFalse(viewModel.isCheckShown("canada"))
    }

    @Test
    fun `markCheckShown delegates to the univers repository`() {
        every { universRepository.markCheckShown("canada") } just runs

        viewModel.markCheckShown("canada")

        verify(exactly = 1) { universRepository.markCheckShown("canada") }
    }

    @Test
    fun `getUniversPage returns the stored page`() {
        every { universRepository.getUniversPage(SharedPreferenceKey.UNIVERS_1) } returns "SOME_STEP"

        assertEquals("SOME_STEP", viewModel.getUniversPage(SharedPreferenceKey.UNIVERS_1))
    }

    @Test
    fun `getUniversPage returns null when nothing is stored`() {
        every { universRepository.getUniversPage(SharedPreferenceKey.UNIVERS_1) } returns null

        assertNull(viewModel.getUniversPage(SharedPreferenceKey.UNIVERS_1))
    }

    @Test
    fun `getUnivers2Page passes the inspector to the univers repository`() {
        every { universRepository.getUnivers2Page(inspector) } returns "FIRST_STEP"

        assertEquals("FIRST_STEP", viewModel.getUnivers2Page())
        verify(exactly = 1) { universRepository.getUnivers2Page(inspector) }
    }

    @Test
    fun `getUnivers2FinalStatus passes the inspector to the univers repository`() {
        every { universRepository.getUnivers2FinalStatus(inspector) } returns UniversStatus.GOING

        assertEquals(UniversStatus.GOING, viewModel.getUnivers2FinalStatus())
        verify(exactly = 1) { universRepository.getUnivers2FinalStatus(inspector) }
    }

    @Test
    fun `unlockedTrophiesCount counts only unlocked trophies`() {
        every { trophyRepository.getAllTrophies() } returns
            Trophies.entries.mapIndexed { index, trophy -> trophy to (index < 2) }

        assertEquals(minOf(2, Trophies.entries.size), viewModel.unlockedTrophiesCount)
    }

    @Test
    fun `unlockedTrophiesCount is zero when nothing is unlocked`() {
        every { trophyRepository.getAllTrophies() } returns Trophies.entries.map { it to false }

        assertEquals(0, viewModel.unlockedTrophiesCount)
    }

    @Test
    fun `unlockedTrophiesCount is zero when there is no trophy`() {
        every { trophyRepository.getAllTrophies() } returns emptyList()

        assertEquals(0, viewModel.unlockedTrophiesCount)
    }

    @Test
    fun `unlockedTrophiesCount equals the total when everything is unlocked`() {
        every { trophyRepository.getAllTrophies() } returns Trophies.entries.map { it to true }

        assertEquals(viewModel.totalTrophiesCount, viewModel.unlockedTrophiesCount)
    }

    @Test
    fun `totalTrophiesCount equals the number of trophies`() {
        assertEquals(Trophies.entries.size, viewModel.totalTrophiesCount)
    }

    @Test
    fun `isTrophyUnlocked returns true when the trophy is unlocked`() {
        val trophy = Trophies.entries.first()
        every { trophyRepository.isTrophyUnlocked(trophy) } returns true

        assertTrue(viewModel.isTrophyUnlocked(trophy))
    }

    @Test
    fun `isTrophyUnlocked returns false when the trophy is locked`() {
        val trophy = Trophies.entries.first()
        every { trophyRepository.isTrophyUnlocked(trophy) } returns false

        assertFalse(viewModel.isTrophyUnlocked(trophy))
    }

    @Test
    fun `saveTrophy delegates to the trophy repository`() {
        val trophy = Trophies.entries.first()
        every { trophyRepository.saveTrophy(trophy) } just runs

        viewModel.saveTrophy(trophy)

        verify(exactly = 1) { trophyRepository.saveTrophy(trophy) }
    }
}
