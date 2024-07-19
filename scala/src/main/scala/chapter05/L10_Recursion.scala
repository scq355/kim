package chapter05

import scala.annotation.tailrec

object L10_Recursion {
  def main(args: Array[String]): Unit = {
    println(fac(10))
    println(tailFac(10))
  }

  def fac(n: Int): Long = {
    if (n == 0) return 1
    fac(n - 1) * n
  }

  def tailFac(n: Int): Long = {
    @tailrec
    def loop(n: Int, result: Int): Int = {
      if (n == 0) return result
      loop(n - 1, result * n)
    }
    loop(n, 1)
  }

}
