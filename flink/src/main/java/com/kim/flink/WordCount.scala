package com.kim.flink

import org.apache.flink.api.scala.{AggregateDataSet, DataSet, ExecutionEnvironment, GroupedDataSet}

object WordCount {

  def main(args: Array[String]): Unit = {
    val environment: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val inputDataSet: DataSet[String] = environment.readTextFile("D:\\workspaces\\mytools\\flink-study\\src\\main\\resources\\files\\words.txt")
    val wordDataSet: DataSet[String] = inputDataSet.flatMap((_: String).split(" "))
    val tupleDataSet: DataSet[(String, Int)] = wordDataSet.map((_, 1))
    val groupedDataSet: GroupedDataSet[(String, Int)] = tupleDataSet.groupBy(0)
    val resultDataSet: AggregateDataSet[(String, Int)] = groupedDataSet.sum(1)
    resultDataSet.print()
  }
}
