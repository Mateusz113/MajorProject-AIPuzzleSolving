package uk.ac.aber.dcs.cs39440.maze_solver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs39440.maze_solver.ui.maze.MazeScreen
import uk.ac.aber.dcs.cs39440.maze_solver.ui.navigation.Screen
import uk.ac.aber.dcs.cs39440.maze_solver.ui.settings.SettingsScreen
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Maze_solverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph()
                }
            }
        }
    }
}

@Composable
private fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Maze.route
    ) {
        //Route to maze screen
        composable(Screen.Maze.route) {
            MazeScreen(navController)
        }
        //Route to settings screen
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
    }
}