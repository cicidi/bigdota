sudo mkdir -p /usr/lib/jvm
sudo tar zxvf /tmp/jdk-8u144-linux-x64.tar.gz -C /usr/lib/jvm/
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.8.0_144/bin/java" 1
sudo update-alternatives --config java