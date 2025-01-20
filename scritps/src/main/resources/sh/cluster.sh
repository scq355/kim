#!/bin/bash

case $1 in
"start") {
  echo ====================启动集群====================
  zk.sh start

  hdp.sh start

  kafka.sh start

  f1.sh start

  f2.sh start

  f3.sh start

  mxw.sh start

  echo ====================集群启动完成====================
};;

"stop") {
  echo ====================停止集群====================
  mxw.sh stop

  f3.sh stop

  f2.sh stop

  f1.sh stop

  kafka.sh stop

  hdp.sh stop

  kafka_count=$(xcall jps | grep Kafka | wc -l)
  while [ $kafka_count -gt 0 ]
  do
    sleep 1
    kafka_count=$(jpsall | grep Kafka | wc -l)
    echo "kafka进程数: $kafka_count"
  done

  zk.sh stop

  echo ====================集群停止完成====================
};;
esac