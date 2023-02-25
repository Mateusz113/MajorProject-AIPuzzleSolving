package uk.ac.aber.dcs.cs39440.maze_solver.ui.maze

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs39440.maze_solver.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.typography

@Composable
fun MazeScreen(
    navController: NavController
) {
    TopLevelScaffold(navController)
    { innerPadding ->
        Text(
            text = "Maze page",
            modifier = Modifier.padding(innerPadding),
            style = TextStyle(fontSize = typography.headlineLarge.fontSize)
        )
    }
}

@Preview
@Composable
fun MazeScreenPreview() {
    Maze_solverTheme {
        val navController = rememberNavController()
        MazeScreen(navController)
    }
}