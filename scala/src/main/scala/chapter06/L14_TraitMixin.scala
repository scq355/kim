package chapter06

object L14_TraitMixin {
  def main(args: Array[String]): Unit = {
    val s14 = new Student14
    s14.study()
    s14.increase()

    s14.play()
    s14.increase()

    s14.play()
    s14.increase()

    s14.dating()
    s14.increase()

    // 动态混入
    val studentWithTalent = new Student14 with Talent {
      override def singing(): Unit = println(s"$name good at singing")

      override def dancing(): Unit = println(s"$name good at dancing")
    }

    studentWithTalent.play()
    studentWithTalent.dating()
    studentWithTalent.increase()
    studentWithTalent.study()
    studentWithTalent.singing()
    studentWithTalent.dancing()
  }
}

trait Knowledge {
  var amount: Int = 0

  def increase(): Unit
}


trait Talent {
  def singing(): Unit

  def dancing(): Unit
}

class Student14 extends Person13 with Young with Knowledge {
  override def increase(): Unit = {
    amount += 1
    println(s"$name knowledge increased $amount")
  }

  // 重写冲突的属性
  override val name: String = "student"

  override def dating(): Unit = println(s"$name 约会")

  def study(): Unit = println(s"$name 学习")

  override def sayHello(): Unit = {
    super.sayHello()
    println(s"hello from $name")
  }
}