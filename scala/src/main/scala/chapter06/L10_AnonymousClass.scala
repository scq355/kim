package chapter06

object L10_AnonymousClass {
  def main(args: Array[String]): Unit = {
    val p10 = new Person10 {
      override var name: String = "scq"

      override def eat(): Unit = println("eat")
    }

    p10.eat()
    println(p10.name)
  }
}

abstract class Person10 {
  var name: String
  def eat(): Unit
}
