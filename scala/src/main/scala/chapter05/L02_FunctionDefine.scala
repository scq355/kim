package chapter05

object L02_FunctionDefine {
  def main(args: Array[String]): Unit = {

    def f1(): Unit = {
      println("无参无返回")
    }

    f1()
    println(f1())

    def f2(): Int = {
      println("无参有返回")
      return 1
    }

    f2()
    println(f2())

    def f3(name: String): Unit = {
      println("有参无返回" + name)
    }

    f3("scq")
    println(f3("scq"))


    def f4(name: String): String = {
      println("有参有返回" + name)
      return name
    }

    f4("scq")
    println(f4("scq"))


    def f5(name: String, age: Int): Unit = {
      println("多参无返回" + name + " " + age)
    }

    f5("scq", 11)
    println(f5("scq", 33))


    def f6(name: String, age: Int): String = {
      println("多参有返回" + name + " " + age)
      return name + age
    }

    f6("scq", 11)
    println(f6("scq", 33))

  }
}
