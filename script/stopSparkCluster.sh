ssh ubuntu03@ubuntu03 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/stop-master.sh"
#ssh dse01@dse01 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/stop-slave.sh"
#ssh dse02@dse02 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/stop-slave.sh"
ssh dse02@dse02 "/home/dse02/spark-2.2.0-bin-hadoop2.7/sbin/stop-slave.sh"
#ssh windows@windows "/mnt/d/App/spark-2.2.0-bin-hadoop2.7/sbin/stop-slave.sh"
ssh cicidi@dse03 "/Volumes/WD/app/spark-2.2.0-bin-hadoop2.7/sbin/stop-slave.sh"