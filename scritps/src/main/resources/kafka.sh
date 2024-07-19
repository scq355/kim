#!/bin/bash
if [ $# -lt 1 ]; then
  echo Not Enough Arguement!
  exit
fi

case $1 in
"start")
  for host in 192.168.100.220 192.168.100.223 192.168.100.224; do
    echo "----- 启动 $host kafka -----"
    ssh $host "/data/kafka/bin/kafka-server-start.sh -daemon /data/kafka/config/server.properties"
  done
  ;;
"stop")
  for host in 192.168.100.220 192.168.100.223 192.168.100.224; do
    echo "----- 停止 $host kafka -----"
    ssh $host "/data/kafka/bin/kafka-server-stop.sh"
  done
  ;;
"*")
  echo "Input Args Error..."
  ;;
esac
