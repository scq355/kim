hadoop fs -ls /origin_data/gmall/db |grep _inc |awk '{print $8}'|xargs hadoop fs -rm -r -f