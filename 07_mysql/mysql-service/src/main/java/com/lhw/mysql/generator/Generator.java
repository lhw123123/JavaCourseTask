package com.lhw.mysql.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhw
 * @title
 * @description
 * @created 8/6/21 5:55 PM
 * @changeRecord
 */
public class Generator {
    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);

    public void generator() throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overWrite = true;
        //文件保存路径
        File saveDir = new File("target/mybatis");
        if (!saveDir.exists()) {
            boolean result = saveDir.mkdirs();
            LOG.info("文件夹创建结果 {}", result);
        } else {//清除缓存目录
            cleanFile(saveDir);
        }
        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String projectName = "mysql-service";
        filePath = filePath.substring(0, filePath.indexOf(projectName) + projectName.length());
        File configFile = new File(filePath + "/src/main/resources/mybatis/generator/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overWrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        LOG.info("\n\n\033[31;0m文件保存目录: {} \033[0m", saveDir.getAbsolutePath());
    }

    /**
     * 清理文件
     *
     * @param file
     */
    private void cleanFile(File file) throws Exception {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (null != files && files.length > 0) {
                    for (File item : files) {
                        cleanFile(item);
                    }
                }
            }
            Files.delete(file.toPath());
        }
    }

    public static void main(String[] args) {
        try {
            Generator generatorSqlMap = new Generator();
            generatorSqlMap.generator();
        } catch (Exception e) {
            LOG.error("ERROR:{}", e);
        }
    }

}
