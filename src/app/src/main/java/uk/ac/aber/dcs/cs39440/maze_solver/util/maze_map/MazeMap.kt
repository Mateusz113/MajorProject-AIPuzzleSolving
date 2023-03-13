package uk.ac.aber.dcs.cs39440.maze_solver.util.maze_map

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs39440.maze_solver.util.node.Node
import java.io.FileNotFoundException
import java.io.InputStream

//Value used for Logging purposes
private const val LOG_TAG = "MAP"

/**
 * Functions that handles IO for the first maze load from the txt file
 * @param fileName Name of the file that contains the information about the first maze
 * @param mazeSize Size of the first maze
 * @param mazeMap Map of the maze that is updated when loading the file
 * @author Mateusz Chilicki (mac129) - 13th March 2023 - v1.0
 */
@Composable
fun GetMazeMap(
    fileName: String,
    mazeSize: Pair<Int, Int>,
    mazeMap: MutableMap<Pair<Int, Int>, Node>,
) {
    //Application context used to get the file
    val context = LocalContext.current

    //Used to hold the file information
    val file: InputStream

    //Variable stores the line from the file
    var nextLine: String

    //Coroutine scope used for multithreading
    val coroutineScope = rememberCoroutineScope()

    try {
        //Tries to read a file with given fileName from assets folder
        file = context.assets.open(fileName)

        Log.i(LOG_TAG, "Load successful")
    } catch (e: FileNotFoundException) {
        //File was not found so the exception is handled by returning from function
        Log.i(LOG_TAG, "Failed to load the file")
        return
    }

    //Used to hold the list of line Strings produced from txt file
    val fileTextLines: List<String> = file.bufferedReader().use {
        it.readText().lines()
    }
    //Log.i(LOG_TAG, fileTextLines.size.toString())

    //Loops over the items and adds all the maze nodes to the map
    Log.i("MAP", "Starting the loop")
    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.Default) {
            for (row in 0..mazeSize.second) {
                nextLine = fileTextLines[row]
                //Log.i(LOG_TAG, nextLine)
                for (column in 0..mazeSize.first) {
                    //Log.i(LOG_TAG, Pair(column, row).toString())

                    //Adds nodes to the map based on the characters in String
                    //'0' - wall representation
                    //'1' - corridor representation
                    if (nextLine[column] == '0') {
                        mazeMap[Pair(column, row)] = Node.Wall
                    } else {
                        mazeMap[Pair(column, row)] = Node.Corridor
                    }
                }
            }
        }
    }
}