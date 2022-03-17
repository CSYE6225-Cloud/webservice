#!/bin/bash
sudo yum upgrade -y

echo "----------------INSTALL JDK----------------"
wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.rpm
sudo rpm -Uvh jdk-17_linux-x64_bin.rpm
java -version
wget https://dlcdn.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz
rm jdk-17_linux-x64_bin.rpm apache-maven-3.8.4-bin.tar.gz
echo "---------------Finished JDK----------------"

echo "----------------Config----------------"
sudo chmod 755 webapp.service
sudo chown root:root webapp.service
sudo mv webapp.service /etc/systemd/system

sudo mkdir /var/log/webapp
sudo cat << EOF > /etc/rsyslog.d/webapp.conf
if \$programname == 'webapp' then /var/log/webapp/webapp.log
& stop
EOF

sudo groupadd webapp && sudo useradd -g webapp webapp
sudo chown webapp:webapp webapp-0.0.1-SNAPSHOT.jar
sudo chmod 500 webapp-0.0.1-SNAPSHOT.jar
sudo mv webapp-0.0.1-SNAPSHOT.jar /usr/local/bin/

sudo chmod 500 schema.sql
sudo chown webapp:webapp schema.sql
sudo mv schema.sql /usr/local/etc/

echo "----------------Config Finished----------------"
