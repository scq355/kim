package chapter05

object L01_FunctionMethod {
  def main(args: Array[String]): Unit = {
    def hi(words: String): Unit = {
      println("hi, " + words)
    }

    hi("scq")

    L01_FunctionMethod.hi("alice")

    val result = L01_FunctionMethod.hello("Mike")
    println(result)
  }

  def hi(words: String): Unit = {
    println("Hi, " + words)
  }

  def hello(words: String): String = {
    println("Hi, " + words)
    "Hello"
  }
}
