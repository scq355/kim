#!/bin/bash

# 可以将脚本复制到/bin目录中，以便全局调用
# sudo cp xsync /bin/
# 同步环境变量配置
# sudo ./bin/xsync /etc/profile.d/my_env.sh
# 让环境变量生效
# source /etc/profile

# 判断参数个数
if [ $# -lt 1 ]; then
  echo Not Enough Arguement!
  exit
fi

# 遍历集群所有机器
for host in 192.168.100.220 192.168.100.223 192.168.100.224; do
  echo =================== $host ===================
  # 遍历所有目录，挨个发送
  for file in $@; do
    #  判断文件是否存在
    if [ -e $file ]; then
      # 获取父目录
      pdir=$(
        cd -P $(dirname $file)
        pwd
      )
      # 获取当前文件名称
      fname=$(basename $file)
      ssh $host "mkdir -p $pdir"
      rsync -av $pdir/$fname $host:$pdir
    else
      echo $file does not exit!
    fi
  done
done
