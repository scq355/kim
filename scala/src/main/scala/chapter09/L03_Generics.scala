package chapter09

object L03_Generics {

  def main(args: Array[String]): Unit = {
    // 协变 逆变
    val child:Parent = new Child
    val childList: MyCollection[Parent] = new MyCollection[Child]
    val oneList: OneCollection[SubChild] = new OneCollection[Child]

    // 上下限
    def test[A <: Child](a: A): Unit = {
      println(a.getClass.getName)
    }

    test[Child](new Child)
    test[Child](new SubChild)
  }

}

class Parent{}

class Child extends Parent {}

class SubChild extends Child {}

class MyCollection[+E] {}

class OneCollection[-E] {}


