package com.t2k.poc.transcoder;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.event.S3EventNotification;

public class FileUploadHandlerLambda implements RequestHandler<S3EventNotification, String>{

    @Override
    public String handleRequest(S3EventNotification s3EventNotification, Context context) {
        LambdaLogger logger = context.getLogger();

        try{
            S3EventNotification.S3EventNotificationRecord record = s3EventNotification.getRecords().get(0);
            logger.log("input: " + record  + "\n" );
            String eventName = record.getEventName();
            logger.log("event name " + eventName + "\n");
            String fileUploadEventName = "ObjectCreated:Put";
            if(fileUploadEventName.equals(eventName)){
                String fileName = record.getS3().getObject().getKey();
                System.out.println("Start transcode process for: " + fileName);
                String renamedFileName = S3Manager.renameFile(fileName);
                TranscoderJobManager.createTranscoderJob(renamedFileName, fileName);
            }
        }

        catch (Exception e){
            logger.log(e.getMessage());
            e.printStackTrace();
        }

        return "lambda function done";
    }

}
