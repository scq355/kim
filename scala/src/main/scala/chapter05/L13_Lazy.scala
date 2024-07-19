package chapter05

object L13_Lazy {
  def main(args: Array[String]): Unit = {
    lazy val result: Int = sum(12, 18)

    println("invoke")
    println("result=" + result)
    println("result=" + result)
  }

  def sum(a: Int, b: Int): Int = {
    println("sum")
    a + b
  }
}
