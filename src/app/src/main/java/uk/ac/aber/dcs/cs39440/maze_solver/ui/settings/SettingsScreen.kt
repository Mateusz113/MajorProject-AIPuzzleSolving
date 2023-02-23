package uk.ac.aber.dcs.cs39440.maze_solver.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs39440.maze_solver.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme

@Composable
fun SettingsScreen(navController: NavController) {
    TopLevelScaffold(navController)
}

@Preview
@Composable
fun SettingsScreenPreview() {
    Maze_solverTheme {
        val navController = rememberNavController()
        SettingsScreen(navController)
    }
}