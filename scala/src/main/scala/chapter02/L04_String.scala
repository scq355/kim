package chapter02

object L04_String {
  def main(args: Array[String]): Unit = {
    val name: String = "alice"
    val age: Int = 18
    println(name + " " + age)

    println(name * 3)

    printf("%d, %s\n", age, name)

    println(s"${name}, ${age}")

    val num: Double = 2.34567
    // 格式化模板字符串
    println(f"${num}%2.2f")

    println(raw"${num}%2.2f")

    // 三引号
    val sql =s"""
      |select
      |*
      |from student
      |where name = ${name}
      |""".stripMargin
    println(sql)

  }

}
