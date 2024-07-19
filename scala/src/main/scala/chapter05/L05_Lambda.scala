package chapter05

object L05_Lambda {

  def main(args: Array[String]): Unit = {
    val fun: String => Unit = (name: String) => {
      println(name)
    }
    fun("scqq")

    def f(func: String => Unit): Unit ={
      func("scq")
    }

    f(fun)
    f((name: String) => {println(name)})

    // 参数类型可省略，形参自动推到
    f((name) => {println(name)})

    // 一个参数时，圆括号可省略
    f(name => {println(name)})

    // 函数体一行，括号可以省略
    f(name => println(name))

    // 参数出现了一次，参数省略，参数用_代替
    f(println(_))

    f(println)


    def dualFunction(fun: (Int, Int) => Int): Int = {
      fun(1, 2)
    }

    val add = (a: Int, b: Int) => a + b
    val minus = (a: Int, b: Int) => a - b

    println(dualFunction(add))
    println(dualFunction(minus))

    println(dualFunction((a: Int, b: Int) => a + b))
    println(dualFunction((a: Int, b: Int) => a - b))

    println(dualFunction(_ + _))
    println(dualFunction(_ - _))

    println(dualFunction((a: Int, b: Int) => b - a))
    println(dualFunction( -_ - _))

  }
}
