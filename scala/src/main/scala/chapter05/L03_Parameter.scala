package chapter05

object L03_Parameter {
  def main(args: Array[String]): Unit = {
    def f1(str: String*): Unit = {
      println(str)
    }

    f1("aaa")
    f1("aaa", "bbb", "ccc")

    def f2(data: String, str: String*): Unit = {
      println(data + " " + str)
    }

    f2("aaa")
    f2("aaa", "bbb", "ccc")


    def f3(data: String = "star"): Unit = {
      println(data)
    }

    f3("aaa")
    f3()


    // 带名参数
    def f4(name: String = "mit", age: Int): Unit = {
      println(s"${name},${age}")
    }

    f4("aaa", 22)
    f4(age = 34, name = "345")
    f4(age = 22)
  }
}
