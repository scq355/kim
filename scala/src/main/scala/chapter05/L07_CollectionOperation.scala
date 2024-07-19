package chapter05

object L07_CollectionOperation {
  def main(args: Array[String]): Unit = {

    val arr: Array[Int] = Array(12, 23, 34, 45)

    def arrayOperation(array: Array[Int], op: Int => Int): Array[Int] = {
      for (elem <- array) yield op(elem)
    }

    def addOne(elem: Int): Int = {
      elem + 1
    }

    val newArray: Array[Int] = arrayOperation(arr, addOne)

    println(newArray.mkString(","))


    // 匿名函数操作
    println(arrayOperation(arr, _ + 1).mkString(","))
  }
}
