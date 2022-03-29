#!/bin/bash

sudo systemctl stop webapp
sudo rm /usr/local/bin/webapp-0.0.1-SNAPSHOT.jar
echo "applicationStop.sh finished"
