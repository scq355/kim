package chapter07

object L11_CommonOp {

  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, 5)
    println(list.length)

    val set = Set(1, 2, 3, 4, 5, 5)
    println(set.size)

    for (elem <- list) print(elem + " ")
    println()

    set.foreach(e => print(e + " "))
    println()

    for(elem <- list.iterator) print(elem + " ")

    println()

    println(list.mkString(", "))


    println(list.contains(23))
    println(set.contains(5))


  }
}
