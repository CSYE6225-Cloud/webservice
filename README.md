# webservice

## Prerequisites for building and deploying
+ `java 17`
+ `Maven 3.8.4`

## Configuration
Do under application root directory
1. `cp ./src/main/resources/application.properties.example ./src/main/resources/application.properties`
2. config debug level if needed
3. configure your MySQL username and password (port and address)
4. configure your database name in your database
5. Create tables:
    + Create manually according to `MySQLDatabase.sql`
    + Run `MySQLDatabase.sql` in command line
      1. `mysql -u root -p`
      2. `CREATE DATABASE CSYE6225;` or use other names
      3. `exit`
      4. `mysql -u root -p CSYE6225 < MySQLDatabase.sql`
    + Run the `MySQLDatabase.sql` by other graphic software like `Navicat`

## Build and Deploy instructions
+ Compiling `mvn package`
+ Test `mvn test`
+ Package `mvn package`
+ Run `mvn spring-boot:run`

## Auto deployement

### Create AWS AMI with Packer

1. Create variables file: `cp ami.pkrvars.hcl.example ami.pkrvars.hcl`
2. Config variables in `ami.pkrvars.hcl`
3. Build with variable file: `packer build --var-file=ami.pkrvars.hcl ami.pkr.hcl`

### Launch AWS EC2 instance in VPC

Launch instances:
``` bash
aws ec2 run-instances \
    --image-id ami-0817a44ad17e15c83 \
    --instance-type t2.micro \
    --key-name aws \
    --security-group-ids sg-0103a543c930443a4 \
    --subnet-id subnet-07d01ffeac4a8d42f \
    --disable-api-termination \
    --associate-public-ip-address \
    --block-device-mappings file://mapping.json
```

Stop instances:
``` bash
aws ec2 stop-instances --instance-ids i-09841420b08f824ac
```
demo
