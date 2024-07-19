package chapter05

object L11_ControlAbstraction {

  def main(args: Array[String]): Unit = {
    // 传值参数
    def f0(a: Int): Unit = {
      println("a=" + a)
      println("a=" + a)
    }
    f0(23)

    def f1():Int = {
      println("f1")
      12
    }
    f0(f1())

    println("======================================")

    // 传名参数，传递的是代码块
    def f2(a: =>Int):Unit = {
      println("a:" + a)
      println("a:" + a)
    }

    f2(f1())
    f2(23)
    f2({
      println("code block")
      33
    })
  }
}
