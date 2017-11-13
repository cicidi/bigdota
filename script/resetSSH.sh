#this script is usd to reset ssh key
ssh-keygen -R ubuntu01@ubuntu01
ssh-keygen -R ubuntu02@ubuntu02
ssh-keygen -R ubuntu03@ubuntu03
ssh-keygen -R ubuntu04@ubuntu04
ssh-keygen -R ubuntu05@ubuntu05
ssh-keygen -R ubuntu06@ubuntu06
ssh-keygen -R dse01@dse01
ssh-keygen -R dse02@dse02
ssh-keygen -R dse03@dse03

ssh-copy-id ubuntu01@ubuntu01
ssh-copy-id ubuntu02@ubuntu02
ssh-copy-id ubuntu03@ubuntu03
ssh-copy-id ubuntu04@ubuntu04
ssh-copy-id ubuntu05@ubuntu05
ssh-copy-id ubuntu06@ubuntu06
ssh-copy-id dse01@dse01
ssh-copy-id dse02@dse02
ssh-copy-id cicidi@dse03
