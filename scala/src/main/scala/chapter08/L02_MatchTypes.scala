package chapter08

object L02_MatchTypes {
  def main(args: Array[String]): Unit = {
    def describeConst(x: Any): String = x match {
      case 1 => "one"
      case "hello" => "string"
      case true => "boolean"
      case '+' => "char"
      case _ => ""
    }
    println(describeConst(true))
    println(describeConst(0.3))


    // 匹配类型
    def describeType(x: Any): String = x match {
      case i: Int => "int"
      case s: String => "string"
      case list: List[String] => "list[string]"
      case array: Array[Int] => "array[int]"
      case other => "some else type"
    }

    println(describeType("hello"))
    println(describeType(List("scq")))
    // 泛型擦除
    println(describeType(List(2)))
    println(describeType(Array("2")))

    println("==============================")

    // 匹配数组
    for(arr <- List(Array(0), Array(1, 0), Array(0, 1, 0), Array(1, 1, 0), Array("hello", 2, 4))) {
      val result = arr match {
        case Array(0) => "0"
        case Array(1, 0) => "Array(1, 0)"
        case Array(x, y) => s"Array($x, $y)"
        case Array(0, _*) => "以0开头的数组"
        case Array(x, 1, z) => "中间为1的三元素数组"
        case _ => "some else"
      }
      println(result)
    }

    println("==============================")

    // 匹配列表
    for (list <- List(List(0), List(1, 0), List(0, 1, 0), List(1, 1, 0), List("hello"))) {
      val result = list match {
        case List(0) => "0"
        case List(1, 0) => "List(1, 0)"
        case List(x, y) => s"List($x, $y)"
        case List(0, _*) => "以0开头的List"
        case List(x, 1, z) => "中间为1的三元素List"
        case _ => "some else"
      }
      println(result)
    }


    val list = List(1, 2, 3, 4)

    list match {
      case first :: second :: rest => println(s"$first, $second, $rest")
      case _ => println("some else")
    }

    // 匹配元组
    println("==============================")
    for (tuple <- List((0, 1), (0, 0), (0, 1, 0), (0, 1, 1), (1, 11, 35), ("hello", true, 1))) {
      val result = tuple match {
        case (a, b) => s"$a, $b"
        case (0, _) => "第一个元素是0的元组"
        case (a, 1, _) => "a, 1, *"
        case _ => "some else"
      }
      println(result)
    }

  }



}
