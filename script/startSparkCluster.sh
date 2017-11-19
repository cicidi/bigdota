ssh root@ubuntu03 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/start-master.sh"
ssh root@dse01 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7078"
ssh root@windows "/mnt/d/App/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7078"
ssh root@dse03 "/Volumes/WD/app/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7078"