import kotlin.random.Random

const val MAX = 1_000_005

fun solve(n: Int) {
    when (n) {
        1 -> println(1)
        2, 3 -> println("No Solution")
        4 -> println("2 4 1 3")
        else -> {
            println("YES")
            val res = mutableListOf<Int>()
            for (i in 1..n step 2) res.add(i)
            for (i in 2..n step 2) res.add(i)
            println(res.joinToString(" "))
        }
    }
}

fun main() {
    val n = Random.nextInt(1, MAX)
    println("Random n = $n")
    solve(n)
}
