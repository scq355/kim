package chapter07

object L08_ImmutableMap {
  def main(args: Array[String]): Unit = {
    // 创建
    val map1: Map[String, Int] = Map("a" -> 12, "b" -> 23, "c" -> 3)
    println(map1)
    println(map1.getClass)

    // 遍历
    map1.foreach(println)
    map1.foreach((kv: (String, Int)) => println(kv))

    // 取map中所有key或value
    for (key <- map1.keys) {
      println(s"$key ----> ${map1(key)}")
    }

    println(map1.get("a").get)
    println(map1.get("c").get)
    println(map1.getOrElse("e", 0))

    println(map1("a"))
  }

}
