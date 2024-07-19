package chapter06

import scala.beans.BeanProperty

object L03_Class {
  def main(args: Array[String]): Unit = {
    val student = new Student
    println(student.gender)
    println(student.age)
    student.gender = "male"
    println(student.gender)
  }

}


class Student {
  private var name: String = "alica"
  @BeanProperty
  var age: Int = _
  var gender: String = _
}
