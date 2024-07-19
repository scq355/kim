package chapter06

object L17_Extends {
  def main(args: Array[String]): Unit = {
    val scq: Student17 = new Student17("scq", 18)
    scq.sayHi()
    scq.study()

    val bob: Person17 = new Student17("bob", 23)
    bob.sayHi()

    val tom: Person17 = new Person17("tom", 23)
    tom.sayHi()

    println("=======================================")
    // 类型判断
    println("student17 " + scq.isInstanceOf[Student17])
    println("person " + scq.isInstanceOf[Person17])
    println("student17 " + bob.isInstanceOf[Student17])
    println("person " + bob.isInstanceOf[Person17])
    println("student17 " + tom.isInstanceOf[Student17])
    println("person " + tom.isInstanceOf[Person17])

    // 类型转换
    if (bob.isInstanceOf[Student17]) {
      val newStu = bob.asInstanceOf[Student17]
      newStu.study()
    }

    println(classOf[Student17])

    println(WorkDay.MONDAY)

  }
}

class Person17(val name:String, val age: Int) {
  def sayHi(): Unit = {
    println(s"person $name")
  }
}

class Student17(name:String, age: Int) extends Person17(name, age) {
  override def sayHi(): Unit = {
    println(s"student $name")
  }

  def study(): Unit = {
    println("study")
  }
}


// 枚举
object WorkDay extends Enumeration {
  val MONDAY = Value(1, "Monday")
  val TUESDAY = Value(2, "TuesDay")
}

// 应用类
object TestApp extends App {
  println("app start")

  type  MyString = String

  val a: MyString = "abc"
  println(a)
}
