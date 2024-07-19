package chapter04

object L05_WhileLoop {

  def main(args: Array[String]): Unit = {
    var a: Int = 10
    while (a >= 1) {
      println(a)
      a -= 1
    }

    var b: Int = 0
    do {
      println(b)
      b -= 1
    } while (b > 0)
  }

}
