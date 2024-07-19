package chapter06

object L12_Singleton {
  def main(args: Array[String]): Unit = {
    val s1 = Student12.getInstance()

    val s2 = Student12.getInstance()

    println(s2)
    println(s1)
  }
}

class Student12 private(val name: String, val age: Int) {
  def printInfo(): Unit = {
    println(s"student: name= $name, age=$age, school=${Student11.school}")
  }
}

// 饿汉式
//object Student12 {
//  private val student: Student12 = new Student12("scq", 23)
//
//  def getInstance(): Student12 = student
//}


// 懒汉式
object Student12 {
  private var student: Student12 = _

  def getInstance(): Student12 = {
    if (student == null) {
       student = new Student12("scq", 23)
    }
    student
  }
}
