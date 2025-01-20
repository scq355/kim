#!/bin/bash

if [ $# -lt 1 ]; then
  echo "No Args Input..."
fi

case $1 in
"start")
  echo "=========================== 启动 Hadoop集群 ==========================="
  echo "---------------------------    启动 HDFS    ---------------------------"
  ssh 192.168.100.223 "/opt/module/hadoop-3.1.3/sbin/start-dfs.sh"
  echo "---------------------------    启动 YARN    ---------------------------"
  ssh 192.168.100.224 "/opt/module/hadoop-3.1.3/sbin/start-yarn.sh"
  echo "------------------------- 启动 HistoryServer -------------------------"
  ssh 192.168.100.220 "/opt/module/hadoop-3.1.3/bin/mapred --daemon start historyserver"
  ;;
"stop")
  echo "=========================== 关闭 Hadoop集群 ==========================="
  echo "------------------------ 关闭 HistoryServer -------------------------"
  ssh 192.168.100.220 "/opt/module/hadoop-3.1.3/bin/mapred --daemon stop historyserver"
  echo "---------------------------   关闭 YARN   ---------------------------"
  ssh 192.168.100.224 "/opt/module/hadoop-3.1.3/sbin/stop-yarn.sh"
  echo "---------------------------   关闭 HDFS   ---------------------------"
  ssh 192.168.100.223 "/opt/module/hadoop-3.1.3/sbin/stop-dfs.sh"
  ;;
"*")
  echo "Input Args Error..."
  ;;
esac
