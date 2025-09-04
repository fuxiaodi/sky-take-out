package com.sky.controller.admin;

import com.aliyun.oss.model.MultipartUpload;
import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AwsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common") //通用接口
@Api(tags="通用接口") //给接口分组/归类：在 Swagger UI 里会以“通用接口”作为分组标题展示该控制器下的接口。便于检索与过滤：按标签筛选、查看同一类接口。
@Slf4j
public class CommonController {

    @Autowired
    private AwsUtil awsUtil;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){ //参数file名称必须跟接口文档中一致
        log.info("文件上传: {}", file);
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //截取原始文件名的后缀 .png, .jpg
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //构造新文件名称
        String objectName = UUID.randomUUID().toString() + extension;
        //文件的请求路径
        try {
            String filePath = awsUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.info("文件上传失败：{}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);

    }
}
