package sama.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    private AwsBasicCredentials awsCreds() {
        return AwsBasicCredentials.create(
                System.getenv("AWS_ACCESS_KEY_ID"),
                System.getenv("AWS_SECRET_ACCESS_KEY")
        );
    }

    private Region awsRegion() {
        return Region.of(System.getenv("AWS_REGION"));
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(awsRegion())
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds()))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(awsRegion())
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds()))
                .build();
    }
}