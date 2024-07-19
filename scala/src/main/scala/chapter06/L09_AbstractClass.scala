package chapter06

object L09_AbstractClass {
  def main(args: Array[String]): Unit = {
    val s9 = new Student9
    s9.eat()
    s9.sleep()
  }
}


abstract class Person9 {
  val name: String = "person"

  // 抽象属性
  var age: Int

  def eat(): Unit = {
    println("eat")
  }

  // 抽象方法
  def sleep(): Unit

}

class Student9 extends Person9 {

  override var age: Int = 23

  override def sleep(): Unit = {
    println("sleep...")
  }

  override def eat(): Unit = {
    super.eat()
    println("eat...")
  }
}