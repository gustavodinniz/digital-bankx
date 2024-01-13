#!/bin/bash
echo "########### Setting region as env variable ##########"
export AWS_REGION=sa-east-1

echo "########### Setting up localstack profile ###########"
aws configure set aws_access_key_id access_key --profile=localstack
aws configure set aws_secret_access_key secret_key --profile=localstack
aws configure set region $AWS_REGION --profile=localstack

echo "########### Setting default profile ###########"
export AWS_DEFAULT_PROFILE=localstack

echo "########### Setting env variables ###########"
export SQS_BANKX_TRANSACTIONS=SQS-BankX-Transactions
export S3_BANKX_TRANSACTIONS=bankx-transactions

echo "########### Creating update store inventory SQS ###########"
aws --endpoint-url=http://localstack:4566 sqs create-queue --queue-name $SQS_BANKX_TRANSACTIONS

echo "########### ARN for update store inventory SQS ###########"
SQS_BANKX_TRANSACTIONS_ARN=$(aws --endpoint-url=http://localstack:4566 sqs get-queue-attributes\
                  --attribute-name QueueArn --queue-url=http://localhost:4566/000000000000/"$SQS_BANKX_TRANSACTIONS"\
                  |  sed 's/"QueueArn"/\n"QueueArn"/g' | grep '"QueueArn"' | awk -F '"QueueArn":' '{print $2}' | tr -d '"' | xargs)

echo "########### Listing queues ###########"
aws --endpoint-url=http://localhost:4566 sqs list-queues

echo "########### Creating S3 buckets ###########"
aws --endpoint-url=http://localhost:4566 s3api create-bucket\
    --bucket $S3_BANKX_TRANSACTIONS --region $AWS_REGION\
    --create-bucket-configuration LocationConstraint=$AWS_REGION

echo "########### Listing S3 buckets ###########"
aws --endpoint-url=http://localhost:4566 s3api list-buckets

echo "########### Setting S3 buckets notification configurations ###########"
aws --endpoint-url=http://localhost:4566 s3api put-bucket-notification-configuration\
    --bucket $S3_BANKX_TRANSACTIONS\
    --notification-configuration  '{
                                      "QueueConfigurations": [
                                         {
                                           "QueueArn": "'"$SQS_BANKX_TRANSACTIONS_ARN"'",
                                           "Events": ["s3:ObjectCreated:*"]
                                         }
                                       ]
                                     }'

echo "########### Get S3 bucket notification configurations ###########"
aws --endpoint-url=http://localhost:4566 s3api get-bucket-notification-configuration\
    --bucket $S3_BANKX_TRANSACTIONS

echo "########### Command to view SQS messages ###########"
echo "aws --endpoint-url=http://localhost:4566 sqs receive-message --queue-url=http://localhost:4566/000000000000/SQS-BankX-Transactions"