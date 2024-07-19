package chapter02

import java.io.PrintWriter
import java.io.File
import scala.io.Source

object L06_FileIO {
  def main(args: Array[String]): Unit = {
    Source.fromFile("scala/src/main/resources/test.txt").foreach(print)

    val writer = new PrintWriter(new File("scala/src/main/resources/test_write.txt"))
    writer.write("hello scala from java")
    writer.close()
  }
}
