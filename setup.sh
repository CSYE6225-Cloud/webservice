#!/bin/bash
sudo yum upgrade -y

echo "----------------INSTALL JDK----------------"
wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.rpm
sudo rpm -Uvh jdk-17_linux-x64_bin.rpm
java -version
rm jdk-17_linux-x64_bin.rpm
echo "---------------Finished JDK----------------"

echo "----------------INSTALL CodeDeploy----------------"
sudo yum install ruby -y
wget https://aws-codedeploy-us-east-1.s3.us-east-1.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto
rm install
echo "---------------Finished CodeDeploy----------------"

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
