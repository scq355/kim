package com.kim.flink.ch05

import com.kim.flink.Domain.Event
import org.apache.flink.streaming.api.functions.source.{ParallelSourceFunction, SourceFunction}

import java.util.Calendar
import scala.util.Random

class ClickSource extends ParallelSourceFunction[Event]{

  var running = true

  override def run(sourceContext: SourceFunction.SourceContext[Event]): Unit = {
    val random = new Random()
    val users: Array[String] = Array("Mary", "Alice", "Bob", "Cary")
    val urls: Array[String] = Array("./home", "./cart", "./prod?id=1", "./prod?id=2", "./prod?id=3")

    while(running) {
      val event: Event = Event(users(random.nextInt(users.length)), urls(random.nextInt(urls.length)), Calendar.getInstance().getTimeInMillis)
      sourceContext.collect(event)

      Thread.sleep(1000)
    }

  }

  override def cancel(): Unit = running = false
}
