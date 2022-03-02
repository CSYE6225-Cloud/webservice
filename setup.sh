#!/bin/bash
sudo yum upgrade -y
echo "----------------INSTALL Git----------------"
sudo yum install -y git
ssh-keyscan github.com >> ~/.ssh/known_hosts
echo "---------------Finished Git----------------"

echo "----------------INSTALL JDK & Maven----------------"
wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.rpm
sudo rpm -Uvh jdk-17_linux-x64_bin.rpm
java -version
wget https://dlcdn.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz
tar -xzvf apache-maven-3.8.4-bin.tar.gz
sudo mv apache-maven-3.8.4 /opt/
echo "export PATH=/opt/apache-maven-3.8.4/bin:\$PATH" >> .bashrc
source .bashrc
mvn -v
rm jdk-17_linux-x64_bin.rpm apache-maven-3.8.4-bin.tar.gz
echo "---------------Finished JDK & Maven----------------"

echo "----------------INSTALL MySQL----------------"
sudo wget https://dev.mysql.com/get/mysql80-community-release-el7-5.noarch.rpm
sudo yum install mysql80-community-release-el7-5.noarch.rpm -y
sudo yum install mysql-community-server -y
sudo systemctl enable mysqld
sudo systemctl start mysqld
export MYSQL_PWD=`sudo grep 'temporary password' /var/log/mysqld.log | awk '{print $NF}'`
mysql --connect-expired-password -uroot -e "ALTER USER 'root'@'localhost' IDENTIFIED BY '$DB_PWD';"
export MYSQL_PWD=$DB_PWD
mysql -uroot -e "CREATE DATABASE IF NOT EXISTS csye6225;"
mysql -uroot csye6225 < MySQLDatabase.sql 
rm mysql80-community-release-el7-5.noarch.rpm
echo "---------------Finished MySQL----------------"
