package chapter07

object L17_CommonWordCount {
  def main(args: Array[String]): Unit = {
    val stringList: List[String] = List(
      "hello", "hello world", "hello scala", "hello spark from scala", "hello flink from scala"
    )

    //    val wordList1: List[Array[String]] = stringList.map(_.split(" "))
    //    val wordList2: List[String] = wordList1.flatten
    //    println(wordList2)

    val wordList = stringList.flatMap(_.split(" "))
    println(wordList)

    val wordMap = wordList.groupBy(w => w)
    println(wordMap)

    val countMap = wordMap.map(kv => (kv._1, kv._2.length))
    println(countMap)

    val sortedList = countMap.toList.sortWith(_._2 > _._2).take(3)
    println(sortedList)

  }

}
