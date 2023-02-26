package uk.ac.aber.dcs.cs39440.maze_solver.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs39440.maze_solver.R
import uk.ac.aber.dcs.cs39440.maze_solver.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.Maze_solverTheme
import uk.ac.aber.dcs.cs39440.maze_solver.ui.theme.typography

@Composable
fun SettingsScreen(navController: NavController) {
    var algorithmType by remember { mutableStateOf("Breadth-first search") }
    var mazeSize by remember { mutableStateOf("20 x 30") }

    TopLevelScaffold(navController) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier.padding(innerPadding)
        ) {
            //Creating refs for items
            val (algorithm, maze) = createRefs()

            AlgorithmChoosing(
                modifier = Modifier
                    .constrainAs(algorithm) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
                radioButtonsState = {
                    algorithmType = it
                }
            )

            MazeSizeChoosing(
                modifier = Modifier
                    .constrainAs(maze) {
                        start.linkTo(parent.start)
                        top.linkTo(algorithm.bottom)
                    },
                radioButtonsState = {
                    mazeSize = it
                }
            )
        }
    }
}

@Composable
private fun AlgorithmChoosing(
    modifier: Modifier,
    radioButtonsState: (String) -> Unit
) {
    //State of radio buttons
    var buttonsState by remember { mutableStateOf("Breadth-first search") }

    //All the radio button options
    val radioButtonsOptions = listOf(stringResource(R.string.bfs), stringResource(R.string.dfs))

    ConstraintLayout(modifier = modifier) {
        //Refs for all the elements
        val (description, algorithmButtons) = createRefs()

        // Algorithm text
        Text(
            text = stringResource(R.string.algorithm_choosing_desc),
            textAlign = TextAlign.Start,
            style = TextStyle(fontSize = typography.headlineMedium.fontSize),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(description) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .padding(start = 35.dp, top = 100.dp)
        )

        //Radio buttons
        RadioButtonsGenerator(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(algorithmButtons) {
                    start.linkTo(parent.start)
                    top.linkTo(description.bottom)
                }
                .padding(start = 20.dp),
            listOfItems = radioButtonsOptions,
            radioButtonsState = { state ->
                buttonsState = state
            }
        )
    }
    //Hoisting the state
    radioButtonsState(buttonsState)
}


@Composable
fun MazeSizeChoosing(
    modifier: Modifier,
    radioButtonsState: (String) -> Unit
) {
    //All the radio button options
    val radioButtonsOptions = listOf(
        stringResource(R.string.smallMazeSize),
        stringResource(R.string.mediumMazeSize),
        stringResource(R.string.largeMazeSize)
    )

    //State of radio buttons
    var buttonsState by remember { mutableStateOf("Breadth-first search") }

    ConstraintLayout(modifier = modifier) {
        //Refs for all the elements
        val (description, algorithmButtons) = createRefs()

        // Algorithm text
        Text(
            text = stringResource(R.string.maze_size_desc),
            textAlign = TextAlign.Start,
            style = TextStyle(fontSize = typography.headlineMedium.fontSize),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(description) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .padding(start = 35.dp, top = 100.dp)
        )

        //Radio buttons
        RadioButtonsGenerator(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(algorithmButtons) {
                    start.linkTo(parent.start)
                    top.linkTo(description.bottom)
                }
                .padding(start = 20.dp),
            listOfItems = radioButtonsOptions,
            radioButtonsState = { state ->
                buttonsState = state
            }
        )
    }
    //Hoisting the state
    radioButtonsState(buttonsState)
}

@Composable
private fun RadioButtonsGenerator(
    modifier: Modifier,
    radioButtonsState: (String) -> Unit,
    listOfItems: List<String>
) {
    //Variables to hold the buttons state
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(listOfItems[0]) }
    Column(
        modifier = modifier
            .selectableGroup()
    ) {
        listOfItems.forEach { text ->
            //Each radio button is packed into the row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                //Button
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {}
                )
                //Button text
                Text(
                    text = text,
                    style = typography.labelLarge,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
    //Hoist the state to the function higher in hierarchy
    radioButtonsState(selectedOption)
}

@Preview
@Composable
fun SettingsScreenPreview() {
    Maze_solverTheme {
        val navController = rememberNavController()
        SettingsScreen(navController)
    }
}