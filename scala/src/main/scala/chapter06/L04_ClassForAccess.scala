package chapter06

object L04_ClassForAccess {

}


class Person {
  private var idCard: String = "342342"
  protected var name: String = "alice"
  var gender: String = "female"

  private[chapter06] var age: Int = 18

  def printInfo(): Unit = {
    println(s"Person: $idCard $name $gender $age")
  }
}