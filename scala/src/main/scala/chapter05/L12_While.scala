package chapter05

import scala.annotation.tailrec

object L12_While {

  def main(args: Array[String]): Unit = {
    var n = 10
    while (n > 0) {
      println(n)
      n -= 1
    }

    def myWhile(condition: => Boolean): (=> Unit) => Unit = {
      def doLoop(op: => Unit): Unit = {
        if (condition) {
          op
          myWhile(condition)(op)
        }
      }

      doLoop _
    }

    n = 10
    myWhile(n > 0) {
      println(n)
      n -= 1
    }

    def myWhile2(condition: => Boolean): (=> Unit) => Unit = {
      op => {
        if (condition) {
          op
          myWhile2(condition)(op)
        }
      }
    }

    n = 10
    myWhile2(n > 0) {
      println(n)
      n -= 1
    }


    @tailrec
    def myWhile3(condition: => Boolean)(op: => Unit): Unit = {
      if (condition) {
        op
        myWhile3(condition)(op)
      }
    }

    n = 10
    myWhile3(n > 0) {
      println(n)
      n -= 1
    }

  }
}
