package com.kim.flink

import org.apache.flink.table.api.{EnvironmentSettings, TableEnvironment}

object MySQL2MySQL {
  def main(args: Array[String]): Unit = {
    val settings: EnvironmentSettings = EnvironmentSettings.newInstance().inStreamingMode().build()
    val tableEnvironment: TableEnvironment = TableEnvironment.create(settings)

    tableEnvironment.executeSql(
      """
        |CREATE TABLE products (
        |    id INT,
        |    name STRING,
        |    description STRING,
        |    PRIMARY KEY (id) NOT ENFORCED
        |) WITH (
        |    'connector' = 'mysql-cdc',
        |    'hostname' = '192.168.100.156',
        |    'port' = '3306',
        |    'username' = 'root',
        |    'password' = '1acc5062',
        |    'database-name' = 'mydb',
        |    'table-name' = 'products'
        |)
        |""".stripMargin
    )


    tableEnvironment.executeSql(
      """
        |CREATE TABLE products_2 (
        |    id INT,
        |    name STRING,
        |    description STRING,
        |    PRIMARY KEY (id) NOT ENFORCED
        |) WITH (
        |    'connector' = 'jdbc',
        |    'url' = 'jdbc:mysql://192.168.100.156:3306/mydb',
        |    'username' = 'root',
        |    'password' = '1acc5062',
        |    'table-name' = 'products_2'
        |)
        |""".stripMargin
    )

    tableEnvironment.from("products").executeInsert("products_2")



  }

}
