#!/bin/sh
# still has problem with this scrpit  does not work. has to run manually
#reset Hosts when ip changed
echo "#"================================
bk_cmd="cp /etc/hosts /etc/hosts_bk"
key_generate_cmd="/usr/bin/ssh-keygen -t rsa"
prefix="localhost;"
IN=$(echo | awk -F " " '{printf $2";"}' /etc/hosts)
emp=${IN#${prefix}}
echo "#"${emp}
ips=$(echo ${emp} | tr ";" "\n")
for ip in ${ips}
do
        # backup hosts file first
#        echo "&&&&&&&&&&&&&"
        echo "#"${ip}
        ssh_bk_cmd=$(echo "ssh root@"${ip} \"${bk_cmd}\")
        echo ${ssh_bk_cmd}
#        ${ssh_bk_cmd}
        #${ssh_bk_cmd}
        #ssh root@${target} \"${bk_cmd}\"
        #scp hosts file to all hosts
        scp_cmd=$(echo "scp /etc/hosts root@"${ip}":/etc/hosts")
        echo ${scp_cmd}
#        ${scp_cmd}
        #${scp_cmd}
        #generate key on #{ip}
        ssh_key_gen_cmd=$(echo "ssh root@"${ip} "\"${key_generate_cmd}\"")
        echo ${ssh_key_gen_cmd}
#        ${ssh_key_gen_cmd}
    for target in ${ips}
    do

        if [ "$ip" != "$target" ]; then
#        echo @@@@@@
        # remove {ip} host name and key from target machine
        # send #{ip} host name and key to target machine
        ssh_copy_id_cmd=$(echo "ssh-copy-id root@"${target})
#        echo ${ssh_copy_id_cmd}

        remote_ssh_copy_id_cmd=$(echo "ssh root@"${ip} "\"${ssh_copy_id_cmd}\"")
        echo ${remote_ssh_copy_id_cmd}
#        ${remote_ssh_copy_id_cmd}
        fi
    done
#    echo "============"
done
