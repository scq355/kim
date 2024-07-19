package chapter06

object L13_Trait {
  def main(args: Array[String]): Unit = {
    val s1 = new Student13()
    s1.sayHello()
    s1.study()
    s1.dating()
    s1.play()
  }
}

class Person13 {
  val name: String = "person"
  var age: Int = 18
  def sayHello(): Unit = {
    println("hello from " + name)
  }
}

trait Young {
  val name: String = "young"
  var age: Int

  def play(): Unit = {
    println(s"$name 年轻人")
  }

  def dating(): Unit
}


class Student13 extends Person13 with Young {
  // 重写冲突的属性
  override val name: String = "student"

  override def dating(): Unit = println(s"$name 约会")

  def study(): Unit = println(s"$name 学习")


  override def sayHello(): Unit = {
    super.sayHello()
    println(s"hello from $name")
  }
}