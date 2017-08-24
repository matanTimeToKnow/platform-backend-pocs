package com.t2k.poc.transcoder;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import static com.t2k.poc.transcoder.Parameters.SQS_ASSETS_ON_FILE_UPLOAD_QUEUE_NAME;

public class Main {
    
    public static void main(String[] args) throws Exception {
        AmazonSQS amazonSqs = AmazonSQSClientBuilder.standard().
                withCredentials(Parameters.getAWSCredentialsProvider()).
                withRegion(Regions.EU_WEST_1).
                build();

        SqsS3Worker sqsS3Worker = new SqsS3Worker(amazonSqs, SQS_ASSETS_ON_FILE_UPLOAD_QUEUE_NAME);
        Thread workerThread = new Thread(sqsS3Worker);
        workerThread.start();
    }
}
