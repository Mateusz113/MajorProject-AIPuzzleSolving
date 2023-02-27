package uk.ac.aber.dcs.cs39440.maze_solver.ui.maze

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs39440.maze_solver.R
import uk.ac.aber.dcs.cs39440.maze_solver.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme
import java.lang.invoke.ConstantCallSite
import kotlin.random.Random


@Composable
fun MazeScreen(
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    var array: Array<Int> = generateNumberList(20, 30)

//    LaunchedEffect(key1 = Unit) {
//        coroutineScope.launch {
//            array = generateNumberList(20, 30)
//        }
//    }
    val itemModifierBlack = Modifier
        .border(50.dp, Color.Black)
        .wrapContentSize()
    val itemModifierWhite = Modifier
        .border(50.dp, Color.White)
        .wrapContentSize()
    TopLevelScaffold(navController)
    { innerPadding ->
        ConstraintLayout(modifier = Modifier.padding(innerPadding)) {
            val (maze, startBtn, stopBtn) = createRefs()

            //Maze vertical grid representation
            LazyVerticalGrid(
                columns = GridCells.Fixed(20),
                modifier = Modifier
                    .constrainAs(maze) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                userScrollEnabled = false
            ) {
                array.forEach { number ->
                    item {
                        if (number == 0) {
                            Text(
                                text = number.toString(),
                                modifier = itemModifierBlack
                            )
                        } else {
                            Text(
                                text = number.toString(),
                                modifier = itemModifierWhite
                            )
                        }
                    }
                }
            }

            //Start button
            Button(
                onClick = { array = generateNumberList(20, 30) },
                modifier = Modifier
                    .constrainAs(startBtn) {
                        start.linkTo(parent.start)
                        top.linkTo(maze.bottom)
                    }
                    .padding(start = 50.dp, top = 30.dp)
                    .width(120.dp)
            ) {
                Text(text = stringResource(R.string.start))
            }

            //Stop button
            Button(
                onClick = {
                    coroutineScope.launch {
                        array = generateNumberList(20, 30)
                    }
                },
                modifier = Modifier
                    .constrainAs(stopBtn) {
                        end.linkTo(parent.end)
                        top.linkTo(maze.bottom)
                    }
                    .padding(end = 50.dp, top = 30.dp)
                    .width(120.dp)
            ) {
                Text(text = stringResource(R.string.stop))
            }
        }
    }
}

private fun generateNumberList(x: Int, y: Int): Array<Int> {
    var gridArray = arrayOf<Int>()
    for (i in (1..x * y)) {
        val nextInt = Random.nextInt(0, 2)
        gridArray += nextInt
    }
    return gridArray
}

@Preview
@Composable
fun MazeScreenPreview() {
    Maze_solverTheme {
        val navController = rememberNavController()
        MazeScreen(navController)
    }
}