package chapter07

import scala.collection.mutable

object L07_MutableSet {
  def main(args: Array[String]): Unit = {
    val set1 = mutable.Set(12, 23, 34, 34, 44)
    println(set1)

    // 不可变添加操作
    val set2 = set1 + 11
    println(set1)
    println(set2)

    // 可变添加
    set1 += 11
    println(set1)

    val flag1 = set1.add(10)
    println(set1)
    println(flag1)

    val flag2 = set1.add(10)
    println(set1)
    println(flag2)

    // 删除
    set1 -= 10
    val flag3 = set1.remove(12)
    println(set1)
    println(flag3)
    val flag4 = set1.remove(12)
    println(set1)
    println(flag4)

    // 合并
    val set3 = mutable.Set(12, 23, 34, 88)
    println(set1)
    println(set3)

    val set4 = set1 ++ set3
    println(set1)
    println(set3)
    println(set4)

    set1 ++= set3
    println(set1)
    println(set3)

  }
}
