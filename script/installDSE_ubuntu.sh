curl --user walterchen.ca@gmail.com:Ajaxxxxx -O https://downloads.datastax.com/enterprise/DataStaxEnterprise-5.1.3-linux-x64-installer.run
chmod +x DataStaxEnterprise-5.1.3-linux-x64-installer.run
./DataStaxEnterprise-5.1.3-linux-x64-installer.run --help
./DataStaxEnterprise-5.1.3-linux-x64-installer.run

#I have 3 machines for my cassandra cluster. 1 Mac 2 Ubuntus
I used defalut install be it is installed to different path in the machines

to hook up the cluster , i am following this video https://www.youtube.com/watch?v=DS4XIVYEeW8



#dse01 (hp)
#less /var/log/cassandra/output.log
#vim ubuntu /etc/dse/cassandra/cassandra.yaml
#/etc/default/dse  set datacenter
#/etc/dse/cassandra/cassandra-env.sh
#sudo vim /etc/dse/cassandra/jvm.options

#dse02(lenovo)
#less ~/dse/logs/cassandra/output.log
#~/dse/bin/start_server
#~/dse/bin/stop_server
#~/dse/bin/nodetool status


#dse03  (mac)
#config
#/Volumes/WD/app/dse/resources/cassandra/conf/cassandra.yaml
#start cmd in
#/Volumes/WD/app/dse/bin/start_server
#/Volumes/WD/app/dse/bin/stop_server
#less /Volumes/WD/app/dse/logs/cassandra/server_output.log
#/Volumes/WD/app/dse/resources/cassandra/bin/nodetool status
#/Volumes/WD/app/dse/bin/dse client-tool spark master-address


#when add new dse cassandra
#in cassandra.yaml
# change cluster_name
# change num_tokens
# seeds
#listen_address to instance ip
#rpc_address: 0.0.0.0
#broadcast_rpc_address: instance ip
# check dse config  if all feature enabled

#vim /Volumes/WD/app/dse/resources/cassandra/conf/cassandra-env.sh    CassandraDaemon.java:705 - Cannot start node if
#snitch's data center (SearchGraphAnalytics) differs from previous data center (Cassandra).
#Please fix the snitch configuration, decommission and rebootstrap this node or use the flag -Dcassandra.ignore_dc=true.

#other cmd

#ifstat check network
#nodetool removenode d0844a21-3698-4883-ab66-9e2fd5150edd

#exceptions
Exception (java.lang.RuntimeException) encountered during startup: Unable to gossip with any seeds
This is caused when seed: parameter and listen_host are different in cassandra.yaml configuration file under conf directory

resize ubuntu vm
#VBoxManage modifyhd “C:\Users\Chris\VirtualBox VMs\Windows 7\Windows 7.vdi” --resize 81920
