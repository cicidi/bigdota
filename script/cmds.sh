#================================
#windows;ubuntu01;ubuntu02;ubuntu03;ubuntu04;ubuntu05;ubuntu06;dse01;dse02;dse03;
#windows
ssh root@windows "cp /etc/hosts /etc/hosts_bk"
scp /etc/hosts root@windows:/etc/hosts
ssh root@windows "/usr/bin/ssh-keygen -t rsa"
ssh root@windows "ssh-copy-id root@ubuntu01"
ssh root@windows "ssh-copy-id root@ubuntu02"
ssh root@windows "ssh-copy-id root@ubuntu03"
ssh root@windows "ssh-copy-id root@ubuntu04"
ssh root@windows "ssh-copy-id root@ubuntu05"
ssh root@windows "ssh-copy-id root@ubuntu06"
ssh root@windows "ssh-copy-id root@dse01"
ssh root@windows "ssh-copy-id root@dse02"
ssh root@windows "ssh-copy-id root@dse03"
#ubuntu01
ssh root@ubuntu01 "cp /etc/hosts /etc/hosts_bk"
scp /etc/hosts root@ubuntu01:/etc/hosts
ssh root@ubuntu01 "/usr/bin/ssh-keygen -t rsa"
ssh root@ubuntu01 "ssh-copy-id root@windows"
ssh root@ubuntu01 "ssh-copy-id root@ubuntu02"
ssh root@ubuntu01 "ssh-copy-id root@ubuntu03"
ssh root@ubuntu01 "ssh-copy-id root@ubuntu04"
ssh root@ubuntu01 "ssh-copy-id root@ubuntu05"
ssh root@ubuntu01 "ssh-copy-id root@ubuntu06"
ssh root@ubuntu01 "ssh-copy-id root@dse01"
ssh root@ubuntu01 "ssh-copy-id root@dse02"
ssh root@ubuntu01 "ssh-copy-id root@dse03"
#ubuntu02
ssh root@ubuntu02 "cp /etc/hosts /etc/hosts_bk"
scp /etc/hosts root@ubuntu02:/etc/hosts
ssh root@ubuntu02 "/usr/bin/ssh-keygen -t rsa"
ssh root@ubuntu02 "ssh-copy-id root@windows"
ssh root@ubuntu02 "ssh-copy-id root@ubuntu01"
ssh root@ubuntu02 "ssh-copy-id root@ubuntu03"
ssh root@ubuntu02 "ssh-copy-id root@ubuntu04"
ssh root@ubuntu02 "ssh-copy-id root@ubuntu05"
ssh root@ubuntu02 "ssh-copy-id root@ubuntu06"
ssh root@ubuntu02 "ssh-copy-id root@dse01"
ssh root@ubuntu02 "ssh-copy-id root@dse02"
ssh root@ubuntu02 "ssh-copy-id root@dse03"
#ubuntu03
ssh root@ubuntu03 "cp /etc/hosts /etc/hosts_bk"
scp /etc/hosts root@ubuntu03:/etc/hosts
ssh root@ubuntu03 "/usr/bin/ssh-keygen -t rsa"
ssh root@ubuntu03 "ssh-copy-id root@windows"
ssh root@ubuntu03 "ssh-copy-id root@ubuntu01"
ssh root@ubuntu03 "ssh-copy-id root@ubuntu02"
ssh root@ubuntu03 "ssh-copy-id root@ubuntu04"
ssh root@ubuntu03 "ssh-copy-id root@ubuntu05"
ssh root@ubuntu03 "ssh-copy-id root@ubuntu06"
ssh root@ubuntu03 "ssh-copy-id root@dse01"
ssh root@ubuntu03 "ssh-copy-id root@dse02"
ssh root@ubuntu03 "ssh-copy-id root@dse03"
#ubuntu04
ssh root@ubuntu04 "cp /etc/hosts /etc/hosts_bk"
scp /etc/hosts root@ubuntu04:/etc/hosts
ssh root@ubuntu04 "/usr/bin/ssh-keygen -t rsa"
ssh root@ubuntu04 "ssh-copy-id root@windows"
ssh root@ubuntu04 "ssh-copy-id root@ubuntu01"
ssh root@ubuntu04 "ssh-copy-id root@ubuntu02"
ssh root@ubuntu04 "ssh-copy-id root@ubuntu03"
ssh root@ubuntu04 "ssh-copy-id root@ubuntu05"
ssh root@ubuntu04 "ssh-copy-id root@ubuntu06"
ssh root@ubuntu04 "ssh-copy-id root@dse01"
ssh root@ubuntu04 "ssh-copy-id root@dse02"
ssh root@ubuntu04 "ssh-copy-id root@dse03"
#ubuntu05
ssh root@ubuntu05 "cp /etc/hosts /etc/hosts_bk"
scp /etc/hosts root@ubuntu05:/etc/hosts
ssh root@ubuntu05 "/usr/bin/ssh-keygen -t rsa"
ssh root@ubuntu05 "ssh-copy-id root@windows"
ssh root@ubuntu05 "ssh-copy-id root@ubuntu01"
ssh root@ubuntu05 "ssh-copy-id root@ubuntu02"
ssh root@ubuntu05 "ssh-copy-id root@ubuntu03"
ssh root@ubuntu05 "ssh-copy-id root@ubuntu04"
ssh root@ubuntu05 "ssh-copy-id root@ubuntu06"
ssh root@ubuntu05 "ssh-copy-id root@dse01"
ssh root@ubuntu05 "ssh-copy-id root@dse02"
ssh root@ubuntu05 "ssh-copy-id root@dse03"
#ubuntu06
ssh root@ubuntu06 "cp /etc/hosts /etc/hosts_bk"
scp /etc/hosts root@ubuntu06:/etc/hosts
ssh root@ubuntu06 "/usr/bin/ssh-keygen -t rsa"
ssh root@ubuntu06 "ssh-copy-id root@windows"
ssh root@ubuntu06 "ssh-copy-id root@ubuntu01"
ssh root@ubuntu06 "ssh-copy-id root@ubuntu02"
ssh root@ubuntu06 "ssh-copy-id root@ubuntu03"
ssh root@ubuntu06 "ssh-copy-id root@ubuntu04"
ssh root@ubuntu06 "ssh-copy-id root@ubuntu05"
ssh root@ubuntu06 "ssh-copy-id root@dse01"
ssh root@ubuntu06 "ssh-copy-id root@dse02"
ssh root@ubuntu06 "ssh-copy-id root@dse03"
#dse01
ssh root@dse01 "cp /etc/hosts /etc/hosts_bk"
scp /etc/hosts root@dse01:/etc/hosts
ssh root@dse01 "/usr/bin/ssh-keygen -t rsa"
ssh root@dse01 "ssh-copy-id root@windows"
ssh root@dse01 "ssh-copy-id root@ubuntu01"
ssh root@dse01 "ssh-copy-id root@ubuntu02"
ssh root@dse01 "ssh-copy-id root@ubuntu03"
ssh root@dse01 "ssh-copy-id root@ubuntu04"
ssh root@dse01 "ssh-copy-id root@ubuntu05"
ssh root@dse01 "ssh-copy-id root@ubuntu06"
ssh root@dse01 "ssh-copy-id root@dse02"
ssh root@dse01 "ssh-copy-id root@dse03"
#dse02
ssh root@dse02 "cp /etc/hosts /etc/hosts_bk"
scp /etc/hosts root@dse02:/etc/hosts
ssh root@dse02 "/usr/bin/ssh-keygen -t rsa"
ssh root@dse02 "ssh-copy-id root@windows"
ssh root@dse02 "ssh-copy-id root@ubuntu01"
ssh root@dse02 "ssh-copy-id root@ubuntu02"
ssh root@dse02 "ssh-copy-id root@ubuntu03"
ssh root@dse02 "ssh-copy-id root@ubuntu04"
ssh root@dse02 "ssh-copy-id root@ubuntu05"
ssh root@dse02 "ssh-copy-id root@ubuntu06"
ssh root@dse02 "ssh-copy-id root@dse01"
ssh root@dse02 "ssh-copy-id root@dse03"
#dse03
ssh root@dse03 "cp /etc/hosts /etc/hosts_bk"
scp /etc/hosts root@dse03:/etc/hosts
ssh root@dse03 "/usr/bin/ssh-keygen -t rsa"
ssh root@dse03 "ssh-copy-id root@windows"
ssh root@dse03 "ssh-copy-id root@ubuntu01"
ssh root@dse03 "ssh-copy-id root@ubuntu02"
ssh root@dse03 "ssh-copy-id root@ubuntu03"
ssh root@dse03 "ssh-copy-id root@ubuntu04"
ssh root@dse03 "ssh-copy-id root@ubuntu05"
ssh root@dse03 "ssh-copy-id root@ubuntu06"
ssh root@dse03 "ssh-copy-id root@dse01"
ssh root@dse03 "ssh-copy-id root@dse02"
