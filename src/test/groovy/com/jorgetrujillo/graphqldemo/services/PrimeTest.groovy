package com.jorgetrujillo.graphqldemo.services

import groovy.transform.CompileStatic

@CompileStatic
class PrimeTest {

  static boolean isPrime(int number) {
    if (number <= 1) {
      return false
    }

    boolean foundDivisor = (2..(number / 2)).find { number % it == 0 }
    return !foundDivisor
  }

  static void main(String[] args) {
    (1..10).each {
      long start = System.currentTimeMillis()
      List<Integer> primes = (1..100000).findAll { isPrime(it) }
      long totalTime = System.currentTimeMillis() - start

      println("Found ${primes.size()} primes in ${totalTime} ms")
    }
  }
}
