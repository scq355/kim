package chapter05

object L06_HighFunction {

  def main(args: Array[String]): Unit = {
    def f(n: Int): Int = {
      println("f调用")
      n + 1
    }

    val result = f(123)
    println(result)

    val f1: Int => Int = f
    val f2 = f _

    println(f1)
    println(f1(123))

    println(f2)
    println(f2(123))

    def fun(): Int = {
      println("fun")
      1
    }

    val f3: () => Int = fun
    val f4 = fun _
    println(f3)
    println(f4)

    // 函数作为参数
    def dualEval(op: (Int, Int) => Int, a: Int, b: Int): Int = {
      op(a, b)
    }

    def add(a: Int, b: Int): Int = {
      a + b
    }

    println(dualEval(add, 12, 35))
    println(dualEval((a, b) => a + b, 12, 35))
    println(dualEval(_ + _, 12, 35))

    // 函数作为返回值
    def f5(): Int => Unit = {
      def f6(a: Int): Unit = {
        println("f6" + a)
      }
      f6
    }

    val function = f5()
    println(function(23))
    println(f5()(23))
  }
}
