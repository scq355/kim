package com.kim.flink

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.table.api.Table
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.apache.flink.types.Row

object FlinkSQLCDCDemo {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    environment.setParallelism(1)
    val tableEnvironment: StreamTableEnvironment = StreamTableEnvironment.create(environment)
    tableEnvironment.executeSql(
      "CREATE TABLE products (" +
        "    id INT," +
        "    name STRING," +
        "    description STRING," +
        "    PRIMARY KEY (id) NOT ENFORCED" +
        ") WITH (" +
        "'connector' = 'mysql-cdc'," +
        "'hostname' = '192.168.100.156'," +
        "'port' = '3306'," +
        "'username' = 'root'," +
        "'password' = '1acc5062'," +
        "'database-name' = 'mydb'," +
        "'table-name' = 'products')"
    )

    val table: Table = tableEnvironment.sqlQuery("select * from products")
    val retractStream: DataStream[(Boolean, Row)] = tableEnvironment.toRetractStream(table)
    retractStream.print()

    environment.execute("FlinkSQLCDC")


  }

}
