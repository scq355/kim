package chapter02

object L08_TestDataTypeConvert {
  def main(args: Array[String]): Unit = {
    val a1: Byte = 10
    val b1: Long = 123L
    val result: Long = a1 + b1
    val resultInt: Int = a1 + b1.toInt

    val a2: Byte = 10
    val b2: Int = a2
    //    val c2: Byte = b2

    val a3: Byte = 10
    val b3: Char = 'b'
    val c3: Byte = b3.toByte
    println(c3)

    val a4: Byte = 12
    val b4: Short = 24
    val c4: Char = 'c'
    val result4: Int = a4 + b4
    println(result4)
    val result44: Int = a4 + b4 + c4
    println(result44)


    val n1: Int = -2.5.toInt
    println(n1)

    val n2: Int = (2.6 + 3.7).toInt
    println(n2)
    val n3: Int = 2.6.toInt + 3.7.toInt
    println(n3)

    val n: Int = 24
    val s: String =  n + ""
    println(s)

    val m: Int = "12".toInt
    val f: Float = "12.3".toFloat
    val f2: Int = "12.3".toDouble.toInt
    println(m + " " + f + " " + f2)
  }
}
