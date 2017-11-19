#reset Hosts when ip changed
ssh root@ubuntu01 "cp /etc/hosts /etc/hosts_bk"
ssh root@dse02"cp /etc/hosts /etc/hosts_bk"

scp /etc/hosts ubuntu01@ubuntu01:/etc/hosts
scp /etc/hosts ubuntu02@ubuntu02:/etc/hosts
scp /etc/hosts ubuntu03@ubuntu03:/etc/hosts
scp /etc/hosts ubuntu04@ubuntu04:/etc/hosts
scp /etc/hosts ubuntu05@ubuntu05:/etc/hosts
scp /etc/hosts ubuntu06@ubuntu06:/etc/hosts
scp /etc/hosts des01@dse01:/etc/hosts
scp /etc/hosts dse02@dse02:/etc/hosts
scp /etc/hosts dse03@dse03:/etc/hosts
