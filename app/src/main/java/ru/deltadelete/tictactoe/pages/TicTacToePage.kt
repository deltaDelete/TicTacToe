package ru.deltadelete.tictactoe.pages

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.deltadelete.tictactoe.R

val CELL_SIZE = 100.dp
val CELL_SPACING = 10.dp

@Preview(device = Devices.PIXEL_4)
@Composable
fun TicTacToePage() {
    val firstToGo = remember { mutableStateOf(CellState.Empty) }
    val openDialog = remember { mutableStateOf(true) }
    val alertWin = remember { mutableStateOf(false) }
    val winText = remember { mutableStateOf("") }
    val states = remember {
        mutableStateListOf(*field)
    }
    val context = LocalContext.current

    if (openDialog.value) {
        FirstToGo({ openDialog.value = false }, firstToGo)
    }

    if (alertWin.value) {
        AlertDialog(
            { alertWin.value = false; openDialog.value = true; states.fill(CellState.Empty) },
            {},
            title = { Text(winText.value) })
    }

    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(CELL_SPACING, Alignment.CenterVertically),
        horizontalArrangement = Arrangement.spacedBy(CELL_SPACING, Alignment.CenterHorizontally),
        modifier = Modifier.fillMaxHeight(),
        columns = GridCells.Fixed(3),
        userScrollEnabled = false,
        contentPadding = PaddingValues(32.dp)
    ) {
        itemsIndexed(states) { index, it ->
            Button(
                onClick = {
                    states[index] = firstToGo.value
                    checkWin(alertWin, winText, states, context)
                    firstToGo.value = if (firstToGo.value == CellState.X) CellState.O else CellState.X
                },
                modifier = Modifier.width(CELL_SIZE).height(CELL_SIZE)
            ) {
                it.ToIcon()
            }
        }
    }
}

@Composable
fun FirstToGo(onDismissRequest: () -> Unit, state: MutableState<CellState>) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = { state.value = CellState.X; onDismissRequest() },
            ) {
                Text(text = stringResource(R.string.X), fontSize = 18.sp)
            }
        },
        dismissButton = {
            Button(
                onClick = { state.value = CellState.O; onDismissRequest() },
            ) {
                Text(text = stringResource(R.string.O), fontSize = 18.sp)
            }
        },
        title = {
            Text(
                text = stringResource(R.string.first_to_go),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
            )
        },
    )
}

enum class CellState {
    Empty,
    X,
    O;

    @Composable
    fun ToIcon() {
        return if (this == O) Icon(
            painter = painterResource(id = R.drawable.outline_circle_24),
            contentDescription = null
        )
        else if (this == X) Icon(imageVector = Icons.Filled.Close, contentDescription = null)
        else Icon(painter = painterResource(id = R.drawable.empty), contentDescription = null)
    }
}

val field = arrayOf(
    CellState.Empty, CellState.Empty, CellState.Empty,
    CellState.Empty, CellState.Empty, CellState.Empty,
    CellState.Empty, CellState.Empty, CellState.Empty
)

val winCombinations = arrayOf(
    intArrayOf(0, 1, 2),
    intArrayOf(3, 4, 5),
    intArrayOf(6, 7, 8),
    intArrayOf(0, 4, 8),
    intArrayOf(2, 4, 6),
    intArrayOf(0, 3, 6),
    intArrayOf(1, 4, 7),
    intArrayOf(2, 5, 8),
)

fun checkWin(
    alertWin: MutableState<Boolean>,
    winText: MutableState<String>,
    states: MutableList<CellState>,
    context: Context
) {
    winCombinations.forEach { it ->
        var isXWon = 0
        var isOWon = 0
        it.forEach {
            if (states[it] == CellState.X) {
                isXWon += 1
            } else if (states[it] == CellState.O) {
                isOWon += 1
            }
        }
        if (isXWon == 3) {
            alertWin.value = true
            winText.value = context.resources.getString(R.string.x_won)
        } else if (isOWon == 3) {
            alertWin.value = true
            winText.value = context.resources.getString(R.string.o_won)
        }
    }
    if (!alertWin.value && states.all { it != CellState.Empty }) {
        alertWin.value = true
        winText.value = context.resources.getString(R.string.no_winners)
    }
}
