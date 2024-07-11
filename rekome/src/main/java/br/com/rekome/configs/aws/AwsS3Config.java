package br.com.rekome.configs.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@Profile("aws")
public class AwsS3Config {

	@Value("${spring.cloud.aws.credentials.access-key}")
    private String awsAccessKey;

	@Value("${spring.cloud.aws.credentials.secret-key}")
    private String awsSecretKey;
    
    @Value("${spring.cloud.aws.s3.region}")
    private String region;

	@Bean
    AmazonS3 s3Client() {

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        var awsS3Config = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(region)
                .build();
        
        return awsS3Config;
    }

}
