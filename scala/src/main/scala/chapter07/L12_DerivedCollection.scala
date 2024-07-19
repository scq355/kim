package chapter07

object L12_DerivedCollection {

  def main(args: Array[String]): Unit = {
    val list1 = List(1, 2, 3, 4, 5)

    val list2 = List(1, 2, 33, 44, 55)

    // 头元素
    println(list1.head)
    // 掐头之后的部分
    println(list2.tail)
    // 最后一个
    println(list1.last)
    // 掐尾之后的部分
    println(list1.init)

    println(list1.reverse)

    println(list1.take(3))

    println(list1.takeRight(3))

    println(list1.drop(2))

    println(list1.dropRight(2))

    val union = list1.union(list2)
    println(union)
    println(list1:::list2)

    val set1 = Set(1, 2, 3, 4, 5)

    val set2 = Set(1, 2, 33, 44, 55)
    println(set1.union(set2))
    println(set1 ++ set2)

    val intersection = list1.intersect(list2)
    println(intersection)

    val diff1 = list1.diff(list2)
    println(diff1)
    val diff2 = list2.diff(list1)
    println(diff2)

    // 拉链
    val zip1 = list1.zip(list2)
    val zip2 = list2.zip(list1)
    println(zip1)
    println(zip2)

    // 滑动窗口
    for (elem <- list1.sliding(3)) println(elem)
  }

}
