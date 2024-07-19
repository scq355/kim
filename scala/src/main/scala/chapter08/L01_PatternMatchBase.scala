package chapter08

object L01_PatternMatchBase {
  def main(args: Array[String]): Unit = {
    val x: Int = 2
    val y: String = x match {
      case 1 => "one"
      case 2 => "two"
      case 3 => "three"
      case _ => "other"
    }
    println(y)


    val a = 25
    val b = 13
    def matchDualOp(op: Char):Int = op match {
      case '+' => a + b
      case '-' => a - b
      case '*' => a * b
      case '/' => a / b
      case '%' => a % b
      case _ => -1
    }
    println(matchDualOp('+'))
    println(matchDualOp('-'))
    println(matchDualOp('*'))
    println(matchDualOp('/'))
    println(matchDualOp('\\'))

    // 模式守卫
    def abs(num: Int): Int ={
      num match {
        case i: Int if i >= 0 => i
        case i: Int if i < 0 => -i
      }
    }

    println(abs(-12))
  }

}
