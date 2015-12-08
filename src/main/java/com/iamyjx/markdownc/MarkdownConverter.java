package com.iamyjx.markdownc;

import com.iamyjx.markdownc.util.FileUtils;
import com.iamyjx.markdownc.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * markdown文件转换器
 * @autor iamyjx
 * @since 15-12-1
 */
public class MarkdownConverter {
    Log logger= LogFactory.getLog(this.getClass());
    private String destStr; //构建的相对根目录
    public interface Strategy{
        void process(File fromFile,String destStr);
        void afterProcess(String destStr);
    }
    private Strategy strategy;
    private String ext;

    public MarkdownConverter(Strategy strategy, String ext) {
        this.strategy = strategy;
        this.ext = ext;
    }
    public void start(String[] args) throws IOException, URISyntaxException {
        start(args,"");
    }
    public void start(String[] args,String toPath) throws IOException, URISyntaxException {
        String classpath=this.getClass().getResource("/").toURI().toString();
        if(args==null||args.length==0) {
            if (logger.isInfoEnabled()) {
                logger.info("未指定目录，默认处理classpath路径下的md文件 ");
                logger.info("classpath路径: "+this.getClass().getResource("/"));
            }
            processDirectoryTree(new File(classpath),classpath);
        }
        else{
            //指定文件或者目录
            for (String arg : args) {
                File fileArg = FileUtils.getFile(arg,getClass());
                if(fileArg.isDirectory())
                {
                    if (logger.isInfoEnabled()) {
                        logger.info("处理目录 "+fileArg.getName());
                    }
                    toPath=StringUtils.isEmpty(toPath)?fileArg.getParentFile().getAbsolutePath():toPath;
                    processDirectoryTree(fileArg,toPath);
                }
                else {
                    if (!arg.endsWith("." + ext)) {
                        arg+="."+ext;
                    }
                    toPath=StringUtils.isEmpty(toPath)?fileArg.getParentFile().getAbsolutePath():toPath;
                    strategy.process(fileArg,toPath);
                    strategy.afterProcess(toPath);
                }
            }
        }
    }
    protected void processDirectoryTree(File fromDirectory,String destStr) throws IOException {
        this.destStr=destStr;
        for(File file : FileUtils.walk(fromDirectory.getAbsolutePath(),".*\\."+ext)){
            strategy.process(file,destStr);
        }
        strategy.afterProcess(destStr);

    }
}
