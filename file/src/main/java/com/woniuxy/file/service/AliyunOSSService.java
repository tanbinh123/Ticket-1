package com.woniuxy.file.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.woniuxy.file.config.ConstantProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class AliyunOSSService {
    @Resource
    private ConstantProperties constantProperties;

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 上传
     *
     * @param file 文件对象
     * @return OSS文件访问地址
     */
    public String upload(File file) {
        log.info("==> OSS文件上传开始: " + file.getName());
        String endPoint = constantProperties.getEndPoint();
        String accessKeyId = constantProperties.getKeyId();
        String accessKeySecret = constantProperties.getKeySecret();
        String bucketName = constantProperties.getBucketName();
        String fileHost = constantProperties.getFileHost();
        String dateStr = format.format(new Date());
        String suffix = file.getName().substring(file.getName().lastIndexOf("."));

        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        try {
            //容器不存在，就创建
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
                // 设置桶权限为 私有
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.Private);
            }
            //创建文件路径
            String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;
            String fileUrl = fileHost + "/" + (dateStr + "/" + fileName);

            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            if (null != result) {
                String ossFileUrl = "https://" + bucketName + "." + endPoint + "/" + fileUrl;
                log.info("==> OSS文件上传成功,访问地址: " + ossFileUrl);
                // 设置文件读写权限为 公共读
                ossClient.setObjectAcl(bucketName, fileUrl, CannedAccessControlList.PublicRead);

                return ossFileUrl;
            }
        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getMessage());
        } finally {
            // 关闭
            ossClient.shutdown();
        }

        return null;
    }

}
