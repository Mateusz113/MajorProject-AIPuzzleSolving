package uk.ac.aber.dcs.cs39440.maze_solver.ui.maze

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.aber.dcs.cs39440.maze_solver.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme

@Composable
fun MazeScreen(
){
    TopLevelScaffold()
}

@Preview
@Composable
fun MazeScreenPreview(){
    Maze_solverTheme {
        MazeScreen()
    }
}