package chapter07

object L10_Tuple {

  def main(args: Array[String]): Unit = {
    val tuple: (String, Int, Char, Boolean) = ("hello", 100, 'a', true)
    println(tuple)

    println(tuple._1)

    println(tuple.productElement(1))

    println("================================")
    for(elem <- tuple.productIterator) println(elem)

    val mulTuple = (12, 0.3, "abc", (23, "scala"), 33)
    println("================================")

    println(mulTuple._4._2)
  }

}
