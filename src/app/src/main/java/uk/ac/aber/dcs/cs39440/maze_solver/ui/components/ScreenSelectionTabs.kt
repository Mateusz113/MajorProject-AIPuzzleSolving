package uk.ac.aber.dcs.cs39440.maze_solver.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs39440.maze_solver.R
import uk.ac.aber.dcs.cs39440.maze_solver.ui.navigation.Screen
import uk.ac.aber.dcs.cs39440.maze_solver.ui.navigation.tabsScreens
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme

@Composable
fun ScreenSelectionTabs(
    navController: NavController
) {
    // List of labels for tabs
    val labels = mapOf(
        Screen.Maze to R.string.maze,
        Screen.Settings to R.string.settings
    )
    //Holds the state of which tab is active
    var tabsState by remember { mutableStateOf(0) }

    //Getting the current destination
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    //Determining the active tab
    tabsScreens.forEach { screen ->
        if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
            tabsState = tabsScreens.indexOf(screen)
        }
    }

    //Creating the tabs based on tabsScreens list
    TabRow(
        selectedTabIndex = tabsState,
    ) {
        tabsScreens.forEach { screen ->
            val labelText = labels[screen]
            Tab(
                selected = tabsScreens.indexOf(screen) == tabsState,
                onClick = {
                    tabsState = tabsScreens.indexOf(screen)
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                text = {
                    if (labelText != null) {
                        Text(text = stringResource(id = labelText))
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun ScreenSelectionTabPreview() {
    Maze_solverTheme {
        val innerPadding = PaddingValues()
        val navController = rememberNavController()
        ScreenSelectionTabs(navController)
    }
}