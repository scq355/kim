package chapter07

object L15_HLevelFunctionReduce {
  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4)
    println(list.reduce(_ + _))
    println(list.reduceLeft(_ + _))
    println(list.reduceRight(_ + _))

    val list2 = List(3, 4, 5, 8, 10)
    println(list2.reduce(_ - _))
    println(list2.reduceLeft(_ - _))
    // (3 - (4 - (5 - (8 - 10))))
    println(list2.reduceRight(_ - _))

    // fold
    println(list.fold(10)(_ + _)) // 10 + 1 + 2 + 3 + 4
    println(list.foldLeft(10)(_ - _)) // 10 - 1 - 2 - 3 - 4
    println(list2.foldRight(11)(_ - _))  // (3 - (4 - (5 - (8 - (10 - 11)))))
  }
}
