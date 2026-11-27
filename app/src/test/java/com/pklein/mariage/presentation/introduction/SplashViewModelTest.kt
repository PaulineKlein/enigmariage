package com.pklein.mariage.presentation.introduction

import com.pklein.mariage.data.model.GameNames
import com.pklein.mariage.data.model.Trophies
import com.pklein.mariage.data.repository.PlayerRepository
import com.pklein.mariage.data.repository.RoomFirebaseRepository
import com.pklein.mariage.data.repository.TrophyRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SplashViewModelTest {
    private lateinit var playerRepository: PlayerRepository
    private lateinit var trophyRepository: TrophyRepository
    private lateinit var roomFirebaseRepository: RoomFirebaseRepository

    private val names = GameNames("Léa", "Tom", "Zorg")

    @Before
    fun setUp() {
        playerRepository = mockk()
        trophyRepository = mockk()
        roomFirebaseRepository = mockk()
        every { trophyRepository.getAllTrophies() } returns Trophies.entries.map { it to false }
        every { playerRepository.storeRoomCode(any()) } just runs
        every { playerRepository.storeGameNames(any()) } just runs
        every { playerRepository.resetStorage() } just runs
        every { playerRepository.leaveRoom() } just runs
    }

    private fun viewModel(
        page: String? = null,
        roomCode: String? = null,
    ): SplashViewModel {
        every { playerRepository.page } returns page
        every { playerRepository.roomCode } returns roomCode
        return SplashViewModel(playerRepository, trophyRepository, roomFirebaseRepository)
    }

    private fun SplashViewModel.state() = uiState.value

    @Test
    fun `state has no saved game without a page`() {
        val state = viewModel(page = null, roomCode = "ABC123").state()

        assertFalse(state.hasSavedGame)
        assertNull(state.roomCode)
    }

    @Test
    fun `state exposes the saved game and its room`() {
        val state = viewModel(page = "PAGE", roomCode = "ABC123").state()

        assertTrue(state.hasSavedGame)
        assertEquals("ABC123", state.roomCode)
    }

    @Test
    fun `a blank room code is a solo game`() {
        assertNull(viewModel(page = "PAGE", roomCode = "").state().roomCode)
    }

    @Test
    fun `state counts the unlocked trophies`() {
        every { trophyRepository.getAllTrophies() } returns Trophies.entries.mapIndexed { index, it -> it to (index < 2) }

        val progress = viewModel().state().trophyProgress

        assertEquals(minOf(2, Trophies.entries.size), progress.unlocked)
        assertEquals(Trophies.entries.size, progress.total)
    }

    @Test
    fun `new game without a saved game stores a solo room and launches the intro`() {
        val viewModel = viewModel()

        viewModel.onNewGame()

        verify { playerRepository.storeRoomCode("") }
        verify { playerRepository.storeGameNames(GameNames()) }
        assertTrue(viewModel.state().launchIntro)
        assertNull(viewModel.state().confirmation)
    }

    @Test
    fun `new game with a saved game asks before replacing it`() {
        val viewModel = viewModel(page = "PAGE")

        viewModel.onNewGame()

        assertEquals(SplashConfirmation.ReplaceGame("", GameNames()), viewModel.state().confirmation)
        assertFalse(viewModel.state().launchIntro)
        verify(exactly = 0) { playerRepository.resetStorage() }
    }

    @Test
    fun `confirming a replacement resets the storage then stores the new room`() {
        val viewModel = viewModel(page = "PAGE")
        viewModel.onNewGame()

        viewModel.confirm()

        verifyOrder {
            playerRepository.resetStorage()
            playerRepository.storeRoomCode("")
        }
        assertTrue(viewModel.state().launchIntro)
        assertNull(viewModel.state().confirmation)
    }

    @Test
    fun `dismissing a confirmation changes nothing`() {
        val viewModel = viewModel(page = "PAGE")
        viewModel.onNewGame()

        viewModel.dismiss()

        assertNull(viewModel.state().confirmation)
        assertFalse(viewModel.state().launchIntro)
        verify(exactly = 0) { playerRepository.resetStorage() }
        verify(exactly = 0) { playerRepository.storeRoomCode(any()) }
    }

    @Test
    fun `confirm without a pending confirmation does nothing`() {
        val viewModel = viewModel(page = "PAGE")

        viewModel.confirm()

        verify(exactly = 0) { playerRepository.resetStorage() }
        assertFalse(viewModel.state().launchIntro)
    }

    @Test
    fun `multiplayer without a saved game stores the room and launches the intro`() {
        val viewModel = viewModel()

        viewModel.onMultiplayer("ABC123", names, keepProgress = true)

        verify { playerRepository.storeRoomCode("ABC123") }
        verify { playerRepository.storeGameNames(names) }
        assertTrue(viewModel.state().launchIntro)
    }

    @Test
    fun `multiplayer with a solo game asks before bringing it into the room`() {
        val viewModel = viewModel(page = "PAGE")

        viewModel.onMultiplayer("ABC123", names, keepProgress = true)

        assertEquals(SplashConfirmation.JoinRoom("ABC123", names), viewModel.state().confirmation)
        verify(exactly = 0) { playerRepository.storeRoomCode(any()) }
    }

    @Test
    fun `confirming a join keeps the progress and only stores the room and names`() {
        val viewModel = viewModel(page = "PAGE")
        viewModel.onMultiplayer("ABC123", names, keepProgress = true)
        every { playerRepository.roomCode } returns "ABC123"

        viewModel.confirm()

        verify { playerRepository.storeRoomCode("ABC123") }
        verify { playerRepository.storeGameNames(names) }
        verify(exactly = 0) { playerRepository.resetStorage() }
        assertFalse(viewModel.state().launchIntro)
        assertEquals("ABC123", viewModel.state().roomCode)
        assertNull(viewModel.state().confirmation)
    }

    @Test
    fun `multiplayer from another room asks to switch`() {
        val viewModel = viewModel(page = "PAGE", roomCode = "OLD111")

        viewModel.onMultiplayer("NEW222", names, keepProgress = true)

        assertEquals(SplashConfirmation.SwitchRoom("OLD111", "NEW222", names), viewModel.state().confirmation)
    }

    @Test
    fun `confirming a switch stores the new room without a reset`() {
        val viewModel = viewModel(page = "PAGE", roomCode = "OLD111")
        viewModel.onMultiplayer("NEW222", names, keepProgress = true)

        viewModel.confirm()

        verify { playerRepository.storeRoomCode("NEW222") }
        verify(exactly = 0) { playerRepository.resetStorage() }
    }

    @Test
    fun `multiplayer for the current room needs no confirmation`() {
        val viewModel = viewModel(page = "PAGE", roomCode = "ABC123")

        viewModel.onMultiplayer("ABC123", names, keepProgress = true)

        assertNull(viewModel.state().confirmation)
        verify { playerRepository.storeRoomCode("ABC123") }
        verify(exactly = 0) { playerRepository.resetStorage() }
        assertFalse(viewModel.state().launchIntro)
    }

    @Test
    fun `multiplayer as a new game asks before replacing the saved game`() {
        val viewModel = viewModel(page = "PAGE", roomCode = "OLD111")

        viewModel.onMultiplayer("NEW222", names, keepProgress = false)

        assertEquals(SplashConfirmation.ReplaceGame("NEW222", names), viewModel.state().confirmation)
    }

    @Test
    fun `leaving a room asks first`() {
        val viewModel = viewModel(page = "PAGE", roomCode = "ABC123")

        viewModel.onLeaveRoom()

        assertEquals(SplashConfirmation.LeaveRoom("ABC123"), viewModel.state().confirmation)
        verify(exactly = 0) { playerRepository.leaveRoom() }
    }

    @Test
    fun `leaving without a room does nothing`() {
        val viewModel = viewModel(page = "PAGE")

        viewModel.onLeaveRoom()

        assertNull(viewModel.state().confirmation)
    }

    @Test
    fun `confirming a leave goes back to solo and keeps the progress`() {
        val viewModel = viewModel(page = "PAGE", roomCode = "ABC123")
        viewModel.onLeaveRoom()
        every { playerRepository.roomCode } returns null

        viewModel.confirm()

        verify { playerRepository.leaveRoom() }
        verify(exactly = 0) { playerRepository.resetStorage() }
        assertTrue(viewModel.state().hasSavedGame)
        assertNull(viewModel.state().roomCode)
        assertNull(viewModel.state().confirmation)
    }

    @Test
    fun `refresh keeps a pending confirmation`() {
        val viewModel = viewModel(page = "PAGE")
        viewModel.onNewGame()

        viewModel.refresh()

        assertEquals(SplashConfirmation.ReplaceGame("", GameNames()), viewModel.state().confirmation)
    }

    @Test
    fun `onIntroLaunched clears the launch flag`() {
        val viewModel = viewModel()
        viewModel.onNewGame()

        viewModel.onIntroLaunched()

        assertFalse(viewModel.state().launchIntro)
    }
}
