package com.kim.flink.ch05

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, createTypeInformation}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

import java.util.Properties

/**
 * 读取kafka
 */
object SourceKafkaTest {

  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    environment.setParallelism(1)

    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "192.168.100.108:9092")
    properties.setProperty("group.id", "consumer-group")

    val stream: DataStream[String] = environment.addSource(new FlinkKafkaConsumer[String]("clicks", new SimpleStringSchema(), properties))

    stream.print()

    environment.execute()
  }

}
