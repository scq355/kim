package chapter07

object L06_ImmutableSet {
  def main(args: Array[String]): Unit = {
    val set1 = Set(12, 23, 34, 45, 45)
    println(set1)

    val set2 = set1 + 20
    println(set1)
    println(set2)

    // 合并
    val set3 = Set(12, 23, 34, 44)
    val set4 = set2 ++ set3
    println(set4)

    // 删除
    val set5 = set3 - 12
    println(set3)
    println(set5)
  }
}
