package com.t2k.poc.transcoder;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class SqsFileUploadHandler {

    private static final String SQS_QUEUE_URL = "https://sqs.eu-west-1.amazonaws.com/361459014155/t2k_s3_transcode_queue";

    private static AmazonSQS amazonSqs = null;
    
    public static void main(String[] args) throws Exception {

        //setup sqs client
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIAKYHCCN3OORT6ZA", "QqhApjKrNGlomtNItXRrVBtlBXoBgDPiifLs+Ptl");
        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(awsCreds);
        amazonSqs = AmazonSQSClientBuilder.standard()
                .withCredentials(awsStaticCredentialsProvider)
                .withRegion(Regions.EU_WEST_1)
                .build();

        // Setup our notification worker.
        SqsS3Worker sqsS3Worker = new SqsS3Worker(amazonSqs, SQS_QUEUE_URL);
        Thread workerThread = new Thread(sqsS3Worker);
        workerThread.start();


        //sqsQueueNotificationWorker.shutdown();

    }
}
