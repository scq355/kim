package com {

  import com.kim.scala.InnerObj

  object OutObj {
    var out: String = "out"

    def main(args: Array[String]): Unit = {
      println(InnerObj.in)
    }
  }
  package kim {
    package scala {
      object InnerObj {
        val in: String = "in"

        def main(args: Array[String]): Unit = {
          println(OutObj.out)
          OutObj.out = "outer"
          println(OutObj.out)
        }
      }
    }

  }

}


package aaa {
  package bbb {

    object L01_Package {
      def main(args: Array[String]): Unit = {
        import com.kim.scala.InnerObj
        println(InnerObj.in)
      }
    }
  }

}

