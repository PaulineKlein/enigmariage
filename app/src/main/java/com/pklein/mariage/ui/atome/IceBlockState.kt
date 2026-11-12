package com.pklein.mariage.ui.atome

import android.os.SystemClock
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

internal const val HITS_TO_SHATTER = 10
private const val IDLE_BEFORE_HEAL_MS = 900L
private const val HEAL_STEP_MS = 500L

@Stable
class IceBlockState(
    initialHits: Int = 0,
    initialShattered: Boolean = false,
) {
    var hits by mutableIntStateOf(initialHits)
        private set
    var isShattered by mutableStateOf(initialShattered)
        private set

    private var lastTapAt = SystemClock.elapsedRealtime()

    fun tap() {
        if (isShattered) return
        lastTapAt = SystemClock.elapsedRealtime()
        hits++
        if (hits >= HITS_TO_SHATTER) isShattered = true
    }

    // The cracks close up one by one when the player stops tapping, so the taps must be quick.
    suspend fun healWhenIdle() {
        while (true) {
            delay(HEAL_STEP_MS)
            if (!isShattered && hits > 0 && SystemClock.elapsedRealtime() - lastTapAt >= IDLE_BEFORE_HEAL_MS) {
                hits--
            }
        }
    }

    companion object {
        val Saver =
            listSaver<IceBlockState, Int>(
                save = { listOf(it.hits, if (it.isShattered) 1 else 0) },
                restore = { IceBlockState(it[0], it[1] == 1) },
            )
    }
}
