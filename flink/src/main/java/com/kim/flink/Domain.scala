package com.kim.flink

import java.util.Date

object Domain {
  case class RcaConfig(id: Int,
                       key_rca: String,
                       value_rca: String,
                       remark_rca: String,
                       create_time: Date,
                       update_time: Date)

  case class Score(name: String, course: String, score: Int)


  case class Order(user: Long, product: String, amount: Int)

  case class Event(user: String, url: String, timestamp: Long)

}
