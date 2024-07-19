package chapter03

object L01_Operator {
  def main(args: Array[String]): Unit = {
    val result1: Int = 10 / 3
    println(result1)

    val result2: Double = 10 / 3
    println(result2)

    val result3: Double = 10.0 / 3
    println(result3.formatted("%5.2f"))

    val result4: Int = 10 % 3
    println(result4)

    val s1: String = "a"
    val s2: String = new String("a")
    println(s1 == s2)
    println(s1.equals(s2))
    println(s1.eq(s2)) // 引用地址


    def m1(n: Int): Int = {
      println(n)
      return n
    }

    val n1 = 1000
    println((4 < 5) && m1(n1) > 0)

    def isNotEmpty(str: String): Boolean = {
      return str != null && !("".equals(str.trim))
    }

    println(isNotEmpty(null))

    var b: Byte = 10
//    b += 1
    println(b)

    val a: Int = 60
    println(a >> 3)
    println(a << 3)

    val bb: Short = -13
    println(bb << 2)
    println(bb >> 2)
    println(bb >>> 2)

    val a1: Int = 12
    val a2: Int = 23
    println(a1.+(a2))

    println(1.23.*(25))
    println(2.3 toString)
  }
}
