package com.t2k.poc.transcoder;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoder;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClientBuilder;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.Job;
import com.amazonaws.services.elastictranscoder.model.JobInput;

import java.util.ArrayList;
import java.util.List;


public class TranscoderJobManager {

    static AmazonElasticTranscoder amazonElasticTranscoder = null;


    public static void createTranscoderJob(String inputFileName, String outputFileName) throws Exception {
        amazonElasticTranscoder = AmazonElasticTranscoderClientBuilder.standard().
                withCredentials(Parameters.getAWSCredentialsProvider()).
                withRegion(Regions.EU_WEST_1).build();

        JobInput input = new JobInput().withKey(inputFileName);
        List<CreateJobOutput> outputs = new ArrayList<CreateJobOutput>();
        CreateJobOutput output = new CreateJobOutput().withKey(outputFileName).withPresetId(Parameters.PRESET_ID);
        outputs.add(output);
        CreateJobRequest createJobRequest = new CreateJobRequest().withPipelineId(Parameters.TRANSCODER_PIPELINE_ID).withInput(input).withOutput(output);
        Job job = amazonElasticTranscoder.createJob(createJobRequest).getJob();
        System.out.println("Job has been created: " + job);

        //TODO - make sure job was completed before copying the file
        ////////////////////////////////////////////////////
        Thread.sleep(10000);
        ////////////////////////////////////////////////////

        
        S3Manager.copyFile(Parameters.S3_TRANSCODER_OUTPUT_BUCKET, outputFileName, Parameters.S3_BUCKET, outputFileName);
        System.out.println("transcoded file has been copied to working bucket");
    }

}
