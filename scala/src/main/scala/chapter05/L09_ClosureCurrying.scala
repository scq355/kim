package chapter05

object L09_ClosureCurrying {
  def main(args: Array[String]): Unit = {
    def add(a: Int, b: Int): Int = {
      a + b
    }

    def addBy4(b: Int): Int = {
      b + 4
    }

    def addBy5(b: Int): Int = {
      b + 5
    }

    def addBy41(): Int => Int = {
      val a = 4

      def addB(b: Int): Int = {
        a + b
      }

      addB
    }

    def addByA(a: Int): Int => Int = {
      def addB(b: Int): Int = {
        a + b
      }

      addB
    }

    println(addByA(23)(21))


    // lambda
    def addByA1(a: Int): Int => Int = {
      b => a + b
    }

    def addByA2(a: Int): Int => Int = a + _

    println(addByA1(12)(23))
    println(addByA2(12)(23))

    // currying
    def addCurrying(a: Int)(b: Int): Int = {
      a + b
    }
    println(addCurrying(23)(21))

  }
}
