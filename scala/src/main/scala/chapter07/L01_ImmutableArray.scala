package chapter07

object L01_ImmutableArray {
  def main(args: Array[String]): Unit = {
    // 创建
    val arr: Array[Int] = new Array[Int](5)

    val arr2 = Array(12, 23, 34, 45, 56)

    //访问
    println(arr(0))
    println(arr(2))
    arr(0) = 23
    arr(2) = 88
    println(arr(0))
    println(arr(2))

    // 遍历
    for (i <- 0 until arr.length) print(arr(i) + " ")
    println()

    for (i <- arr.indices) print(arr(i) + " ")
    println()

    for (elem <- arr2) print(elem + " ")
    println()

    // 迭代器
    val iterator = arr2.iterator
    while (iterator.hasNext) print(iterator.next() + " ")
    println()
    arr2.foreach((e: Int) => print(e + " "))
    println()

    arr.foreach(e => print(e + " "))
    println()

    println(arr2.mkString(","))

    // 添加元素
    val newArr = arr2.:+(66)
    println(arr2.mkString(","))
    println(newArr.mkString(","))

    val newArr2 = newArr.+:(88)
    println(newArr2.mkString(","))

    val newArr3 = newArr2 :+ 99

    val newArr4 = 55 +: 77 +: newArr3 :+ 33
    println(newArr4.mkString(","))
  }
}
