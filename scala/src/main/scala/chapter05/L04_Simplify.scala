package chapter05

// 函数至简原则
object L04_Simplify {
  def main(args: Array[String]): Unit = {
    def f0(name: String): String = {
      return name
    }
    println(f0("scq"))

    def f1(name: String): String = {
      name
    }
    println(f1("scq"))


    def f2(name: String): String = name
    println(f2("scq"))

    def f3(name: String) = name
    println(f3("scq"))

    // return存在，返回值类型声明不能省略
//    def f4(name: String) = {
//      return name
//    }

    // unit下，return不起作用
    def f5(name: String): Unit = {
      return name
    }

    // 无返回值类型，可以省略等号
    def f6(name: String) {
      println(name)
    }
    println(f6("scq"))

    def f7(): Unit = {
      println("scqqqq")
    }
    f7()
    f7

    def f8: Unit = {
      println("scqqqq")
    }
    f8

    def f9(name: String): Unit = {
      println(name)
    }
    // 匿名函数（lambda表达式）只关心逻辑名称无所谓，则函数名可以省略
    (name: String) => {println(name)}

  }
}
