ssh root@ubuntu03 '/itcast/hadoop-2.8.1/sbin/stop-yarn.sh'
ssh root@ubuntu01 '/itcast/hadoop-2.8.1/sbin/stop-dfs.sh'
ssh root@ubuntu01 '/itcast/hadoop-2.8.1/sbin/hadoop-daemons.sh stop journalnode'
ssh root@ubuntu04 '/itcast/zookeeper-3.4.10/bin/zkServer.sh stop'
ssh root@ubuntu05 '/itcast/zookeeper-3.4.10/bin/zkServer.sh stop'
ssh root@ubuntu06 '/itcast/zookeeper-3.4.10/bin/zkServer.sh stop'

