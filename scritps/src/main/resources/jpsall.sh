#!/bin/bash

for host in 192.168.100.220 192.168.100.223 192.168.100.224 ; do
    echo ===================  $host  ===================
    ssh $host jps
done