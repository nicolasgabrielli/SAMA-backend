package sama.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sama.service.S3BucketService;

import java.util.List;

@RestController
public class S3BucketController {

    @Autowired
    private S3BucketService s3BucketService;

    @GetMapping("/buckets")
    public List<String> listBuckets() {
        return s3BucketService.listBuckets();
    }
}