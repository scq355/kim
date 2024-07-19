package com.kim.flink.ch05

import com.kim.flink.Domain
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, createTypeInformation}

/**
 * 自定义数据源
 */
object SourceCustomTest {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    environment.setParallelism(1)

    val stream: DataStream[Domain.Event] = environment.addSource(new ClickSource).setParallelism(2)

    stream.print()
    environment.execute()
  }

}
