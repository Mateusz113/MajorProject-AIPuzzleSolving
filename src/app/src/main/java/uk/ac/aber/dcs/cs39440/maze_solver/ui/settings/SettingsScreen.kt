package uk.ac.aber.dcs.cs39440.maze_solver.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.aber.dcs.cs39440.maze_solver.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs39440.maze_solver.ui.navigation.Screen
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme

@Composable
fun SettingsScreen() {
    TopLevelScaffold()
}

@Preview
@Composable
fun SettingsScreenPreview() {
    Maze_solverTheme {
        SettingsScreen()
    }
}