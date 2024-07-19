package chapter04

import scala.util.control.Breaks

object L06_Break {
  def main(args: Array[String]): Unit = {
    try {
      for (i <- 0 until(5)) {
        if(i == 3) {
          throw new RuntimeException
        }
        println(i)
      }
    } catch {
      case e: Exception =>
    }


    Breaks.breakable(
      for (i <- 0 until(5)) {
      if(i == 3) {
        Breaks.break()
      }
      println(i)
    }
    )
  }



}
