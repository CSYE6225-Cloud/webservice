#!/bin/bash

# set application.properties
cat << EOF > src/main/resources/application.properties
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://\${MYSQL_HOST:localhost}:3306/csye6225
spring.datasource.username=root
spring.datasource.password=$MYSQL_PWD
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
logging.level.org.springframework=DEBUG
logging.pattern.file= %d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%
server.port=8080
EOF

cat << EOF > ami.pkrvars.hcl
aws_access_key="$AWS_ACCESS_KEY"
aws_secret_key="$AWS_SECRET_KEY"
aws_region="us-east-1"
source_ami="ami-033b95fb8079dc481"
ssh_username="ec2-user"
mysql_pwd="$MYSQL_PWD"
EOF
