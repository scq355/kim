package chapter06

object L08_DynamicBind {
  def main(args: Array[String]): Unit = {
    val s8: Person8 = new Student8
    // 属性方法都是动态绑定
    println(s8.name)
    s8.hello()

  }
}


class Person8 {
  val name: String = "person"

  def hello(): Unit = {
    println("person8")
  }
}

class Student8 extends Person8 {
  override val name: String = "student"

  override def hello(): Unit = println("student")
}