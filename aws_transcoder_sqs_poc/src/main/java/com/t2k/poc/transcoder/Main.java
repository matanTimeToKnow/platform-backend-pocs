package com.t2k.poc.transcoder;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class Main {
    
    public static void main(String[] args) throws Exception {
        AmazonSQS amazonSqs = AmazonSQSClientBuilder.standard().
                withCredentials(Parameters.getAWSCredentialsProvider()).
                withRegion(Regions.EU_WEST_1).
                build();

        // Setup our notification worker.
        SqsS3Worker sqsS3Worker = new SqsS3Worker(amazonSqs, Parameters.SQS_QUEUE_URL);
        Thread workerThread = new Thread(sqsS3Worker);
        workerThread.start();
    }
}
