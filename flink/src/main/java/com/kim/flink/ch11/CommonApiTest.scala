package com.kim.flink.ch11

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.Expressions.$
import org.apache.flink.table.api.{EnvironmentSettings, Table, TableEnvironment}
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

object CommonApiTest {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    environment.setParallelism(1)

    val tableEnvironment: StreamTableEnvironment = StreamTableEnvironment.create(environment)


    val settings: EnvironmentSettings = EnvironmentSettings.newInstance()
      .inStreamingMode()
      .build()
    val tableEnvironment1: TableEnvironment = TableEnvironment.create(settings)


    tableEnvironment.executeSql(
      "CREATE TABLE eventTable (" +
      " uid STRING," +
      " url STRING," +
      " ts BIGINT" +
      ") WITH (" +
      " 'connector' = 'filesystem'," +
      " 'path' = 'input/clicks.txt'," +
      " 'format' = 'csv' " +
      ")")

    val resultTable: Table = tableEnvironment.sqlQuery("" +
      "select uid, url, ts from eventTable where uid = 'Alice'" +
      "")

    val urlCountTable: Table = tableEnvironment.sqlQuery("" +
      "select uid, count(url) from eventTable group by uid" +
      "")

    tableEnvironment.createTemporaryView("tempTable", resultTable)

    val eventTable: Table = tableEnvironment.from("eventTable")
    val resultTable1: Table = eventTable.where($("url").isEqual("./home"))
      .select($("url"), $("uid"), $("ts"))


    tableEnvironment.executeSql("" +
      "CREATE TABLE outputTable (" +
      " uid STRING," +
      " url STRING," +
      " ts BIGINT" +
      ") WITH (" +
      " 'connector' = 'filesystem'," +
      " 'path' = 'output'," +
      " 'format' = 'csv' " +
      ")" +
      "")

//    resultTable.executeInsert("outputTable")

    tableEnvironment.toDataStream(resultTable).print("result")
    tableEnvironment.toChangelogStream(urlCountTable).print("count")
    environment.execute()
  }
}
