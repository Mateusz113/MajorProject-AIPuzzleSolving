package uk.ac.aber.dcs.cs39440.maze_solver.ui.maze

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs39440.maze_solver.R
import uk.ac.aber.dcs.cs39440.maze_solver.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.typography
import uk.ac.aber.dcs.cs39440.maze_solver.util.algorithms.bfs
import uk.ac.aber.dcs.cs39440.maze_solver.util.maze_map.GetMazeMap
import uk.ac.aber.dcs.cs39440.maze_solver.util.node.Node
import uk.ac.aber.dcs.cs39440.maze_solver.util.settings.algorithmChosen
import uk.ac.aber.dcs.cs39440.maze_solver.util.settings.mazeSize

//Variables used to stop the constant file reading on navigation recompositions
var wasFileRead = false
var mazeMapRead: MutableMap<Pair<Int, Int>, Node> = mutableMapOf()

/**
 * Maze screen composable
 * @param navController Navigation controller used to switch between screens
 */
@Composable
fun MazeScreen(
    navController: NavController
) {
    //State of the maze map representation
    var mazeMap = remember { mutableStateMapOf<Pair<Int, Int>, Node>() }
    //State used to start the pathfinding
    var isPathfinding by remember { mutableStateOf(false) }
    //State used to start the reload of maze from the file
    var isLoading by remember { mutableStateOf(false) }
    //Coroutine scope for multithreading
    val coroutineScope = rememberCoroutineScope()

    //Checks if the file was read on this application launch and does so if not
    if (!wasFileRead) {
        GetMazeMap(
            fileName = "maze20x30.txt",
            mazeSize = mazeSize,
            mazeMap = mazeMap
        )
        wasFileRead = true
        mazeMapRead = mazeMap
    } else {
        //Reloads the maze from saved variable
        mazeMap = mazeMapRead as SnapshotStateMap<Pair<Int, Int>, Node>
    }

    //Launches the BFS algorithm when the state is changed
    if (isPathfinding) {
        LaunchedEffect(key1 = Unit) {
            coroutineScope.launch(Dispatchers.Default) {
                bfs(
                    mazeWidth = mazeSize.first,
                    mazeHeight = mazeSize.second,
                    mazeMap = mazeMap,
                    startX = 0,
                    startY = 1,
                    endX = mazeSize.first,
                    endY = mazeSize.second - 1,
                )
                isPathfinding = false
            }
        }
    }

    //Reloads the maze from file on state change
    if (isLoading) {
        GetMazeMap(
            fileName = "maze20x30.txt",
            mazeSize = mazeSize,
            mazeMap = mazeMap
        )
        isLoading = false
    }

    //Wrapper function for all the items on the page
    TopLevelScaffold(navController)
    { innerPadding ->
        ConstraintLayout(modifier = Modifier.padding(innerPadding)) {

            //Refs for constraint layout definitions
            val (settingsText, maze, startBtn, stopBtn) = createRefs()

            //Displays the current chosen algorithm as well as maze size
            AlgorithmSettingsText(
                mazeSize = mazeSize,
                algorithm = algorithmChosen,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(settingsText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            //Maze vertical grid representation
            RenderMaze(
                mazeMap = mazeMap,
                columnCount = GridCells.Fixed(mazeSize.first + 1),
                modifier = Modifier
                    .constrainAs(maze) {
                        top.linkTo(settingsText.top)
                    }
                    .padding(top = 50.dp)
                    .height(620.dp),
                userScrollEnabled = false
            )

            //Start button - starts pathfinding onClick
            Button(
                onClick = {
                    isPathfinding = true
                    Log.i("BFS", mazeMap.entries.toString())
                },
                modifier = Modifier
                    .constrainAs(startBtn) {
                        start.linkTo(parent.start)
                        top.linkTo(maze.bottom)
                    }
                    .padding(start = 50.dp, top = 20.dp)
                    .width(120.dp)
            ) {
                Text(text = stringResource(R.string.start))
            }

            //Stop button - reloads the maze onClick
            Button(
                onClick = {
                    isLoading = true
                },
                modifier = Modifier
                    .constrainAs(stopBtn) {
                        end.linkTo(parent.end)
                        top.linkTo(maze.bottom)
                    }
                    .padding(end = 50.dp, top = 20.dp)
                    .width(120.dp)
            ) {
                Text(text = stringResource(R.string.stop))
            }
        }
    }
}

@Composable
private fun RenderMaze(
    mazeMap: MutableMap<Pair<Int, Int>, Node>,
    columnCount: GridCells,
    modifier: Modifier,
    userScrollEnabled: Boolean
) {
    val itemModifierBlack = Modifier
        .border(50.dp, Color.Black)
        .height(20.dp)
        .wrapContentSize()
    val itemModifierWhite = Modifier
        .border(50.dp, Color.White)
        .height(20.dp)
        .wrapContentSize()
    val itemModifierRed = Modifier
        .border(50.dp, Color.Red)
        .height(20.dp)
        .wrapContentSize()
    val itemModifierBlue = Modifier
        .border(50.dp, Color.Blue)
        .height(20.dp)
        .wrapContentSize()

    LazyVerticalGrid(
        columns = columnCount,
        modifier = modifier,
        userScrollEnabled = userScrollEnabled
    ) {
        if (mazeMap.isNotEmpty()) {
            for (row in 0..mazeSize.second) {
                for (column in 0..mazeSize.first) {
                    item {
                        if (mazeMap[Pair(column, row)] == Node.Wall) {
                            Text(
                                text = "",
                                modifier = itemModifierBlack
                            )
                        } else if (mazeMap[Pair(column, row)] == Node.FinalPath) {
                            Text(
                                text = "",
                                modifier = itemModifierRed
                            )
                        } else if (mazeMap[Pair(column, row)] == Node.Considered) {
                            Text(
                                text = "",
                                modifier = itemModifierBlue
                            )
                        } else {
                            Text(
                                text = "",
                                modifier = itemModifierWhite
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun AlgorithmSettingsText(
    mazeSize: Pair<Int, Int>,
    algorithm: String,
    modifier: Modifier
) {
    Text(
        modifier = modifier.padding(top = 5.dp),
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = typography.titleLarge.fontSize),
        text = "Chosen algorithm: ${algorithm}, Maze size: ${mazeSize.first}x${mazeSize.second}",
    )
}


@Preview
@Composable
fun MazeScreenPreview() {
    Maze_solverTheme {
        val navController = rememberNavController()
        MazeScreen(navController)
    }
}