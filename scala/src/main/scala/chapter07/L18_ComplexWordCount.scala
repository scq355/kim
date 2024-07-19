package chapter07

object L18_ComplexWordCount {
  def main(args: Array[String]): Unit = {
    val tupleList: List[(String, Int)] = List(
      ("hello", 1), ("hello world", 2), ("hello scala", 3), ("hello spark from scala", 2), ("hello flink from scala", 1)
    )


    val newStringList:List[String] = tupleList.map(
      kv => {
        (kv._1.trim + " ") * kv._2
      }
    )
    println(newStringList)
    val wordCountList: List[(String, Int)] = newStringList
      .flatMap(_.split(" "))
      .groupBy(w => w)
      .map(kv => (kv._1, kv._2.length))
      .toList
      .sortBy(_._2)(Ordering.Int.reverse)
      .take(3)
    println(wordCountList)

    // 直接分割统计
    val preCountList: List[(String, Int)] = tupleList.flatMap(
      tuple => {
        val strings: Array[String] = tuple._1.split(" ")
        strings.map(w => (w, tuple._2))
      }
    )
    println(preCountList)

    val preCountMap: Map[String, List[(String, Int)]] = preCountList.groupBy(_._1)
    println(preCountMap)

    val countMap: Map[String, Int] = preCountMap.mapValues(tupleList => tupleList.map(_._2).sum)
    println(countMap)

    val countList = countMap.toList.sortWith(_._2 > _._2).take(3)
    println(countList)
  }

}
