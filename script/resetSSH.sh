#this script is usd to reset ssh key
ssh-keygen -R ubuntu01
ssh-keygen -R ubuntu02         
ssh-keygen -R ubuntu03       
ssh-keygen -R ubuntu04         
ssh-keygen -R ubuntu05         
ssh-keygen -R ubuntu06

ssh-copy-id ubuntu01         
ssh-copy-id ubuntu02         
ssh-copy-id ubuntu03         
ssh-copy-id ubuntu04         
ssh-copy-id ubuntu05         
ssh-copy-id ubuntu06         
