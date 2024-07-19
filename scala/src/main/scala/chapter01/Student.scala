package chapter01

class Student(val name: String, var age: Int) {
  def printInfo(): Unit = {
    println(name + " " + age + " " + Student.school)
  }

}

// 伴生对象
object Student {
  val school: String = "mit"

  def main(args: Array[String]): Unit = {
    new Student("scq", 34).printInfo()
  }
}