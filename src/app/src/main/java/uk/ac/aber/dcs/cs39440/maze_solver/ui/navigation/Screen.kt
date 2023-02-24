package uk.ac.aber.dcs.cs39440.maze_solver.ui.navigation

sealed class Screen(
    val route: String
) {
    object Maze : Screen("maze")
    object Settings : Screen("settings")
}

//List of screens used by tabs
val tabsScreens = listOf(
    Screen.Maze,
    Screen.Settings
)