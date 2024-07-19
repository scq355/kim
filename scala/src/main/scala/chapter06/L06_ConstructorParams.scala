package chapter06

object L06_ConstructorParams {
  def main(args: Array[String]): Unit = {
    val s2 = new Student2
    println(s"s2 name=${s2.name}, age=${s2.age}")

    val s3: Student3 = new Student3("scq", 23)
    println(s"s3 name=${s3.name}, age=${s3.age}")

    val s4: Student4 = new Student4("Kim", 33)
    s4.printInfo()

    val s5: Student5 = new Student5("scq", 23)
    println(s"s5 name=${s5.name}, age=${s5.age}")

    val s6: Student6 = new Student6("scq", 23, "mit")
    s6.printInfo()
  }
}


class Student2 {
  var name: String = _
  var age: Int = _
}

class Student3(var name: String, var age: Int)

class Student4(name: String, age: Int) {
  def printInfo(): Unit = {
    println(s"s3 name=${name}, age=${age}")
  }
}

class Student41(_name: String, _age: Int) {
  var name: String = _name
  var age: Int = _age
}

class Student5(val name: String, val age: Int)

class Student6(var name: String, var age: Int) {

  var school: String = _

  def this(name: String, age: Int, school: String) {
    this(name, age)
    this.school = school
  }

  def printInfo(): Unit = {
    println(s"s6 name=$name, age=$age, school=$school")
  }
}

