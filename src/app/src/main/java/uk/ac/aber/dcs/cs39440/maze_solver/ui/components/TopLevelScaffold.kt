package uk.ac.aber.dcs.cs39440.maze_solver.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint
fun TopLevelScaffold(
    pageContent:
    @Composable (innerPadding: PaddingValues) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar()
        },
        content = { innerPadding ->
            pageContent(innerPadding)
        }
    )
}

@Composable
@Preview
fun TopLevelScaffoldPreview() {
    Maze_solverTheme {
        TopLevelScaffold()
    }
}