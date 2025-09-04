package com.sky.utils;

import com.sky.properties.AwsProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@AllArgsConstructor
@Slf4j
public class AwsUtil {

    private String region;
    private AwsProperties.Credentials credentials;
    private AwsProperties.S3 s3;

    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {

        // 创建AWSClient实例。
        S3Client client = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(credentials.getAccessKey(), credentials.getSecretKey())
                        )
                )
                .build();
        try {
            // 创建 PutObject 请求
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(s3.getBucket())
                    .key(objectName)   // 相当于文件名/路径
                    .build();

            client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));
        } catch (AwsServiceException oe) {
            System.out.println("Caught an AWSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getMessage());
            System.out.println("Error Code:" + oe.statusCode());
            System.out.println("Request ID:" + oe.requestId());
            System.out.println("Host ID:" + oe.extendedRequestId());
        } catch (SdkClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (client != null) {
                client.close();
            }
        }

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(s3.getBucket())
                .append(".s3.")
                .append(region)
                .append(".amazonaws.com/")
                .append(objectName);

        log.info("文件上传到:{}", stringBuilder.toString());

        return stringBuilder.toString();
    }
}
