package com.example.janche.common.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 项目路径
 * <p>文件上传下载获取项目路径</p>
 */
public class ProjectPath {
    private  static  final Logger logger = LoggerFactory.getLogger(ProjectPath.class);

    /**
     * 获取根目录
     */
    public static String getProjectAbsolutPath() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("获取项目路径失败");
        }
        if (!path.exists()) path = new File("");
        return path.getAbsolutePath();
    }
}
