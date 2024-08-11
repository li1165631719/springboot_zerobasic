package org.example.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.example.common.HttpResult;
import org.example.common.OssResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * 七牛云图片上传
 *
 * @Author 李志豪
 * @Date 2024/8/10 15:59
 */
@Service
public class QiniuPictureServiceUtils {

    final static Logger logger = LoggerFactory.getLogger(QiniuPictureServiceUtils.class);

    //设置好账号的ACCESS_KEY和SECERT_KEY
    private String ACCESS_KEY = "8xi5Um0yYUjU1A8XVqMvpwqWN6XzRKM-IhiogZT7";
    private String SECRET_KEY = "d-JtN-tCKwXyrmIFIK_-9pVyTEmqmXmRV3FgLnnB";
    private String bucketName = "springboot-zerobais";

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    String upToken = auth.uploadToken(bucketName);
    Configuration config = new Configuration(Region.region0());
    //创建上传对象
    UploadManager uploadManager = new UploadManager(config);

    private static String QINIU_IMAGE_DOMAIN = "shzl4rwya.hd-bkt.clouddn.com";

    public String getUpToken() {
        return upToken;
    }

    public HttpResult<OssResult> saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (StringUtils.isEmpty(fileExt)) {
                return null;
            }
            if (!filterFileExt(fileExt)) {
                return null;
            }

            String fileName = CommonUtil.generateUUID() + "." + fileExt;
            //调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());
            //打印返回的信息
            if (res.isOK() && res.isJson()) {
                logger.info("res.bodyString() : {}", res.bodyString());

                String url = JsonUtils.jsonToPojo(res.bodyString(), HashMap.class).get("key").toString();
                OssResult ossResult = new OssResult();
                ossResult.setFileName(url);
                ossResult.setViewUrl(queryImage(url));
                return new HttpResult<>(ossResult);
            } else {
                logger.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            logger.error("七牛异常:" + e.getMessage());
            return null;
        }
    }

    public String queryImage(String key) {
        try {
            DownloadUrl url = new DownloadUrl(QINIU_IMAGE_DOMAIN, false, key);
            // 带有效期
            long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
            long deadline = System.currentTimeMillis() / 1000 + expireInSeconds;
            String urlString = url.buildURL(auth, deadline);
            return urlString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean filterFileExt(String fileExt) {
        String[] rules = {"JPEG", "jpeg", "JPG", "jpg", "PNG", "png", "GIF", "gif"};
        for (String rule : rules) {
            if (rule.equals(fileExt)) {
                return true;
            }
        }
        return false;
    }

}
