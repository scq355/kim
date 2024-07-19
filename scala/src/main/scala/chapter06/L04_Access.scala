package chapter06

object L04_Access {
  def main(args: Array[String]): Unit = {
    val person = new Person()
    println(person.age)
    println(person.gender)
    person.printInfo()


    val worker = new Worker()
    worker.age = 32
    worker.printInfo()
  }
}


class Worker extends Person {
  override def printInfo(): Unit = {
    println("worker:")
    name = "bob"
    age = 24
    gender = "male"
    println(s"Worker: $name $gender $age")
  }
}