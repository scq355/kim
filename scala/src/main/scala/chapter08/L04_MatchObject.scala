package chapter08

object L04_MatchObject {
  def main(args: Array[String]): Unit = {
    val student = new Student("scq", 23)
    val result = student match {
      case Student("scq", 23) => "scq 23"
      case _ => "some else"
    }
    println(result)
  }
}

class Student(val name:String, val age: Int)

object Student {
  def apply(name: String, age: Int): Student = new Student(name, age)

  def unapply(stu: Student): Option[(String, Int)] = {
    if (stu == null) {
      None
    } else {
      Some(stu.name, stu.age)
    }
  }
}