package com.jorgetrujillo.graphqlclient

import groovy.util.logging.Slf4j

@Slf4j
class StringTestUtil {

  static boolean requestsEqual(String one, String two) {
    List<String> linesOne = one
        .split('\n')
        .collect { it.trim() }
        .findAll { !it.isEmpty() }
    List<String> linesTwo = two
        .split('\n')
        .collect { it.trim() }
        .findAll { !it.isEmpty() }

    if (linesOne.size() != linesTwo.size()) {
      log.info("Sizes are different: ${linesOne.size()} vs ${linesTwo.size()}")
      return false
    }

    boolean isDifferent = (0..linesOne.size() - 1).any {
      if (linesOne[it] != linesTwo[it]) {
        log.info("Lines different: ${linesOne[it]} vs ${linesTwo[it]}")
        return true
      }
      return false
    }

    return !isDifferent
  }

}
