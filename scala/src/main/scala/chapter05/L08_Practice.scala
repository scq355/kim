package chapter05

object L08_Practice {
  def main(args: Array[String]): Unit = {
    val fun = (i: Int, s: String, c: Char) => {
      if (i == 0 && s.equals("") && c == '0') false else true
    }
    println(fun(0, "", '0'))
    println(fun(0, "", 'D'))

    def func(i: Int): String => (Char => Boolean) = {
      def f1(s: String): Char => Boolean = {
        def f2(c: Char): Boolean = {
          if (i == 0 && s.equals("") && c == '0') false else true
        }
        f2
      }
      f1
    }

    println(func(0)("")('0'))
    println(func(0)("")('C'))


    def func1(i: Int): String => (Char => Boolean) = {
      s => c => if (i == 0 && s.equals("") && c == '0') false else true
    }

    println(func1(0)("")('0'))
    println(func1(0)("")('C'))


    def func2(i: Int)(s: String)(c: Char): Boolean = {
      if (i == 0 && s.equals("") && c == '0') false else true
    }

    println(func2(0)("")('0'))
    println(func2(0)("")('C'))
  }
}
