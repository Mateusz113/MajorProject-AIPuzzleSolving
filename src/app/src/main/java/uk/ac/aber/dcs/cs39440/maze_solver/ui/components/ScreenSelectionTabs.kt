package uk.ac.aber.dcs.cs39440.maze_solver.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs39440.maze_solver.R
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme

@Composable
fun ScreenSelectionTabs(
    innerPadding: PaddingValues,
    navController: NavController
) {
    TabRow(
        selectedTabIndex = 0,
        modifier = Modifier
            .padding(innerPadding)
    ) {
        Tab(
            selected = false,
            onClick = { /*TODO*/ },
            text = { Text(text = stringResource(id = R.string.maze)) }
        )

        Tab(
            selected = false,
            onClick = { /*TODO*/ },
            text = { Text(text = stringResource(id = R.string.settings)) }
        )
    }
}

@Preview
@Composable
fun ScreenSelectionTabPreview() {
    Maze_solverTheme {
        val innerPadding = PaddingValues()
        val navController = rememberNavController()
        ScreenSelectionTabs(innerPadding, navController)
    }
}