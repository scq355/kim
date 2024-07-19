package chapter07

object L13_SimpleFunction {
  def main(args: Array[String]): Unit = {
    val list = List(1,2,3,4,5)

    var sum = 0
    for(elem <- list) {
      sum += elem
    }
    println(sum)

    println(list.sum)
    // 乘积
    println(list.product)
    println(list.max)
    println(list.min)

    val list2 = List(("a", 5), ("b", 1), ("c", 8), ("d", 2), ("e", -3), ("f", 4))

    println(list2.max)
    println(list2.maxBy((tuple: (String, Int)) => tuple._2))
    println(list2.maxBy(_._2))
    println(list2.min)
    println(list2.minBy(_._2))


    println(list.sorted)
    println(list.sorted(Ordering[Int].reverse))

    println(list2.sorted)
    println(list2.sortBy(_._2))
    println(list2.sortBy(_._2)(Ordering.Int.reverse))

    println(list.sortWith((x:Int, y:Int) => {x < y} ))
    println(list.sortWith(_ < _ ))
    println(list.sortWith(_ > _ ))

  }
}
