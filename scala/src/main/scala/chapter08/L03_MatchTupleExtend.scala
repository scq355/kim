package chapter08

object L03_MatchTupleExtend {
  def main(args: Array[String]): Unit = {
    val (x, y) = (10, "hello")
    println(s"$x, $y")

    val List(first, second, _*) = List(23, 34, 45, 56)
    println(s"$first, $second")

    val fir :: sec :: rest = List(34, 34, 45, 56)
    println(s"$fir, $sec, $rest")

    // for推导式模式匹配
    val list: List[(String, Int)] = List(("a", 12), ("b", 34), ("a", 45))
    for (elem <- list) {
      println(elem._1 + " " + elem._2)
    }

    for ((word, count) <- list) {
      println(word + "->" + count)
    }

    for((word, _) <- list) {
      println(word)
    }

    for(("a", count) <- list) {
      println(count)
    }
  }
}
