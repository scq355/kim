#!/bin/bash
if [ $# -lt 1 ]; then
  echo Not Enough Arguement!
  exit
fi
case $1 in
"start")
  {
    for host in 192.168.100.220 192.168.100.223 192.168.100.224; do
      echo ----------------- zookeeper $host 启动 -----------------
      ssh $host "/data/zookeeper-3.5.7/bin/zkServer.sh start"
    done
  }
  ;;
"stop")
  {
    for host in 192.168.100.220 192.168.100.223 192.168.100.224; do
      echo ----------------- zookeeper $host 停止 -----------------
      ssh $host "/data/zookeeper-3.5.7/bin/zkServer.sh stop"
    done
  }
  ;;
"status")
  {
    for host in 192.168.100.220 192.168.100.223 192.168.100.224; do
      echo ----------------- zookeeper $host 状态 -----------------
      ssh $host "/data/zookeeper-3.5.7/bin/zkServer.sh status"
    done
  }
  ;;
"*")
  echo "Input Args Error..."
  ;;
esac
