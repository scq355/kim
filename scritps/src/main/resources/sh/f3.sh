#!/bin/bash

FLUME_HOME=/opt/module/flume-1.11

case $1 in
"start")
  echo "--------------启动hadoop101业务数据flume--------------"
    ssh hadoop101 "$FLUME_HOME/bin/flume-ng agent -n a1 -c $FLUME_HOME/conf -f $FLUME_HOME/job/kafka_to_hdfs_db.conf -Dflume.root.logger=INFO,LOGFILE >/opt/module/flume-1.11/log/flume3.log 2>&1 &"
  ;;
"stop")
  echo "--------------停止hadoop101业务数据flume--------------"
  ssh hadoop101 "ps -ef | grep kafka_to_hdfs_db | grep -v grep | awk '{print \$2}' | xargs -n1 kill"
  ;;
esac



