
package test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@PropertySource(value="classpath:awsAccess.properties")
public class SqsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqsTestApplication.class, args);
    }

    @SqsListener(value = "${queue.name}")
    private void receiveMessage(String message) {
        System.out.println("Got SQS message: " + message);
    }

}