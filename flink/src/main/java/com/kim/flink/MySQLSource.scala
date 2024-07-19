package com.kim.flink

import com.kim.flink.Domain.RcaConfig
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}

import java.sql.{Connection, PreparedStatement, ResultSet}

class MySQLSource extends RichSourceFunction[RcaConfig]{

  var connection: Connection = _
  var preparedStatement: PreparedStatement = _
  var isRunning = true

  override def open(parameters: Configuration): Unit = {
    connection = JDBCUtils.getConnection
    preparedStatement = connection.prepareStatement("select * from t_rca_config")
  }

  override def run(sourceContext: SourceFunction.SourceContext[RcaConfig]): Unit = {
    val resultSet: ResultSet = preparedStatement.executeQuery()
    while (isRunning && resultSet.next()) {
      val rcaConfig: RcaConfig = RcaConfig(
        resultSet.getInt("id"),
        resultSet.getString("key_rca"),
        resultSet.getString("value_rca"),
        resultSet.getString("remark_rca"),
        resultSet.getDate("create_time"),
        resultSet.getDate("update_time")
      )
      sourceContext.collect(rcaConfig)
    }
  }

  override def cancel(): Unit = {
    this.isRunning = false
  }
}
