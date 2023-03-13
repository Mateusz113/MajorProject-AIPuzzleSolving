package uk.ac.aber.dcs.cs39440.maze_solver.util.algorithms

import android.util.Log
import kotlinx.coroutines.delay
import uk.ac.aber.dcs.cs39440.maze_solver.util.node.Node
import java.util.LinkedList
import java.util.Queue

//Value used for Log purposes
private const val LOG_TAG = "BFS"

/**
 * Breadth-first search algorithm used for pathfinding within the maze
 * @param mazeWidth Width of the maze that will be searched in
 * @param mazeHeight Height of the maze that will be searched in
 * @param mazeMap Map of the maze to search
 * @param startX Horizontal coordinate of the start point in maze
 * @param startY Vertical coordinate of the start point in maze
 * @param endX Horizontal coordinate of the end point in maze
 * @param endY Vertical coordinate of the end point in maze
 * @author Mateusz Chilicki (mac129) - 13th March 2023 - v1.0
 */
suspend fun bfs(
    mazeWidth: Int,
    mazeHeight: Int,
    mazeMap: MutableMap<Pair<Int, Int>, Node>,
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int
) {
    Log.i(LOG_TAG, "Starting the pathfinding")

    /**
     * Init of the parameters used
     */
    //Hold the coordinates for the current node
    var currentX = startX
    var currentY = startY

    //Queue of the nodes to check if they are solution
    val queueToCheck: Queue<Pair<Int, Int>> = LinkedList()

    //Current node
    var currentNode: Pair<Int, Int>

    //Stores the map of parents coordinates of nodes
    val parentMap = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

    while (true) {
        //Check if the current node is the end
        if (currentX == endX && currentY == endY) {
            //Goes through the parent list to find the final path through the maze
            getFinalPath(
                mazeMap = mazeMap,
                parentMap = parentMap,
                startX = startX,
                startY = startY,
                endX = endX,
                endY = endY
            )
            break
        } else {
            //Set the node to considered so the algorithm does not go back
            mazeMap[Pair(currentX, currentY)] = Node.Considered
        }


        //Consider possible next step starting from top and moving clockwise
        //TOP
        //If checks for three conditions: within boundaries, not considered and not wall
        if (checkIfValidPath(
                mazeWidth,
                mazeHeight,
                mazeMap,
                currentX,
                currentY,
                Direction.TOP
            )
        ) {
            Log.i(LOG_TAG, "Found path TOP : (${currentX},${currentY - 1})")

            //Sets the parent of found passage to the current node
            parentMap[Pair(currentX, currentY - 1)] = Pair(currentX, currentY)

            //Adds the found node to the queue to check
            queueToCheck.add(Pair(currentX, currentY - 1))
        }

        //RIGHT
        if (checkIfValidPath(
                mazeWidth,
                mazeHeight,
                mazeMap,
                currentX,
                currentY,
                Direction.RIGHT
            )
        ) {
            Log.i(LOG_TAG, "Found path RIGHT : (${currentX + 1},${currentY})")
            parentMap[Pair(currentX + 1, currentY)] = Pair(currentX, currentY)
            queueToCheck.add(Pair(currentX + 1, currentY))
        }

        //DOWN
        if (checkIfValidPath(
                mazeWidth,
                mazeHeight,
                mazeMap,
                currentX,
                currentY,
                Direction.DOWN
            )
        ) {
            Log.i(LOG_TAG, "Found path DOWN : (${currentX},${currentY + 1})")
            parentMap[Pair(currentX, currentY + 1)] = Pair(currentX, currentY)
            queueToCheck.add(Pair(currentX, currentY + 1))
        }

        //LEFT
        if (checkIfValidPath(
                mazeWidth,
                mazeHeight,
                mazeMap,
                currentX,
                currentY,
                Direction.LEFT
            )
        ) {
            Log.i(LOG_TAG, "Found path LEFT : (${currentX - 1},${currentY})")
            parentMap[Pair(currentX - 1, currentY)] = Pair(currentX, currentY)
            queueToCheck.add(Pair(currentX - 1, currentY))
        }

        //Tries to retrieve from the queue and catches the exception if there are no elements
        try {
            currentNode = queueToCheck.remove()
            Log.i(LOG_TAG, queueToCheck.toString())
        } catch (e: java.util.NoSuchElementException) {
            Log.i(LOG_TAG, "No more elements in the queue. Did not manage to find the path!")
            return
        }

        //Reassigns the values of coordinates for current node
        currentX = currentNode.first
        currentY = currentNode.second
        //Slows down the execution
        delay(100)
        Log.i(LOG_TAG, "Current Node is: (${currentX},${currentY})")
    }
}

private suspend fun getFinalPath(
    mazeMap: MutableMap<Pair<Int, Int>, Node>,
    parentMap: MutableMap<Pair<Int, Int>, Pair<Int, Int>>,
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int
) {
    var currentXCoordinate = endX
    var currentYCoordinate = endY
    while (currentXCoordinate != startX && currentYCoordinate != startY) {
        //Updates the node in map to be the final path
        mazeMap[Pair(currentXCoordinate, currentYCoordinate)] = Node.FinalPath
        //Gets the parent of node from the map
        currentXCoordinate = parentMap[Pair(currentXCoordinate, currentYCoordinate)]!!.first
        currentYCoordinate = parentMap[Pair(currentXCoordinate, currentYCoordinate)]!!.second
        //Slows down the execution
        delay(50)
    }
}

//Enum class used by the private function
private enum class Direction {
    TOP, RIGHT, DOWN, LEFT
}

//Private functions that checks for valid paths in all directions
private fun checkIfValidPath(
    mazeWidth: Int,
    mazeHeight: Int,
    mazeList: MutableMap<Pair<Int, Int>, Node>,
    currentX: Int,
    currentY: Int,
    direction: Direction,
): Boolean {
    when (direction) {
        //Checks top
        Direction.TOP -> {
            if (currentY - 1 > 0 &&
                mazeList[Pair(currentX, currentY - 1)] == Node.Corridor
            ) {
                return true
            }
        }

        //Checks right
        Direction.RIGHT -> {
            if (currentX + 1 <= mazeWidth &&
                mazeList[Pair(currentX + 1, currentY)] == Node.Corridor
            ) {
                return true
            }
        }

        //Checks down
        Direction.DOWN -> {
            if (currentY + 1 < mazeHeight &&
                mazeList[Pair(currentX, currentY + 1)] == Node.Corridor
            ) {
                return true
            }
        }

        //Checks left
        Direction.LEFT -> {
            if (currentX - 1 >= 0 &&
                mazeList[Pair(currentX - 1, currentY)] == Node.Corridor
            ) {
                return true
            }
        }
    }
    return false
}