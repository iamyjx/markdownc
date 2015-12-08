package com.iamyjx.markdownc;

import com.iamyjx.markdownc.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @autor iamyjx
 * @since 15-12-8
 */
public class DefaultConverter implements Converter {
    private final Log logger= LogFactory.getLog(this.getClass());
    private FileHelper fileHelper;
    private ProcessStrategy strategy;
    private String data;
    private String[] extensions;
    private File sourceFile;
    private File destDirectory;
    private String defaultPath;

    public DefaultConverter(FileHelper fileHelper, ProcessStrategy strategy, String[] extensions) {
        this.fileHelper = fileHelper;
        this.strategy = strategy;
        this.extensions=extensions;
        defaultPath=this.strategy.getDefaultPath();
    }
    public void convert(String sourcePath,String destPath) {
        sourceFile = new File(normalized(sourcePath));
        //append 一个build文件夹
        this.destDirectory = new File(normalized(destPath)+File.separator+"build");
        convert(sourceFile);
    }

    private void convert(File sourceFile) {
        if(!sourceFile.exists()) {
            if (logger.isInfoEnabled()) {
                logger.info("文件"+sourceFile.getAbsolutePath()+"不存在");
            }
        }
        if (sourceFile.isDirectory()) {
            if (logger.isInfoEnabled()) {
                //好奇怪，好像这个迭代有闭包的问题
                logger.info("开始处理文件夹 "+sourceFile.getName());
            }
            for(File file : fileHelper.list(sourceFile,extensions)){
                convert(file);
            }
        }else {
            if (logger.isInfoEnabled()) {
                logger.info("开始处理文件 "+sourceFile.getName());
            }
            //TODO 对比目标路径下的文件，决定是否重新convert
            String markdownStr = fileHelper.read(sourceFile);
            markdownStr = strategy.beforeProcess(markdownStr);
            markdownStr = strategy.process(markdownStr);
            markdownStr = strategy.afterProcess(markdownStr);
            //构建相对目录,目标文件的路径
            String relativePath = "";
            if (sourceFile.getAbsolutePath().startsWith(this.sourceFile.getAbsolutePath())) {
                relativePath = sourceFile.getAbsolutePath().replace(this.sourceFile.getAbsolutePath(), "");
            }
//            relativePath = relativePath + sourceFile.getName();
            for (String str : extensions) {
                relativePath=relativePath.replace(str, "html");
            }
            fileHelper.write(markdownStr, new File(destDirectory, relativePath));
            if (logger.isInfoEnabled()) {
                logger.info("处理文件 "+sourceFile.getName()+" 完成");
            }
        }


    }
    private String normalized (String path) {
        //为空，则默认路径
        if (StringUtils.isEmpty(path)) {
            path=defaultPath;
        }
        //处理classpath
        if(path.startsWith("classpath:")){
            String classpath="";
            try {
                classpath=this.getClass().getResource("/").toURI().getPath();
            } catch (URISyntaxException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("获取classpath出错："+e);
                }
                e.printStackTrace();
            }
            path=classpath+File.separator+path.replace("classpath:","");
        }
        return path;
    }



}
