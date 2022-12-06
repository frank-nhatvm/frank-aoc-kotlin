import java.io.File

fun main() {

    fun readInput(input: String): List<List<Int>>{
        return input.split("\n\n").map {elf -> elf.split("\n").map { it.toInt() } }
    }

    fun part1(input: String): Int {
        val data = readInput(input)
        val sumList =  data.map { it.sum() }
        return sumList.max()
    }

    fun part2(input: String): Int{
        val data = readInput(input)
        val sortedSumList = data.map { it.sum() }.sortedDescending()
        val top3Elfs = sortedSumList.take(3)
        return  top3Elfs.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = File("src","Day01_test.txt").readText()
    check(part1(testInput) == 24000)

    val input = File("src","Day01.txt").readText()
    println(part1(input))
    println(part2(input))
}
