package com.iamyjx.markdownc;

import com.iamyjx.markdownc.util.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.markdownj.MarkdownProcessor;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * @autor iamyjx
 * @since 15-12-2
 */
public class DefaultStrategy implements MarkdownConverter.Strategy {
    private int count=0;
    private String buildStr;
    private final static String PRE_HTML="<!DOCTYPE html>\n" +
            "<html lang=\"zh-CN\">\n" +
            "<head>\n" +
//            "    <base href=\"http://www.w3school.com.cn/i/\" />\n"+
            "    <meta charset=\"utf-8\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" charset=\"utf-8\"  href=\"${context}/css/main.css\"/>\n"+
            "</head>\n" +
            "<body>";
    private final static String AFTER_HTML="</body>\n" +
            "</html>";
    private final Log logger= LogFactory.getLog(this.getClass());


    public void process(File fromeFile,String destStr) {
        buildStr=destStr+File.separator+"build";
        if (!fromeFile.exists()) {
            if (logger.isInfoEnabled()) {
                logger.info("文件 "+fromeFile.getPath()+"不存在; ");
            }
            return;
        }
        MarkdownProcessor processor = new MarkdownProcessor();
        String content = null;
        try {
            content = new Scanner(fromeFile).useDelimiter("\\Z").next();
            if (logger.isInfoEnabled()) {
                logger.info("文件载入 " + fromeFile.getName());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("读取文件 " + fromeFile.getName() + "出错");
        }
        String convertedStr = processor.markdown(content);
        convertedStr=PRE_HTML+convertedStr+AFTER_HTML;
        PrintWriter out = null;
        try {
            //构建源文件的文件层次
            String path=fromeFile.getAbsolutePath();
            File toFile = new File(buildStr+File.separator+path.replace(destStr,"").replace("." + FileUtils.getExt(fromeFile), ".html"));
            if(!toFile.getParentFile().exists())toFile.getParentFile().mkdirs();
            out = new PrintWriter(toFile);
            out.println(convertedStr);
            if (logger.isInfoEnabled()) {
                logger.info("文件处理完成 " + fromeFile.getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (logger.isInfoEnabled()) {
                logger.info("输入路径错误 " + fromeFile.getName());
            }
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public void afterProcess(String destStr) {
        File dest=new File(buildStr+File.separator+"css"+File.separator+"main.css");
        dest.getParentFile().mkdirs();
        try {
            File file= FileUtils.getFile("classpath:main.css");
            if (!file.exists()) {
                Files.copy(file.toPath(),dest.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error(e);
            }
        }
    }
}
