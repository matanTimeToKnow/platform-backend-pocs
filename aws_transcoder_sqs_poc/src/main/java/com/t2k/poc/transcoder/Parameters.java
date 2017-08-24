package com.t2k.poc.transcoder;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;

public class Parameters {

    static final String TRANSCODER_PIPELINE_ID = "1502617090063-d2xqid";

    static final String PRESET_ID = "1351620000001-000020";// This will generate a 480p 16:9 mp4 output.

    static String S3_BUCKET = "t2k.bucket.assets";

    static String S3_TRANSCODER_OUTPUT_BUCKET = "t2k.bucket.assets.transcoder.output";

    static final String SQS_ASSETS_ON_FILE_UPLOAD_QUEUE_NAME = "ASSETS_ON_FILE_UPLOAD_QUEUE";

    static final String SQS_TRANSCODER_OUTPUT_QUEUE_NAME = "TRANSCODER_OUTPUT_QUEUE";

    static AWSCredentialsProvider getAWSCredentialsProvider(){
        return new ClasspathPropertiesFileCredentialsProvider();
    }





}
