transcoding POC

#############################
option #1####################
#############################
	1. Create an AWS Lambda function:
		- intercept file upload
		- that create an AWS Transcoder client. 
		- and create a new Transcoder pipeline if one does not exists.
		- create a new job that transcode the input
	2. deploy the Lambda function 
		- a CI/CD mechanism is needed
		- build the artifact with gradle
		- create an AWS Lambda client
		- create Lambda Function configuration, most important: add S3 upload trigger		
		- deploy the code
		
#############################
option #2####################
#############################
	1. Create a Java ap0plication with a main method
	2. Create SQS queue
	3. Use S3 client and publish onupload events to the above queue
	4. Create an SQS client
	5. poll the queue for incoming messages
	6. In the above event handler	
		- that create an AWS Transcoder client. 
		- and create a new Transcoder pipeline if one does not exists.
		- create a new job that transcode the input
		
#############################
option #3####################
#############################
	1. In t2k application, create a public HTTP endpoint:
		- create an AWS Transcoder client. 
		- and create a new Transcoder pipeline if one does not exists.
		- create a new job that transcode the input
	2. In t2k application:
		- Create SNS topic
		- configure the S3 bucket the publish onupload event to the above SNS topic
		- subscribe to SNS topic
		- refer subscription to HTTP endpoint
		
#############################
option #4####################
#############################
	mix all the above:
	1. connect S3  to SNS notification
	2. connect Lambda to SNS -> invoke transcoder
	3. connect SQS queue to SNS -> invoke transcoder


#############################
option #5####################
#############################
    1. when the editor performs a file upload it will send the file the the backend server, and not interact with s3 by itself.
    2. once the file is in the backend server, upload it to a dedicated bucket in S3
    3. activate the transcoder on the given file
    4. copy the output to a dedicated location


the POC did not tackle the following issues:
	logging
	error handling
	bucket management:
		- bucket objects renaming - triggers the lambda over and over again	
		- re-transcode
	different file format
	transcoder fine tuning 
