package sama.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3BucketService {

    @Autowired
    private S3Client s3Client;

    public List<String> listBuckets() {
        ListBucketsResponse response = s3Client.listBuckets();
        return response.buckets().stream()
                .map(Bucket::name)
                .collect(Collectors.toList());
    }
}
