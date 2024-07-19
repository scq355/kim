package com.kim.flink.ch05

import com.kim.flink.Domain.Event
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, createTypeInformation}

object SourceBoundedTest {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    environment.setParallelism(1)
    val stream: DataStream[Int] = environment.fromElements(1, 2, 3, 4, 5)

    val dataStream1: DataStream[Event] = environment.fromElements(
      Event("Mary", "./home", 1000L),
      Event("Bob", "./cart", 2000L)
    )

    val clicks = List(
      Event("Mary", "./home", 1000L),
      Event("Bob", "./cart", 2000L)
    )
    val dataStream2: DataStream[Event] = environment.fromCollection(clicks)


    val dataStream3: DataStream[String] = environment.readTextFile("input/clicks.txt")

    stream.print("number")
    dataStream1.print("1")
    dataStream2.print("2")
    dataStream3.print("3")
    environment.execute()
  }

}



