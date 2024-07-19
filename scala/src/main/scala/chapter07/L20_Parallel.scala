package chapter07
import scala.collection.immutable
import scala.collection.parallel.immutable.ParSeq

object L20_Parallel {
  def main(args: Array[String]): Unit = {
    val result: immutable.IndexedSeq[String] = (1 to 100).map(
      x => Thread.currentThread().getName
    )
    println(result)


    val result2: ParSeq[Long] = (1 to 100).par.map(
      x => Thread.currentThread().getId
    )
    println(result2)
  }
}
