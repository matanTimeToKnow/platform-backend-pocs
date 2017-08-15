package com.t2k.poc.transcoder;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.transfer.internal.CompleteMultipartUpload;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


public class SqsS3Worker implements Runnable {

    private static final int MAX_NUMBER_OF_MESSAGES = 5;
    private static final int VISIBILITY_TIMEOUT = 15;
    private static final int WAIT_TIME_SECONDS = 15;
    private static final ObjectMapper mapper = new ObjectMapper();
    
    private AmazonSQS amazonSqs;
    private String queueUrl;
    
    private volatile boolean shutdown = false;
    
    public SqsS3Worker(AmazonSQS amazonSqs, String queueUrl) {
        this.amazonSqs = amazonSqs;
        this.queueUrl = queueUrl;
    }
    
    @Override
    public void run() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
            .withQueueUrl(queueUrl)
            .withMaxNumberOfMessages(MAX_NUMBER_OF_MESSAGES)
            .withVisibilityTimeout(VISIBILITY_TIMEOUT)
            .withWaitTimeSeconds(WAIT_TIME_SECONDS);
        
        while (!shutdown) {
            // Long pole the SQS queue.  This will return as soon as a message
            // is received, or when WAIT_TIME_SECONDS has elapsed.
            List<Message> messages = amazonSqs.receiveMessage(receiveMessageRequest).getMessages();
            if (messages == null) {
                // If there were no messages during this poll period, SQS
                // will return this list as null.  Continue polling.
                continue;
            }

            for (Message message : messages) {
                try{
                    S3EventNotification s3EventNotification = S3EventNotification.parseJson(message.getBody());
                    String fileName = s3EventNotification.getRecords().get(0).getS3().getObject().getKey();
                    TranscoderJobManager.createTranscoderJob(fileName);
                }catch (Exception e){
                    System.out.println("Error creating transcoder job");

                }
                // Delete the message from the queue.
                amazonSqs.deleteMessage(new DeleteMessageRequest().withQueueUrl(queueUrl).withReceiptHandle(message.getReceiptHandle()));
            }

        }
    }

    public void shutdown() {
        shutdown = true;
    }
}
