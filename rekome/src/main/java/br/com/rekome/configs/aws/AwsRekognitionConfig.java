package br.com.rekome.configs.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;

@Configuration
@Profile("aws")
public class AwsRekognitionConfig {

	@Value("${spring.cloud.aws.credentials.access-key}")
    private String awsAccessKey;

	@Value("${spring.cloud.aws.credentials.secret-key}")
    private String awsSecretKey;
    
    @Value("${spring.cloud.aws.s3.region}")
    private String region;
    
	@Bean
	RekognitionClient rekognitionClient() {
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create(awsAccessKey, awsSecretKey);

		var rekClient = RekognitionClient.builder()
				.region(Region.of(region))
				.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
				.build();

		return rekClient;
	}
}
