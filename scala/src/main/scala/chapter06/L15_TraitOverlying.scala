package chapter06

object L15_TraitOverlying {
  def main(args: Array[String]): Unit = {
    val stu15 = new Student15
    stu15.increase()


    //钻石问题叠加
    val ball = new MyFootBall
    println(ball.describe())
  }
}


trait Ball {
  def describe(): String = "ball"
}

trait ColorBall extends Ball {

  var color: String = "red"

  override def describe(): String = color + " " +  super.describe()
}

trait CategoryBall extends Ball {
  var category:String = "foot"

  override def describe(): String = category + " " + super.describe()
}

class MyFootBall extends CategoryBall with ColorBall {
  override def describe(): String = "my ball is" + " " + super[CategoryBall].describe()
}

trait Knowledge15 {
  var amount: Int = 0

  def increase(): Unit = {
    println("knowledge increase")
  }
}


trait Talent15 {
  def singing(): Unit

  def dancing(): Unit

  def increase(): Unit = {
    println("talent increase")
  }
}

class Student15 extends Person13 with Talent15 with Knowledge15 {

  override def singing(): Unit = println("singing")

  override def dancing(): Unit = println("dancing")

  override def increase(): Unit = super[Talent15].increase()
}