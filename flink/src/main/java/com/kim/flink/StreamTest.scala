package com.kim.flink

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object StreamTest {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val dataStream: DataStream[Domain.RcaConfig] = environment.addSource(new MySQLSource)
    dataStream.print()
    environment.execute("StreamMySQLSource")
  }
}
