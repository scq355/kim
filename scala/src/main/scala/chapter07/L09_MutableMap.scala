package chapter07

import scala.collection.mutable

object L09_MutableMap {
  def main(args: Array[String]): Unit = {
    // 创建
    val map1: mutable.Map[String, Int] = mutable.Map("a" -> 12, "b" -> 23, "c" -> 3)
    println(map1)
    println(map1.getClass)

    map1.put("c", 5)
    map1.put("g", 6)
    println(map1)

    map1.remove("c")
    println(map1.getOrElse("c", -1))

    map1 -= "a"
    println(map1)

    map1.update("c", 22)
    map1.update("b", 33)
    println(map1)

    val map2: Map[String, Int] = Map("aa" -> 12, "b" -> 77, "cc" -> 3)
    map1 ++= map2
    println(map1)
    println(map2)


    val map3 = map2 ++ map1
    println(map3)
  }
}
