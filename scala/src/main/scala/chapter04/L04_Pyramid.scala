package chapter04

object L04_Pyramid {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 9) {
      val stars = 2 * i - 1
      val spaces = 9 - i
      println(" " * spaces + "*" * stars)
    }

    for (i <- 1 to 9; stars = 2 * i - 1; spaces = 9 - i) {
      println(" " * spaces + "*" * stars)
    }

    for (stars <- 1 to 17 by 2; spaces = (17 - stars) / 2) {
      println(" " * spaces + "*" * stars)
    }

  }

}
