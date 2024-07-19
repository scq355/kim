package com.kim.flink

import com.kim.flink.Domain.Score
import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.functions.co.CoMapFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object TransformTest {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

//    val listDataStream: DataStream[Int] = environment.fromCollection(List(1, 2, 3, 4, 5, 6))
//    val dataStreamFromFilter: DataStream[Int] = listDataStream.filter(_>3)
//    dataStreamFromFilter.print()

//    val dataStreamStringList: DataStream[String] = environment.fromCollection(List("hadoop hello scala", "flink hello"))
//    val dataStreamFromList: DataStream[String] = dataStreamStringList.flatMap(_.split(" "))
//    dataStreamFromList.print()

//    val listDataStream: DataStream[Int] = environment.fromCollection(List(1, 2, 3, 4, 5, 6))
//    val dataStreamFromList: DataStream[Int] = listDataStream.map((x: Int) => x + 1)
//    dataStreamFromList.print()

//    val dataStream: DataStream[Score] = environment.fromElements(
//      Score("zhang", "english", 98),
//      Score("wang", "english", 89),
//      Score("zhang", "math", 88),
//      Score("li", "math", 94)
//    )

//    val reduceDataStream: DataStream[Score] = dataStream.keyBy(_.name).reduce(new MyReduceFunction)
//    reduceDataStream.print()
//
//    val reduceDataStream2: DataStream[Score] = dataStream.keyBy(_.name).reduce((s1, s2) => {
//      Score(s1.name, "sum", s1.score + s2.score)
//    })
//    reduceDataStream2.print()

//    val dataStream1: DataStream[(Int, Int, Int)] = environment.fromElements((0, 0, 0), (1, 1, 1), (2, 2, 2))
//    val dataStream2: DataStream[(Int, Int, Int)] = environment.fromElements((3, 3, 3), (4, 4, 4), (5, 5, 5))
//    val unionDataStream: DataStream[(Int, Int, Int)] = dataStream1.union(dataStream2)
//    unionDataStream.print()

//    val dataStream1: DataStream[Int] = environment.fromElements(1, 2, 5, 3)
//    val dataStream2: DataStream[String] = environment.fromElements("a", "b", "c", "d")
//    val connectStream: ConnectedStreams[Int, String] = dataStream1.connect(dataStream2)
//    val resultDataStream: DataStream[String] = connectStream.map(new MyConMapFunction)
//    resultDataStream.print()

//    val tupleStream: DataStream[(Int, Int, Int)] = environment.fromElements(
//      (0, 0, 0), (0, 1, 1), (0, 2, 2),
//      (1, 0, 6), (1, 1, 7), (0, 2, 8)
//    )
//    val sumStreamMax: DataStream[(Int, Int, Int)] = tupleStream
//      .keyBy((_: (Int, Int, Int))._1)
//      .max(1)
//    sumStreamMax.print()
//
//    val sumStreamMaxBy: DataStream[(Int, Int, Int)] = tupleStream
//      .keyBy((_: (Int, Int, Int))._1)
//      .maxBy(1)
//    sumStreamMaxBy.print()
//
//    val sumStreamSum: DataStream[(Int, Int, Int)] = tupleStream
//      .keyBy((_: (Int, Int, Int))._1)
//      .sum(1)
//    sumStreamSum.print()
    environment.execute()
  }
}

class MyReduceFunction extends ReduceFunction[Score] {
  override def reduce(s1: Score, s2: Score): Score = {
    Score(s1.name, "sum", s1.score + s2.score)
  }
}


class MyConMapFunction extends CoMapFunction[Int, String, String] {
  override def map1(in1: Int): String = {
    in1.toString
  }

  override def map2(in2: String): String = {
    in2
  }
}


