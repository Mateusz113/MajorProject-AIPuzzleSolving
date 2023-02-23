package uk.ac.aber.dcs.cs39440.maze_solver.ui.maze

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs39440.maze_solver.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme

@Composable
fun MazeScreen(
    navController: NavController
) {
    TopLevelScaffold(navController = navController)
}

@Preview
@Composable
fun MazeScreenPreview() {
    Maze_solverTheme {
        val navController = rememberNavController()
        MazeScreen(navController)
    }
}