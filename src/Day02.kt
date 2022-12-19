enum class GamePoint(val point: Int) {
    Lost(0),
    Draw(3),
    Won(6);

    companion object {
        fun fromString(characterShape: String): GamePoint {
            return when (characterShape) {
                "X" -> Lost
                "Y" -> Draw
                "Z" -> Won
                else -> throw  Exception("Invalid game point shape")
            }
        }
    }
}

enum class GameShape {
    Rock,
    Paper,
    Scissors;

    // Rock defeats Scissors, Scissors defeats Paper , Paper defeats Rock
    // lost : 0 point
    // draw: 3 points
    // won: 6 points
    fun calGamePoint(shape2: GameShape): GamePoint {
        return when (this) {
            Rock -> {
                when (shape2) {
                    // rock : draw
                    Rock -> GamePoint.Draw
                    // paper: won
                    Paper -> GamePoint.Lost
                    // scissors : lost
                    else -> GamePoint.Won
                }
            }
            Paper -> {
                when (shape2) {
                    // rock: lost
                    Rock -> GamePoint.Won
                    // paper : draw
                    Paper -> GamePoint.Draw
                    // scissors : won
                    else -> GamePoint.Lost
                }
            }
            // scissors
            else -> {
                when (shape2) {
                    Rock -> GamePoint.Lost
                    Paper -> GamePoint.Won
                    else -> GamePoint.Draw
                }
            }
        }
    }

    // x : rock 1 point
    // y: paper 2 points
    // z: scissors 3 points
    fun pointForShape(): Int {
        return when (this) {
            Rock -> 1
            Paper -> 2
            // scissors
            else -> 3
        }

    }
}

data class GamePair(val opponentShape: GameShape, val yourShape: GameShape) {

    companion object {
        fun fromListInput(input: List<String>): GamePair {
            require(input.size == 2) {
                throw  Exception("Input for game pair is invalid. (Size is not equal 2)")
            }
            return GamePair(input[0].uppercase().toGameShape(), input[1].uppercase().toGameShape())
        }
    }

    private fun calGamePoint(): GamePoint {
        return yourShape.calGamePoint(opponentShape)
    }

    fun calPoint(): Int {
        val gamePoint = calGamePoint()
        return gamePoint.point + yourShape.pointForShape()
    }

}

fun String.toGameShape(): GameShape {
    return when (this) {
        "A", "X" -> GameShape.Rock
        "B", "Y" -> GameShape.Paper
        "C", "Z" -> GameShape.Scissors
        else -> throw  Exception()
    }
}

data class GamePointPair(val opponentShape: GameShape, val gamePoint: GamePoint) {

    companion object {
        fun fromListInput(input: List<String>): GamePointPair {
            require(input.size == 2) {
                throw Exception("Input for game point pair is invalid.")
            }
            return GamePointPair(input[0].uppercase().toGameShape(), GamePoint.fromString(input[1]))
        }
    }

    private fun getYourGameShape(): GameShape {
        return when (gamePoint) {
            GamePoint.Lost -> {
                when (opponentShape) {
                    GameShape.Rock -> GameShape.Scissors
                    GameShape.Scissors -> GameShape.Paper
                    GameShape.Paper -> GameShape.Rock
                }
            }
            GamePoint.Draw -> {
                when (opponentShape) {
                    GameShape.Rock -> GameShape.Rock
                    GameShape.Scissors -> GameShape.Scissors
                    GameShape.Paper -> GameShape.Paper
                }
            }
            GamePoint.Won -> {
                when (opponentShape) {
                    GameShape.Rock -> GameShape.Paper
                    GameShape.Scissors -> GameShape.Rock
                    GameShape.Paper -> GameShape.Scissors
                }
            }
        }
    }

    fun calPoint(): Int {
        val yourShape = getYourGameShape()
        //    println("GamePairPoint: opponentShape:${opponentShape.name} -- yourShape:${yourShape.name} -- gamePoint:${gamePoint.name} -- shapePoint:${yourShape.pointForShape()} -- gamePoint:${gamePoint.point}")
        return yourShape.pointForShape() + gamePoint.point
    }

}

fun main() {

    fun part1(input: List<String>): Int {
        val games: List<GamePair> = input.map { GamePair.fromListInput(it.split(" ")) }
        return games.sumOf { gamePair -> gamePair.calPoint() }
    }

    fun part2(input: List<String>): Int {
        val games: List<GamePointPair> = input.map { GamePointPair.fromListInput(it.split(" ")) }
        return games.sumOf { it.calPoint() }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))

}
