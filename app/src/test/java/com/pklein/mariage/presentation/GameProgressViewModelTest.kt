package com.pklein.mariage.presentation

import com.pklein.mariage.data.model.SharedPreferenceKey
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.data.repository.PlayerRepository
import com.pklein.mariage.data.repository.ScoreRepository
import com.pklein.mariage.data.repository.TrophyRepository
import com.pklein.mariage.data.repository.UniversRepository
import com.pklein.mariage.domain.IInspector
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GameProgressViewModelTest {
    private lateinit var playerRepository: PlayerRepository
    private lateinit var universRepository: UniversRepository
    private lateinit var scoreRepository: ScoreRepository
    private lateinit var trophyRepository: TrophyRepository
    private lateinit var inspector: IInspector
    private lateinit var viewModel: GameProgressViewModel

    @Before
    fun setUp() {
        playerRepository = mockk()
        universRepository = mockk()
        scoreRepository = mockk()
        trophyRepository = mockk()
        inspector = mockk()
        viewModel =
            GameProgressViewModel(
                playerRepository,
                universRepository,
                scoreRepository,
                trophyRepository,
                inspector,
            )
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
    fun `gender returns the gender from the inspector`() {
        every { inspector.gender } returns PlayerGender.RABBIT

        assertEquals(PlayerGender.RABBIT, viewModel.gender)
    }

    @Test
    fun `page returns the page from the player repository`() {
        every { playerRepository.page } returns "CARNET_BORD2"

        assertEquals("CARNET_BORD2", viewModel.page)
    }

    @Test
    fun `page returns null when no page is stored`() {
        every { playerRepository.page } returns null

        assertNull(viewModel.page)
    }

    @Test
    fun `victoryTrophyAwardedThisRun is false by default and can be updated`() {
        assertFalse(viewModel.victoryTrophyAwardedThisRun)

        viewModel.victoryTrophyAwardedThisRun = true

        assertTrue(viewModel.victoryTrophyAwardedThisRun)
    }

    @Test
    fun `unlockedTrophiesCount counts only unlocked trophies`() {
        val all = Trophies.entries
        every { trophyRepository.getAllTrophies() } returns
            all.mapIndexed { index, trophy -> trophy to (index < 2) }

        assertEquals(minOf(2, all.size), viewModel.unlockedTrophiesCount)
    }

    @Test
    fun `unlockedTrophiesCount is zero when nothing is unlocked`() {
        every { trophyRepository.getAllTrophies() } returns Trophies.entries.map { it to false }

        assertEquals(0, viewModel.unlockedTrophiesCount)
    }

    @Test
    fun `unlockedTrophiesCount equals the total when everything is unlocked`() {
        every { trophyRepository.getAllTrophies() } returns Trophies.entries.map { it to true }

        assertEquals(Trophies.entries.size, viewModel.unlockedTrophiesCount)
    }

    @Test
    fun `storePage delegates to the player repository`() {
        val step = mockk<GameStep>()
        every { playerRepository.storePage(step) } just runs

        viewModel.storePage(step)

        verify(exactly = 1) { playerRepository.storePage(step) }
    }

    @Test
    fun `storeUniversPage delegates to the univers repository`() {
        val step = mockk<GameStep>()
        every { universRepository.storeUniversPage(SharedPreferenceKey.UNIVERS_1, step) } just runs

        viewModel.storeUniversPage(SharedPreferenceKey.UNIVERS_1, step)

        verify(exactly = 1) {
            universRepository.storeUniversPage(
                SharedPreferenceKey.UNIVERS_1,
                step,
            )
        }
    }

    @Test
    fun `finishUnivers delegates to the univers repository`() {
        every { universRepository.finishUnivers(SharedPreferenceKey.UNIVERS_2_CANADA) } just runs

        viewModel.finishUnivers(SharedPreferenceKey.UNIVERS_2_CANADA)

        verify(exactly = 1) { universRepository.finishUnivers(SharedPreferenceKey.UNIVERS_2_CANADA) }
    }

    @Test
    fun `storePlayerName delegates to the player repository`() {
        every { playerRepository.storeName("Bob") } just runs

        viewModel.storePlayerName("Bob")

        verify(exactly = 1) { playerRepository.storeName("Bob") }
    }

    @Test
    fun `storePlayerGender delegates to the player repository`() {
        every { playerRepository.storeGender(PlayerGender.DOG) } just runs

        viewModel.storePlayerGender(PlayerGender.DOG)

        verify(exactly = 1) { playerRepository.storeGender(PlayerGender.DOG) }
    }

    @Test
    fun `storeStartTime delegates to the score repository`() {
        every { scoreRepository.storeStartTime() } just runs

        viewModel.storeStartTime()

        verify(exactly = 1) { scoreRepository.storeStartTime() }
    }

    @Test
    fun `storeEndTime delegates to the score repository`() {
        every { scoreRepository.storeEndTime() } just runs

        viewModel.storeEndTime()

        verify(exactly = 1) { scoreRepository.storeEndTime() }
    }

    @Test
    fun `resetStorage delegates to the player repository`() {
        every { playerRepository.resetStorage() } just runs

        viewModel.resetStorage()

        verify(exactly = 1) { playerRepository.resetStorage() }
    }

    @Test
    fun `updateError delegates to the score repository`() {
        every { scoreRepository.updateError() } just runs

        viewModel.updateError()

        verify(exactly = 1) { scoreRepository.updateError() }
    }

    @Test
    fun `updateClue delegates to the score repository`() {
        every { scoreRepository.updateClue() } just runs

        viewModel.updateClue()

        verify(exactly = 1) { scoreRepository.updateClue() }
    }

    @Test
    fun `saveTrophy delegates to the trophy repository`() {
        val trophy = Trophies.entries.first()
        every { trophyRepository.saveTrophy(trophy) } just runs

        viewModel.saveTrophy(trophy)

        verify(exactly = 1) { trophyRepository.saveTrophy(trophy) }
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
    fun `getAllTrophies returns the list from the trophy repository`() {
        val expected = Trophies.entries.map { it to false }
        every { trophyRepository.getAllTrophies() } returns expected

        assertEquals(expected, viewModel.getAllTrophies())
    }

    @Test
    fun `getDescription returns the inspector value`() {
        val step = mockk<GameStep>()
        every { inspector.getDescription(step) } returns 42

        assertEquals(42, viewModel.getDescription(step))
    }

    @Test
    fun `getDescription returns null when the step is null`() {
        every { inspector.getDescription(null) } returns null

        assertNull(viewModel.getDescription(null))
    }

    @Test
    fun `getEnigme returns the inspector value`() {
        val step = mockk<GameStep>()
        every { inspector.getEnigme(step) } returns 1

        assertEquals(1, viewModel.getEnigme(step))
    }

    @Test
    fun `getEnigmeAccessibility returns the inspector value`() {
        val step = mockk<GameStep>()
        every { inspector.getEnigmeAccessibility(step) } returns 2

        assertEquals(2, viewModel.getEnigmeAccessibility(step))
    }

    @Test
    fun `getClue returns the inspector value`() {
        val step = mockk<GameStep>()
        every { inspector.getClue(step) } returns 3

        assertEquals(3, viewModel.getClue(step))
    }

    @Test
    fun `getReponse returns the inspector value`() {
        val step = mockk<GameStep>()
        every { inspector.getReponse(step) } returns 4

        assertEquals(4, viewModel.getReponse(step))
    }

    @Test
    fun `inspector getters return null when the inspector has no resource`() {
        val step = mockk<GameStep>()
        every { inspector.getEnigme(step) } returns null
        every { inspector.getEnigmeAccessibility(step) } returns null
        every { inspector.getClue(step) } returns null
        every { inspector.getReponse(step) } returns null

        assertNull(viewModel.getEnigme(step))
        assertNull(viewModel.getEnigmeAccessibility(step))
        assertNull(viewModel.getClue(step))
        assertNull(viewModel.getReponse(step))
    }
}
