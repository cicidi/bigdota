ssh root@ubuntu03 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/stop-master.sh"
ssh root@dse01 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/stop-slave.sh"
ssh root@windows "/mnt/d/App/spark-2.2.0-bin-hadoop2.7/sbin/stop-slave.sh"
ssh root@dse03 "/Volumes/WD/app/spark-2.2.0-bin-hadoop2.7/sbin/stop-slave.sh"