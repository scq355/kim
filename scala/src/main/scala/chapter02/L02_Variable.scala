package chapter02

import chapter01.Student

object L02_Variable {
  def main(args: Array[String]): Unit = {
    var a: Int = 10
    val b: String = "hello"

    var scq = new Student("scq", 33)
    scq = null

    val bob = new Student("bob", 23)
    bob.age = 24
    bob.printInfo()
  }
}
