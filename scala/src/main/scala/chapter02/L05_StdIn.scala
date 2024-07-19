package chapter02

import scala.io.StdIn

object L05_StdIn {

  def main(args: Array[String]): Unit = {
    println("请输入name：")
    val name: String = StdIn.readLine()
    println("请输入age")
    val age = StdIn.readInt()
    println(s"${name}, ${age}")
  }

}
