package com.t2k.poc.transcoder;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.event.S3EventNotification;

public class FileUploadHandler implements RequestHandler<S3EventNotification, String>{

    @Override
    public String handleRequest(S3EventNotification input, Context context) {
        LambdaLogger logger = context.getLogger();

        try{
            logger.log("input: " + input.getRecords().get(0)  + "\n" );
            String eventName = input.getRecords().get(0).getEventName();
            logger.log("event name " + eventName + "\n");
            String createS3EventName = "ObjectCreated:Put";
            if(createS3EventName.equals(eventName)){
                String key = input.getRecords().get(0).getS3().getObject().getKey();
                //String renamedFileName = S3Manager.renameFile(key);
                //S3Manager.deleteFile(key);
                TranscoderJobManager.createTranscoderJob(key);
            }
        }

        catch (Exception e){
            logger.log(e.getMessage());
            e.printStackTrace();
        }

        return input.toString() + " + test 1 2 3 ";
    }

}
