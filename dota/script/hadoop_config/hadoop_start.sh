ssh root@ubuntu04 '/itcast/zookeeper-3.4.10/bin/zkServer.sh start'
ssh root@ubuntu05 '/itcast/zookeeper-3.4.10/bin/zkServer.sh start'
ssh root@ubuntu06 '/itcast/zookeeper-3.4.10/bin/zkServer.sh start'
ssh root@ubuntu01 '/itcast/hadoop-2.8.1/sbin/hadoop-daemons.sh start journalnode'
ssh root@ubuntu01 '/itcast/hadoop-2.8.1/sbin/start-dfs.sh'
ssh root@ubuntu03 '/itcast/hadoop-2.8.1/sbin/start-yarn.sh'

