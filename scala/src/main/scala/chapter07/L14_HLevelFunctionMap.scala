package chapter07

object L14_HLevelFunctionMap {
  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9)

    println(list.filter((e: Int) => e % 2 == 0))
    println(list.filter(_ % 2 == 1))

    println(list.map((e:Int) => e * 2))
    println(list.map(_*2))

    val nestedList:List[List[Int]] = List(List(1,2,3), List(4,5), List(6,7,8,9))

    val flatList = nestedList(0) ::: nestedList(1) ::: nestedList(2)
    println(flatList)
    val flatList2 = nestedList.flatten
    println(flatList2)

    val strings: List[String] = List("hello world", "hello scala", "hello java")
    val splitList: List[Array[String]] = strings.map(_.split(" "))
    val flattenList = splitList.flatten
    println(flattenList)

    val flatmapList = strings.flatMap(_.split(" "))
    println(flatmapList)

    val groupMap: Map[Int, List[Int]]= list.groupBy(_ % 2)
    val groupMap2: Map[String, List[Int]] = list.groupBy(data => if(data % 2 == 0) "偶数" else "奇数")
    println(groupMap)
    println(groupMap2)

    // 按照单词首字母分组
    val wordList:List[String] = List("china", "america", "france", "russia", "australia", "england")
    println(wordList.groupBy(_.charAt(0)))
  }
}
