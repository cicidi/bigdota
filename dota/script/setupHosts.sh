#reset Hosts when ip changed
bk_cmd="cp /etc/hosts /etc/hosts_bk"
IN="dse01;dse02"
ips=$(echo ${IN} | tr ";" "\n")
for ip in ${ips}
do
    for target in ${ips}
    do
        scp_cmd="scp /etc/hosts root@"${target}:/etc/hosts
        echo ${scp_cmd}
        ssh root@{target} \"${bk_cmd}\"
#        ssh root@ip \"${scp_cmd}\"
    done
done
#scp /etc/hosts ubuntu01@ubuntu01:/etc/hosts
#scp /etc/hosts ubuntu02@ubuntu02:/etc/hosts
#scp /etc/hosts ubuntu03@ubuntu03:/etc/hosts
#scp /etc/hosts ubuntu04@ubuntu04:/etc/hosts
#scp /etc/hosts ubuntu05@ubuntu05:/etc/hosts
#scp /etc/hosts ubuntu06@ubuntu06:/etc/hosts
#scp /etc/hosts des01@dse01:/etc/hosts
#scp /etc/hosts dse02@dse02:/etc/hosts
#scp /etc/hosts dse03@dse03:/etc/hosts