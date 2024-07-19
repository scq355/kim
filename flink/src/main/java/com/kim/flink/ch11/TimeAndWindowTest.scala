package com.kim.flink.ch11

import com.kim.flink.Domain.Event
import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, createTypeInformation}
import org.apache.flink.table.api.Expressions.$
import org.apache.flink.table.api.Table
import org.apache.flink.table.api.bridge.scala.{StreamTableEnvironment, tableConversions, tableToChangelogDataStream}

import java.time.Duration

object TimeAndWindowTest {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    environment.setParallelism(1)

    val tableEnvironment: StreamTableEnvironment = StreamTableEnvironment.create(environment)


    tableEnvironment.executeSql("" +
      "CREATE TABLE eventTable (" +
      " uid STRING," +
      " url STRING," +
      " ts BIGINT," +
      " et AS TO_TIMESTAMP(FROM_UNIXTIME(ts/1000)), " +
      " WATERMARK FOR et as et - INTERVAL '2' SECOND" +
      ") WITH (" +
      " 'connector' = 'filesystem'," +
      " 'path' = 'input/clicks.txt'," +
      " 'format' = 'csv' " +
      ")")


    val stream: DataStream[Event] = environment.fromElements(
      Event("Mary", "./home", 1000L),
      Event("Bob", "./cart", 2000L),
      Event("Alice", "./prod?id=1", 90000L),
      Event("Cary", "./prod?id=3",950000L),
      Event("Mary", "./home", 100000L)
    ).assignTimestampsAndWatermarks(WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(2))
    .withTimestampAssigner(new SerializableTimestampAssigner[Event] {
      override def extractTimestamp(t: Event, l: Long): Long = {
        t.timestamp
      }
    }))

    // 转换成表
    val table: Table = tableEnvironment.fromDataStream(stream,
      $("url"),
      $("user").as("uid"),
      $("timestamp").as("ts"),
    $("et").rowtime())

//    val table: Table = tableEnvironment.fromDataStream(stream,
//      $("url"),
//      $("user").as("uid"),
//      $("timestamp").rowtime().as("ts"))

    tableEnvironment.from("eventTable").printSchema()
    table.printSchema()

    tableEnvironment.createTemporaryView("eventTable", table)
    val resultTable: Table = tableEnvironment.sqlQuery(
      """
        |SELECT
        | uid, window_end AS endT, COUNT(url) AS cnt
        | FROM TABLE(
        | CUMULATE(
        |   TABLE  eventTable,
        |   DESCRIPTOR(et),
        |   INTERVAL '30' MINUTE,
        |   INTERVAL '1' HOUR
        | )
        | )
        | GROUP BY uid, window_start, window_end
        |""".stripMargin
    )
    tableEnvironment.toDataStream(resultTable).print()


    val overResultTable: Table = tableEnvironment.sqlQuery(
      """
        |SELECT
        | uid, url, ts, AVG(ts) OVER (
        |  PARTITION BY uid
        |  ORDER BY et
        |  ROWS BETWEEN 1 PRECEDING AND CURRENT ROW
        | ) AS avg_ts
        |FROM eventTable
        |""".stripMargin
    )

    tableEnvironment.toDataStream(overResultTable).print("row")

    environment.execute()

  }
}
