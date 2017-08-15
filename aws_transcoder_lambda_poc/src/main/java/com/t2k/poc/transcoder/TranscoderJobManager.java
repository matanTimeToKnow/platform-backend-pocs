package com.t2k.poc.transcoder;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
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

    private static final String PIPELINE_ID = "1502617090063-d2xqid";

    // All outputs will have this prefix prepended to their output key.
    //private static final String OUTPUT_KEY_PREFIX = "elastic-transcoder-output";

    // This will generate a 480p 16:9 mp4 output.
    private static final String PRESET_ID = "1351620000001-000020";

    static AmazonElasticTranscoder amazonElasticTranscoder = null;


    public static void createTranscoderJob(String inputFileName) throws Exception {
        BasicAWSCredentials  awsCreds = new BasicAWSCredentials("AKIAIAKYHCCN3OORT6ZA", "QqhApjKrNGlomtNItXRrVBtlBXoBgDPiifLs+Ptl");
        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(awsCreds);
        amazonElasticTranscoder = AmazonElasticTranscoderClientBuilder.standard().
                withCredentials(awsStaticCredentialsProvider)
                .withRegion(Regions.EU_WEST_1)
                .build();
        Job job = createElasticTranscoderJob(inputFileName);
        System.out.println("Job has been created: " + job);
    }

    private static Job createElasticTranscoderJob(String inputFileName) throws Exception {
        // Setup the job input using the provided input key.
        JobInput input = new JobInput()
            .withKey(inputFileName);

        // Setup the job output using the provided input key to generate an output key.
        List<CreateJobOutput> outputs = new ArrayList<CreateJobOutput>();
        CreateJobOutput output = new CreateJobOutput()
                .withKey(inputFileName)
                .withPresetId(PRESET_ID);
        outputs.add(output);

        // Create the job.
        CreateJobRequest createJobRequest = new CreateJobRequest()
            .withPipelineId(PIPELINE_ID)
            .withInput(input)
            //.withOutputKeyPrefix(OUTPUT_KEY_PREFIX + "/" )
                .withOutput(output);
        return amazonElasticTranscoder.createJob(createJobRequest).getJob();
    }
}
