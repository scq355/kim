package com.kim.flink

import com.kim.flink.Domain.Order
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

object FlinkSQLDemo {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val settings: EnvironmentSettings = EnvironmentSettings.newInstance()
      .inStreamingMode()
      .build()

    val tableEnvironment: StreamTableEnvironment = StreamTableEnvironment.create(environment, settings)

    val orderStreamA: DataStream[Order] = environment.fromCollection(List(
      Order(1L, "尺子", 3),
      Order(1L, "铅笔", 4),
      Order(1L, "橡皮", 2)
    ))

    val orderStreamB: DataStream[Order] = environment.fromCollection(List(
      Order(2L, "手表", 3),
      Order(2L, "笔记本", 3),
      Order(4L, "计算器", 1)
    ))

    val tableA: Table = tableEnvironment.fromDataStream(orderStreamA, $"user", $"product", $"amount")
    tableA.printSchema()

    tableEnvironment.createTemporaryView("tableB", orderStreamB, $("user"), $("product"), $("amount"))
    println("tableA默认表名：" + tableA.toString)

    val resultTable: Table = tableEnvironment.sqlQuery(
      "SELECT * FROM " + tableA + " WHERE amount > 2 " +
        "UNION ALL " +
        "SELECT * FROM tableB WHERE amount > 2"
    )

    val dataStreamResult: DataStream[Order] = tableEnvironment.toAppendStream(resultTable)
    dataStreamResult.print()
    environment.execute()
  }

}
