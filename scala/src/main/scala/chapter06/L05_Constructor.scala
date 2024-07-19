package chapter06

object L05_Constructor {
  def main(args: Array[String]): Unit = {
    val s1 = new Student1
    s1.student1()
    val s2 = new Student1("scq")
    val s3 = new Student1("scq", 11)
  }
}


class Student1() {
  var name: String = _
  var age: Int = _

  println("主构造器被调用")

  def this(name: String) {
    this()
    println("辅助构造器1被调用")
    this.name = name
    println(s"name: $name, age: $age")
  }

  def this(name: String, age: Int) {
    this(name)
    println("辅助构造器2被调用")
    this.age = age
    println(s"name: $name, age: $age")
  }

  def student1(): Unit = {
    println("不是构造器，只是一个普通方法")
  }

}
