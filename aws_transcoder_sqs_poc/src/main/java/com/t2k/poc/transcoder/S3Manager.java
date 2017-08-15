package com.t2k.poc.transcoder;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectResult;


public class S3Manager {

    public static String bucket = "t2k.bucket.test1";
    public static AmazonS3 s3;

    public static String renameFile(String fileName){
        try {
            String destinationFileName = fileName + "_original_before_trancode";
            BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIAKYHCCN3OORT6ZA", "QqhApjKrNGlomtNItXRrVBtlBXoBgDPiifLs+Ptl");
            AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(awsCreds);
            s3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(awsStaticCredentialsProvider)
                    .withRegion(Regions.EU_WEST_1)
                    .build();

            CopyObjectResult copyObjectResult = s3.copyObject(bucket, fileName, bucket, destinationFileName);
            return destinationFileName;
        } catch (AmazonServiceException e) {
            //System.err.println(e.getErrorMessage());
            //System.exit(1);
        }
        return null;
    }

    public static void deleteFile(String fileName){
        try {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIAKYHCCN3OORT6ZA", "QqhApjKrNGlomtNItXRrVBtlBXoBgDPiifLs+Ptl");
            AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(awsCreds);
            s3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(awsStaticCredentialsProvider)
                    .withRegion(Regions.EU_WEST_1)
                    .build();
            s3.deleteObject(bucket, fileName);
        } catch (AmazonServiceException e) {
            //System.err.println(e.getErrorMessage());
            //System.exit(1);
        }
    }




}
