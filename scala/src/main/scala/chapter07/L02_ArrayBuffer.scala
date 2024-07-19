package chapter07

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

object L02_ArrayBuffer {
  def main(args: Array[String]): Unit = {
    // 可变数组
    val arr1: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    val arr2 = ArrayBuffer(12, 23, 34)

    println(arr1.mkString(","))
    println(arr2)

    // 访问元素
    //    println(arr1(0))
    println(arr2(1))
    arr2(1) = 22
    println(arr2(1))

    // 添加元素
    // 不可变添加方式
    val newArr1 = arr1 :+ 15
    println(arr1)
    println(newArr1)
    println(arr1 == newArr1)

    // 可变添加方式
    val newArr2 = arr1 += 19
    println(arr1)
    println(newArr2)
    println(arr1 == newArr2)
    newArr2 += 99
    println(arr1)

    33 +=: arr1
    println(arr1)

    arr1.append(44)
    arr1.prepend(22)
    arr1.insert(1, 55, 66, 77, 88)
    arr1.insertAll(1, newArr1)
    arr1.appendAll(newArr2)
    println(arr1)

    // 删除
    arr1.remove(3)
    println(arr1)
    arr1.remove(0, 10)
    println(arr1)

    arr1 -= 33
    println(arr1)

    // 可变 -》不可变

    val arr = ArrayBuffer(12, 23, 34)
    val newArr: Array[Int] = arr.toArray
    println(newArr.mkString(","))
    println(arr)

    // 不可变 -》可变
    val buffer: mutable.Buffer[Int] = newArr.toBuffer
    println(buffer)
    println(newArr)

  }

}
