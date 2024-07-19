package chapter07


import scala.collection.mutable.ListBuffer

object L05_ListBuffer {
  def main(args: Array[String]): Unit = {
    // 可变列表
    val list1: ListBuffer[Int] = new ListBuffer[Int]()
    val list2 = ListBuffer(12, 23, 34)
    println(list1)
    println(list2)

    // 添加
    list1.append(23, 32)
    list2.append(99)

    list1.insert(1, 33, 44)
    println(list1)
    println(list2)
    println("============================")

    11 +=: 98 +=: list1 += 22 += 55 += 66
    println(list1)
    println("============================")

    //合并
    val list3 = list1 ++ list2
    println(list1)
    println(list2)
    println(list3)
    println("============================")
    list1 ++= list2
    println(list1)
    println(list2)

    println("============================")
    list1 ++=: list2
    println(list1)
    println(list2)

    // 修改
    list2(3) = 33
    list2.update(0, 0)
    println(list2)

    // 删除
    list2.remove(9)
    list2 -= 55
    println(list2)
  }
}
