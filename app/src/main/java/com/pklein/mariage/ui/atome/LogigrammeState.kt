package com.pklein.mariage.ui.atome

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

enum class LogigrammeCellState {
    EMPTY,
    TRUE,
    FALSE,
    ;

    fun next() =
        when (this) {
            EMPTY -> TRUE
            TRUE -> FALSE
            FALSE -> EMPTY
        }
}

enum class LogigrammeCategory { COUP, VITESSE, ZONE }

class LogigrammeWord(
    val id: Int,
    @StringRes val label: Int,
    val category: LogigrammeCategory,
)

@Stable
class LogigrammeState(
    private val solution: Set<Set<Int>>,
    initialCells: List<LogigrammeCellState> = List(CELL_COUNT) { LogigrammeCellState.EMPTY },
    initialSlots: List<Int?> = List(SLOT_COUNT) { null },
) {
    val cells = mutableStateListOf<LogigrammeCellState>().apply { addAll(initialCells) }

    // Word id placed in each slot, row by row; null when the slot is empty.
    val slots = mutableStateListOf<Int?>().apply { addAll(initialSlots) }

    var draggedWord by mutableStateOf<Int?>(null)
        private set
    var dragPointer by mutableStateOf(Offset.Zero)
        private set
    var dragGrab = Offset.Zero
        private set
    var dragSize = IntSize.Zero
        private set

    val isSolved: Boolean
        get() =
            slots.none { it == null } &&
                slots
                    .chunked(LINE_SIZE)
                    .map { line -> line.filterNotNull().toSet() }
                    .toSet() == solution

    fun toggleCell(
        row: Int,
        column: Int,
    ) {
        val index = row * GRID_SIZE + column
        cells[index] = cells[index].next()
    }

    fun startDrag(
        word: Int,
        pointer: Offset,
        grab: Offset,
        size: IntSize,
    ) {
        dragGrab = grab
        dragSize = size
        dragPointer = pointer
        draggedWord = word
    }

    fun dragBy(delta: Offset) {
        dragPointer += delta
    }

    fun cancelDrag() {
        draggedWord = null
    }

    // targetSlot null means the word was released outside any slot and goes back to the pool.
    fun drop(targetSlot: Int?) {
        val word = draggedWord ?: return
        val origin = slots.indexOf(word)
        if (targetSlot == null) {
            if (origin >= 0) slots[origin] = null
        } else if (targetSlot != origin) {
            val occupant = slots[targetSlot]
            slots[targetSlot] = word
            if (origin >= 0) slots[origin] = occupant
        }
        draggedWord = null
    }

    fun removeFromSlot(slot: Int) {
        slots[slot] = null
    }

    fun reset() {
        draggedWord = null
        cells.indices.forEach { cells[it] = LogigrammeCellState.EMPTY }
        slots.indices.forEach { slots[it] = null }
    }

    companion object {
        const val GRID_SIZE = 6
        const val LINE_SIZE = 3
        const val LINE_COUNT = 3
        const val CELL_COUNT = GRID_SIZE * GRID_SIZE
        const val SLOT_COUNT = LINE_SIZE * LINE_COUNT

        fun saver(solution: Set<Set<Int>>) =
            listSaver<LogigrammeState, Int>(
                save = { state -> state.cells.map { it.ordinal } + state.slots.map { it ?: -1 } },
                restore = { saved ->
                    LogigrammeState(
                        solution = solution,
                        initialCells =
                            saved
                                .take(CELL_COUNT)
                                .map { LogigrammeCellState.entries[it] },
                        initialSlots = saved.drop(CELL_COUNT).map { if (it < 0) null else it },
                    )
                },
            )
    }
}
