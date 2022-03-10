#!/bin/bash

#set packer parameters
cat << EOF > ami.pkrvars.hcl
aws_access_key="$AWS_ACCESS_KEY"
aws_secret_key="$AWS_SECRET_KEY"
aws_region="us-east-1"
ssh_username="ec2-user"
EOF
