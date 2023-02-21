package uk.ac.aber.dcs.cs39440.maze_solver.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.aber.dcs.cs39440.maze_solver.R
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = R.string.application_name)) }
    )
}

@Preview
@Composable
fun TopAppBarPreview() {
    Maze_solverTheme () {
        TopAppBar()
    }
}