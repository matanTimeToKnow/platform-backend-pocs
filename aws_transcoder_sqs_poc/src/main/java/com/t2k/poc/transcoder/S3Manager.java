package com.t2k.poc.transcoder;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectResult;

import java.util.regex.Pattern;


public class S3Manager {

    public static String renameFile(String fileName){
        try {
            String[] filePartsArray = fileName.split(Pattern.quote("."));
            String destinationFileName = filePartsArray[0] + "_original_before_trancode"  + "." + filePartsArray[1];
            AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(Parameters.getAWSCredentialsProvider()).withRegion(Regions.EU_WEST_1).build();
            CopyObjectResult copyObjectResult = s3.copyObject(Parameters.S3_BUCKET, fileName, Parameters.S3_BUCKET, destinationFileName);
            s3.deleteObject(Parameters.S3_BUCKET, fileName);
            return destinationFileName;
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
        return null;
    }
    public static void copyFile(String bucket, String fileName, String toBucket, String toFileName){
        try {
            AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(Parameters.getAWSCredentialsProvider()).withRegion(Regions.EU_WEST_1).build();
            CopyObjectResult copyObjectResult = s3.copyObject(bucket, fileName, toBucket, toFileName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }
}
