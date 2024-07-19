package chapter06

object L11_Object {
  def main(args: Array[String]): Unit = {
//    val s11 = new Student11("scq", 22)
//    s11.printInfo()

    val s12 = Student11.StudentInstance("scq", 22)
    s12.printInfo()

    val s13 = Student11.apply("sss", 333)
    s13.printInfo()

    Student11("apply", 44).printInfo()
  }
}


class Student11 private(val name: String, val age: Int) {
  def printInfo(): Unit ={
    println(s"student: name= $name, age=$age, school=${Student11.school}")
  }
}

object Student11 {
  val school: String = "mit"

  def StudentInstance(name: String, age: Int): Student11 = {
    new Student11(name, age)
  }

  def apply(name: String, age: Int): Student11 = new Student11(name, age)
}