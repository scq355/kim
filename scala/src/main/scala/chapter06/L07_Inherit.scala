package chapter06

object L07_Inherit {
  def main(args: Array[String]): Unit = {
    val s1: Student7 = new Student7("scq", 65)
    val s2: Student7 = new Student7("scq", 34, "mit")

    s1.printInfo()
    s2.printInfo()


    println("=============================")

    val t = new Teacher
    t.printInfo()

    def personInfo(p: Person7): Unit = {
      p.printInfo()
    }

    val p = new Person7()
    personInfo(s1)
    personInfo(t)
    personInfo(p)
  }
}

class Person7() {
  var name: String = _
  var age: Int = _
  println("1父类主构造器")

  def this(name: String, age: Int) {
    this()
    println("2父类辅助构造器")
    this.name = name
    this.age = age
  }

  def printInfo(): Unit = {
    println(s"Person: ${name}, ${age}")
  }
}


class Student7(name: String, age: Int) extends Person7(name, age) {
  var stdNo: String = _

  println("3子类主构造器")

  def this(name: String, age: Int, stdNo: String){
    this(name, age)
    println("4子类辅助构造器")
    this.stdNo = stdNo
  }

  override def printInfo(): Unit = {
    println(s"Student: ${name}, ${age}, $stdNo")
  }
}


class Teacher extends Person7 {
  override def printInfo(): Unit = {
    println(s"Teacher")
  }
}
