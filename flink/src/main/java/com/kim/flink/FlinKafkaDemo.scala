package com.kim.flink

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.api.bridge.scala.{StreamTableEnvironment, tableConversions}
import org.apache.flink.types.Row

object FlinKafkaDemo {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val settings: EnvironmentSettings = EnvironmentSettings.newInstance().inStreamingMode().build()
    val tableEnvironment: StreamTableEnvironment = StreamTableEnvironment.create(environment, settings)
    tableEnvironment.executeSql(
      "CREATE TABLE input_table (" +
        "    'user_id' BIGINT," +
        "    'product_id' BIGINT," +
        "    'status' STRING" +
        ") WITH (" +
        "    'connector' = 'kafka'," +
        "    'topic' = 'topic01'," +
        "    'properties.bootstrap.servers' = '192.168.100.108:9092'," +
        "    'properties.group.id' = 'testGroup'," +
        "    'scan.startup.mode' = 'latest-offset'," +
        "    'format' = 'json'" +
        ")"
    )

    val inputTable: Table = tableEnvironment.sqlQuery("" +
      "SELECT user_id, product_id, status" +
      "FROM input_table " +
      "WHERE status = 'success'" +
      "")

    inputTable.toRetractStream[Row].print()

    tableEnvironment.executeSql(
      "CREATE TABLE output_table (" +
        "    'user_id' BIGINT," +
        "    'product_id' BIGINT," +
        "    'status' STRING" +
        ") WITH (" +
        "    'connector' = 'kafka'," +
        "    'topic' = 'topic2'," +
        "    'properties.bootstrap.servers' = '192.168.100.108:9092'," +
        "    'properties.group.id' = 'testGroup'," +
        "    'scan.startup.mode' = 'latest-offset'," +
        "    'format' = 'json'," +
        "    'sink.partitioner' = 'round-robin'" +
        ")"
    )

    tableEnvironment.executeSql("insert into output_table select * from " + inputTable)

    environment.execute("MyJob")

  }

}
