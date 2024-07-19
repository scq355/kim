package chapter07

object L03_MulArray {

  def main(args: Array[String]): Unit = {
    // 二维数组
    val array: Array[Array[Int]] = Array.ofDim[Int](3, 3)

    array(0)(2) = 12
    array(1)(1) = 11

    for (i <- 0 until array.length; j <- 0 until array(i).length) println(array(i)(j))

    println()

    for (i <- array.indices; j <- array(i).indices) {
      print(array(i)(j) + "\t")
      if (j == array(i).length - 1) println()
    }
    println()

    array.foreach(line => line.foreach(println))
    array.foreach(_.foreach(println))

  }
}
