#!/bin/bash

sudo chmod 500 /usr/local/bin/webapp-0.0.1-SNAPSHOT.jar
sudo chown webapp:webapp /usr/local/bin/webapp-0.0.1-SNAPSHOT.jar

sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -s -c file:amazon-cloudwatch-agent.json
echo "afterInstall.sh finished"
