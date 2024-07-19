package com.kim.flink

import java.sql.{Connection, DriverManager}

object JDBCUtils {
  private val driver = "com.mysql.jdbc.Driver"
  private val url = "jdbc:mysql://192.168.100.156:3306/jhy_rca"
  private val username = "root"
  private val password = "1acc5062"

  def getConnection(): Connection = {
    Class.forName(driver)
    val connection: Connection = DriverManager.getConnection(url, username, password)
    connection
  }

}
