package com.kim.flink

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object StreamWordCount {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val data: DataStream[String] = environment.socketTextStream("192.168.100.108", 9999)
    val result: DataStream[(String, Int)] = data.flatMap(_.split(" "))
      .filter((_: String).nonEmpty)
      .map(((_: String), 1))
      .keyBy((_: (String, Int))._1)
      .sum(1)
    result.print()
    environment.execute("StreamWordCount")
  }

}
