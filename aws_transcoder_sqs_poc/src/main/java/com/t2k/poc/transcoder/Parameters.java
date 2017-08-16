package com.t2k.poc.transcoder;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

public class Parameters {

    static final String PIPELINE_ID = "1502617090063-d2xqid";

    static final String PRESET_ID = "1351620000001-000020";// This will generate a 480p 16:9 mp4 output.

    static String S3_BUCKET = "t2k.bucket.input";

    static String S3_TRANSCODER_OUTPUT_BUCKET = "t2k.bucket.transcoder.output";

    static final String SQS_QUEUE_URL = "https://sqs.eu-west-1.amazonaws.com/361459014155/t2_transcode_queue";

    static final String ACCESS_KEY = "AKIAIAKYHCCN3OORT6ZA";

    static final String SECRET_KEY = "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";

    static AWSCredentialsProvider getAWSCredentialsProvider(){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AWSCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(awsCreds);
        return awsStaticCredentialsProvider;
    }





}
