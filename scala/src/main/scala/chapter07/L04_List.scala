package chapter07

object L04_List {
  def main(args: Array[String]): Unit = {
    val list = List(12, 23, 34, 45)
    println(list)
    println(list(1))

    list.foreach(println)

    val list2 = 10 +: list :+ 20
    println(list)
    println(list2)

    println("=============================")
    val list4 = list2.::(60)
    println(list4)

    val list5 = Nil.::(12)
    println(list5)

    val list6 = 32 :: Nil
    val list7 = 17 :: 28 :: 59 :: 89 :: Nil
    println(list7)

    val list8 = list6 :: list7
    println(list8)

    val list9 = list6 ::: list7
    println(list9)

    val list10 = list6 ++ list7
    println(list10)
  }
}
