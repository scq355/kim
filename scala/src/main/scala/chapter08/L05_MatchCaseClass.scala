package chapter08

object L05_MatchCaseClass {

  def main(args: Array[String]): Unit = {
    val s = new Student1("scq", 23)
    val result = s match {
      case Student1("scq", 23) => "scq 23"
      case _ => "some else"
    }
    println(result)
  }

}


case class Student1(name:String, age: Int)