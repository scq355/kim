package com.kim.flink.ch11

import com.kim.flink.Domain.Event
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, createTypeInformation}
import org.apache.flink.table.api.Expressions.$
import org.apache.flink.table.api.Table
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.study.flink.Domain.Event

object SimpleTableExample {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    environment.setParallelism(1)

    val stream: DataStream[Event] = environment.fromElements(
      Event("Mary", "./home", 1000L),
      Event("Bob", "./cart", 2000L),
      Event("Alice", "./prod?id=1", 3000L),
      Event("Cary", "./prod?id=3", 4000L)
    )

    val tableEnvironment: StreamTableEnvironment = StreamTableEnvironment.create(environment)
    // 转换成表
    val table: Table = tableEnvironment.fromDataStream(stream)

    val resultTable: Table = table.select($("user"), $("url"))
      .where($("user").isEqual("Alice"))

    val resultSqlTable: Table = tableEnvironment.sqlQuery("select url, user from " + table + " where user = 'Bob'")

    tableEnvironment.toDataStream(resultTable).print("1")
    tableEnvironment.toDataStream(resultSqlTable).print("2")

    environment.execute()

  }
}
