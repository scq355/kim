package chapter04

object L02_Loop {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10) {
      println(i)
    }

    for (i: Int <- 1.to(10)) {
      println(i)
    }

    for (i <- Range(1, 10)) {
      println(i)
    }

    for (i <- 1 until 10) {
      println(i)
    }

    for (i <- Array(11, 22, 33, 44)) {
      println(i)
    }

    for (i <- List(11, 22, 33, 44)) {
      println(i)
    }

    for (i <- Set(11, 22, 33, 44)) {
      println(i)
    }

    // 循环守卫
    for (i <- 1 to 10 if i != 5) {
      println(i)
    }

    // 循环步长
    for (i <- 1 to 10 by 2) {
      println(i)
    }

    for (i <- 13 to 30 by 3) {
      println(i)
    }

    for (i <- 13 to 30 by -2) {
      println(i)
    }

    for (i <- 30 to 13 by -2) {
      println(i)
    }

    for (i <- 1 to 10 reverse) {
      println(i)
    }

    //    for (i <- 30 to 13 by 0) {
    //      println(i)
    //    }


    for (i <- 1.0 to 10.0 by 0.5) {
      println(i)
    }

    // 循环嵌套
    for (i <- 1 to 3) {
      for (j <- 1 to i) {
        println(i + " * " + j)
      }
    }
    for (i <- 1 to 3; j <- 1 to 3) {
      println(i + " * " + j)
    }

    // 引入变量
    for (i <- 1 to 10; j = 10 - i) {
      println(s"${i} + ${j} = ${i + j}")
    }

    for {i <- 1 to 10
         j = 10 - i} {
      println(s"${i} + ${j} = ${i + j}")
    }

    // 循环返回值
    val unit: Unit = for (i <- 1 to 10) {
      println(i)
      i
    }
    println(unit)

    val ints: IndexedSeq[Int] = for (i <- 1 to 10) yield i * 2
    println(ints)

  }
}
