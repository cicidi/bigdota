ssh root@ubuntu03 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/start-master.sh"
# ignore dse01  for spark because it is too slow adn will decrease whole cluster performance
ssh dse01@dse01 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7077"
#ssh dse02@dse02 "/etc/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7077"
ssh dse02@dse02 "/home/dse02/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7077"
#ssh root@windows "/mnt/d/App/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7077"
ssh cicidi@dse03 "/Volumes/WD/app/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7077"

ssh jianming@10.0.0.30 "/home/jianming/spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh spark://ubuntu03:7077"

/Volumes/WD/app/spark-2.2.0-bin-hadoop2.7/bin/spark-submit --class com.cicidi.bigdota.Dota2WinApplication --master spark://ubuntu03:7077 --driver-java-options "-Dlog4j.configuration=file:///Volumes/WD/project/bigdota/dota/src/main/resources/log4j.properties -Dspring.profiles.active=prod -Dspark.executor.memory=10g" --files /Volumes/WD/project/bigdota/dota/src/main/resources/log4j.properties --conf "spark.executor.extraJavaOptions='-Dlog4j.configuration=log4j.properties'" ~/Desktop/dota-1.0-SNAPSHOT.jar 1 1 0 0

~/spark-2.2.0-bin-hadoop2.7/bin/spark-submit --class com.cicidi.bigdota.Dota2WinApplication --master spark://ubuntu03:7077 --driver-java-options "-Dlog4j.configuration=file:///home/dse02/Downloads/log4j.properties -Dspring.profiles.active=prod -Dspark.executor.memory=10g" --files /home/dse02/Downloads/log4j.properties --conf "spark.executor.extraJavaOptions='-Dlog4j.configuration=log4j.properties'" /home/dse02/Downloads/dota-1.0-SNAPSHOT.jar 1 1 0 0