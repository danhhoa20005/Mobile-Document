import kotlin.random.Random

const val MX = 1_000_005

fun solve(n: Int) {
    val sum = n * (n + 1) / 2
    if (sum % 2 != 0) {
        println("NO")
        return
    }

    val half = sum / 2
    val a = mutableListOf<Int>()
    val b = mutableListOf<Int>()
    var s = 0

    for (i in n downTo 1) {
        if (s + i <= half) {
            a.add(i)
            s += i
        } else {
            b.add(i)
        }
    }

    println("YES")
    println(a.joinToString(" "))
    println(b.joinToString(" "))
}

fun main() {
    val n = Random.nextInt(1, MX)
    solve(n)
}
