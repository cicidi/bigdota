ssh root@ubuntu03 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/start-master.sh"
# ignore dse01  for spark because it is too slow adn will decrease whole cluster performance
#ssh root@dse01 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7077"
ssh root@dse02 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7077"
#ssh root@windows "/mnt/d/App/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7077"
ssh cicidi@dse03 "/Volumes/WD/app/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7077"