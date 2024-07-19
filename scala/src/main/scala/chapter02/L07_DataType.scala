package chapter02

import chapter01.Student

object L07_DataType {
  def main(args: Array[String]): Unit = {
    val a1: Byte = 127
    val a2: Byte = -128

    //    val a22: Byte = 128
    val a3 = 12
    val a4: Long = 234123353415314652L

    val b1: Byte = 10
    val b2: Byte = (10 + 20)
    println(b2)

    val b3: Byte = (b1 + 20).toByte
    println(b3)

    val f1: Float = 1.234f
    val d1 = 1.234

    val c1: Char = 'a'
    println(c1)

    val c2: Char = '9'
    println(c2)

    val c3: Char = '\t'
    val c4: Char = '\n'

    println("abc" + c3 + "def")
    println("abc" + c4 + "def")

    val c5 = '\\'
    val c6 = '\"'
    println("abc" + c5 + "def")
    println("abc" + c6 + "def")

    val i1: Int = c1
    println(i1)
    val i2: Int = c2
    println(i2)

    val c7: Char = (i1 + 1).toChar
    println(c7)
    val c8: Char = (i2 + 1).toChar
    println(c8)

    val isTrue: Boolean = true
    println(isTrue)

    def m1(): Unit = {
      println("m1")
    }
    val a = m1()
    println("a=" + a)

    // 值类型不能接收null
    //    val n: Int = null

    var student: Student = new Student("alice", 12)
    student = null
    println(student)

    def m2(n: Int): Int = {
      if (n == 0) {
        throw new NullPointerException()
      } else {
        return n
      }
    }

    val b = m2(10)
    println(b)


  }
}
