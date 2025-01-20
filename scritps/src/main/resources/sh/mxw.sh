#!/bin/bash

MAXWELL_HOME=/opt/module/maxwell

status_maxwell() {
  result=`ps -ef |grep com.zendesk.maxwell.Maxwell |grep -v grep|wc -l`
  return $result
}

start_maxwell() {
  status_maxwell
  if [ $? -lt 1 ];then
    echo "maxwell is starting..."
    $MAXWELL_HOME/bin/maxwell --config $MAXWELL_HOME/conf/maxwell.properties --daemon
    sleep 3
    status_maxwell
    if [ $? -eq 0 ];then
      echo "maxwell start success..."
    else
      echo "maxwell start fail..."
   fi
  else
    echo "maxwell is running..."
  fi
}


stop_maxwell() {
  status_maxwell
  if [ $? -gt 0 ];then
    echo "maxwell is stopping..."
    pid=`ps -ef |grep com.zendesk.maxwell.Maxwell |grep -v grep|awk '{print $2}'`
    kill -9 $pid
    sleep 3
    status_maxwell
    if [ $? -eq 0 ];then
      echo "maxwell stop fail..."
    else
      echo "maxwell stop success..."
    fi
  fi
}


case "$1" in
  start)
    start_max
    ;;
  stop)
    stop_maxwell
    ;;
  restart)
    stop_maxwell
    sleep 3
    start_maxwell
    ;;
  *)
    echo "Usage: $0 {start|stop|restart}"
    exit 1
    ;;
esac