package chapter08

object L06_PartialFunction {
  def main(args: Array[String]): Unit = {
    val list: List[(String, Int)] = List(("a", 12), ("b", 34), ("a", 45))

    val newList = list.map(tuple => (tuple._1, tuple._2 * 2))

    val newList2 =list.map(tuple => {
      tuple match {
        case (w, c) => (w, c * 2)
      }
    })

    val newList3 = list.map{
        case (w, c) => (w, c * 2)
    }

    println(newList3)
    println(newList2)
    println(newList)

    // 偏函数应用：求绝对值
    val positiveAbs: PartialFunction[Int, Int] ={
      case x if x > 0 => x
    }

    val negativeAbs: PartialFunction[Int, Int] ={
      case x if x < 0 => -x
    }

    val zeroAbs: PartialFunction[Int, Int] ={
      case 0 => 0
    }

    def abs(x: Int): Int = (positiveAbs orElse negativeAbs orElse zeroAbs) (x)

    println(abs(-99))
  }

}
