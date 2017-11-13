#this script is usd to reset ssh key
ssh-keygen
ssh-keygen -R root@ubuntu01
ssh-keygen -R root@ubuntu02
ssh-keygen -R root@ubuntu03
ssh-keygen -R root@ubuntu04
ssh-keygen -R root@ubuntu05
ssh-keygen -R root@ubuntu06
ssh-keygen -R root@dse01
ssh-keygen -R root@dse02
ssh-keygen -R root@dse03


ssh-copy-id root@ubuntu01
ssh-copy-id root@ubuntu02
ssh-copy-id root@ubuntu03
ssh-copy-id root@ubuntu04
ssh-copy-id root@ubuntu05
ssh-copy-id root@ubuntu06
ssh-copy-id root@dse01
ssh-copy-id root@dse02
ssh-copy-id root@dse03
