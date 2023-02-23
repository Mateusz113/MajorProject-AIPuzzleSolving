package uk.ac.aber.dcs.cs39440.maze_solver.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint
fun TopLevelScaffold(
    navController: NavController,
    pageContent:
    @Composable (innerPadding: PaddingValues) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar()
        }
    ){ innerPadding ->
        ScreenSelectionTabs(innerPadding, navController)
    }
}

@Composable
@Preview
fun TopLevelScaffoldPreview() {
    Maze_solverTheme {
        val navController = rememberNavController()
        TopLevelScaffold(navController)
    }
}