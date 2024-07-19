package chapter04

import scala.io.StdIn

object L01_IfElse {
  def main(args: Array[String]): Unit = {
    println("输入年龄")
    val age: Int = StdIn.readInt()

    if (age >= 18) {
      println("成年")
    }

    if (age >= 18) {
      println("未成年")
    } else {
      println("成年")
    }

    val result: Any = if (age < 6) {
      println("童年")
      "童年"
    } else if (age < 18) {
      println("青年")
      age
    } else {
      println("成年")
      "成年"
    }
    println(result)

    val res1: String = if (age >= 18) {
      "未成年"
    } else {
      "成年"
    }

    val res2: String = if (age >= 18) "未成年" else "成年"


  }
}
