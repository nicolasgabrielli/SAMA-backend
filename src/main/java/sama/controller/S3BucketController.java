package sama.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sama.service.S3BucketService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class S3BucketController {

    private final S3BucketService s3BucketService;

    @GetMapping("/buckets")
    public List<String> listBuckets() {
        return s3BucketService.listBuckets();
    }
}