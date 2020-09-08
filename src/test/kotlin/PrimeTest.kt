fun isPrime(number: Int): Boolean {
  if (number <= 1) {
    return false
  }

  val divisorFound = (2..(number / 2)).any { divisor -> number % divisor == 0 }
  return !divisorFound
}

fun main(args: Array<String>) {
  (1..10).forEach {
    val startTime = System.currentTimeMillis()
    val primes: List<Int> = (1..100000).filter { isPrime(it) }
    val totalTime = System.currentTimeMillis() - startTime
    println("Found ${primes.size} primes in $totalTime ms")
  }
}
