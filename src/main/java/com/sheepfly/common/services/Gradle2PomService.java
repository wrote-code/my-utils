package com.sheepfly.common.services;

import org.apache.commons.cli.CommandLine;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * gradle转pom。
 *
 * @author sheepfly
 */
public class Gradle2PomService {
    private static final Logger log = LoggerFactory.getLogger(Gradle2PomService.class);

    /**
     * 版本号，用作替换
     */
    private Properties version;
    /**
     * jar包
     */
    private List<Map<String, String>> libList;
    /**
     * 命令行参数。
     */
    private CommandLine cli;
    /**
     * build.gradle路径
     */
    private String buildFilePath;
    /**
     * 版本变量文件路径
     */
    private String versionPath;

    /**
     * 初始化。
     *
     * @param cli 命令行参数
     */
    public void init(CommandLine cli) {
        this.cli = cli;
        this.buildFilePath = cli.getOptionValue("b");
        if (cli.hasOption("v")) {
            this.versionPath = cli.getOptionValue("v");
        }
        log.info("应用初始化完成");
    }

    /**
     * 开始服务。
     */
    public void doService() {
        this.loadBuildFile();
        if (this.versionPath != null) {
            this.loadVersionFile();
            this.replaceVersion();
        }
        this.createPom();
    }

    /**
     * 加载版本变量文件
     */
    public void loadVersionFile() {
        Properties version = new Properties();
        InputStream inputStream = null;
        try {
            log.info("开始加载版本变量");
            inputStream = new FileInputStream(this.versionPath);
            version.load(inputStream);
            this.version = version;
            log.info("版本变量加载完成");
        } catch (FileNotFoundException e) {
            log.error("文件不存在", e);
        } catch (IOException e) {
            log.error("io异常", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("关闭文件流失败", e);
            }
        }
    }

    /**
     * 加载jar包依赖
     */
    public void loadBuildFile() {
        InputStream inputStream;
        InputStreamReader insReader;
        BufferedReader bfReader = null;
        try {
            log.info("开始加载依赖");
            List<Map<String, String>> libList = new ArrayList<>();
            inputStream = new FileInputStream(this.buildFilePath);
            insReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bfReader = new BufferedReader(insReader);
            String currentLine;
            while ((currentLine = bfReader.readLine()) != null) {
                currentLine = currentLine.trim();
                Map<String, String> tempMap = new HashMap<>();
                String[] libDetail = currentLine.split(":");
                // 不是依赖的文本
                if (libDetail.length != 3) {
                    log.warn("不是依赖:" + currentLine);
                    continue;
                }
                log.info("找到依赖: " + currentLine);
                tempMap.put("groupId", libDetail[0]);
                tempMap.put("artifactId", libDetail[1]);
                tempMap.put("version", libDetail[2]);
                libList.add(tempMap);
            }
            this.libList = libList;
            log.info("依赖加载完成");
        } catch (FileNotFoundException e) {
            log.error("build.gradle不存在", e);
        } catch (UnsupportedEncodingException e) {
            log.error("不支持的编码, 请使用utf-8编码的文件", e);
        } catch (IOException e) {
            log.error("io异常", e);
        } finally {
            try {
                bfReader.close();
            } catch (IOException e) {
                log.error("关闭文件流失败");
            }
        }
    }

    /**
     * 替换版本变量。
     */
    public void replaceVersion() {
        log.info("开始替换版本号");
        for (Map<String, String> ele : this.libList) {
            if (this.version.containsKey(ele.get("version"))) {
                ele.put("version", this.version.getProperty(ele.get("version")));
            }
        }
        log.info("版本号替换完成");
    }

    /**
     * 创建pom。
     */
    public void createPom() {
        log.info("开始生成pom");
        Document pom = new DefaultDocument();
        Element project = new DefaultElement("project");
        pom.setRootElement(project);
        log.info("添加基本信息");
        Element modelVersion = new DefaultElement("modelVersion");
        modelVersion.setText("4.0.0");
        Element groupId = new DefaultElement("groupId");
        groupId.setText("com.example");
        Element artifactId = new DefaultElement("artifactId");
        artifactId.setText("gradle");
        Element version = new DefaultElement("version");
        version.setText("1.0.0.RELEASE");
        project.add(modelVersion);
        project.add(groupId);
        project.add(artifactId);
        project.add(version);

        log.info("基本信息添加完成，开始添加jar包");

        Element dependencies = new DefaultElement("dependencies");
        project.add(dependencies);
        for (Map<String, String> ele : this.libList) {
            dependencies.add(createDependency(ele));
        }
        log.info("依赖添加完成，开始写入xml");

        OutputStream outputStream = null;
        OutputStreamWriter osWriter = null;
        XMLWriter xmlWriter = null;
        try {
            String pomDir = new File(this.buildFilePath).getParent();
            outputStream = new FileOutputStream(pomDir + "/pom.xml");
            osWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            xmlWriter = new XMLWriter(osWriter, new OutputFormat("  ", true));
            xmlWriter.write(pom);
            xmlWriter.flush();
            log.info("pom创建完成");
        } catch (FileNotFoundException e) {
            log.error("文件不存在", e);
        } catch (UnsupportedEncodingException e) {
            log.error("不支持的编码", e);
        } catch (IOException e) {
            log.error("io异常", e);
        } finally {
            try {
                xmlWriter.close();
            } catch (IOException e) {
                log.error("io异常", e);
            }
        }
    }

    /**
     * 创建依赖。
     *
     * @param dependencyMap 依赖信息
     *
     * @return 依赖
     */
    public Element createDependency(Map<String, String> dependencyMap) {
        Element dependency = new DefaultElement("dependency");
        Element groupId = new DefaultElement("groupId");
        groupId.setText(dependencyMap.get("groupId"));
        Element artifactId = new DefaultElement("artifactId");
        artifactId.setText(dependencyMap.get("artifactId"));
        Element version = new DefaultElement("version");
        version.setText(dependencyMap.get("version"));
        dependency.add(groupId);
        dependency.add(artifactId);
        dependency.add(version);
        return dependency;
    }
}
