package chapter06

object L02_PackageObject {
  def main(args: Array[String]): Unit = {
    commonMethod()
    println(commonValue)
  }
}


package chapter06 {
  object L02_PackageObject {
    def main(args: Array[String]): Unit = {
      commonMethod()
      println(commonValue)
    }
  }
}


// 包对象的定义要跟包在同一层级
package ccc {
  package ddd {
    object L02_PackageObject {
      def main(args: Array[String]): Unit = {
        println(ccc.school)
      }
    }
  }
}

package object ccc {
  val school: String = "mit"
}