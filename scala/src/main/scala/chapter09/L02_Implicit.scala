package chapter09

object L02_Implicit {
  def main(args: Array[String]): Unit = {
    // 隐式函数
    implicit def convert(num: Int): MyRichInt = new MyRichInt(num)

    val new12 = new MyRichInt(12)
    println(new12.myMax(13))

    println(12.myMin(2))

    implicit class MyRichInt2(val self: Int) {
      def myMax2(n: Int): Int = if (n < self) self else n
      def myMin2(n: Int): Int = if (n < self) self else self
    }

    println(12.myMin2(2))


    implicit val str: String = "ssccqq"
    implicit val num: Int = 18

    // 隐式参数
    def sayHello()(implicit name:String):Unit = {
      println("hello " + name)
    }

    def sayHi(implicit name:String = "scq"):Unit = {
      println("hi " + name)
    }

    sayHello()
    sayHi()
    sayHi

    // 简化写法
    def hiage(): Unit ={
      println("hi " + implicitly[Int])
    }

    hiage
  }
}

class MyRichInt(val self: Int) {
  def myMax(n: Int): Int = if (n < self) self else n
  def myMin(n: Int): Int = if (n < self) self else self
}
