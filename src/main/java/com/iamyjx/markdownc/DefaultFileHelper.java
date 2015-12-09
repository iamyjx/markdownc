package com.iamyjx.markdownc;

import com.iamyjx.markdownc.util.Assert;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * @autor iamyjx
 * @since 15-12-8
 */
public class DefaultFileHelper implements FileHelper {
    private final Log logger= LogFactory.getLog(this.getClass());



    public String read(File sourceFile) {
        return read(sourceFile,null);

    }

    public String read(File sourceFile, String encoding) {
        String result="";
        try {
            result= FileUtils.readFileToString(sourceFile,encoding);
        } catch (IOException e) {
            e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("读取文件 "+sourceFile.getAbsolutePath()+" 失败");
            }
        }
        return result;
    }

    public String read(String sourcePath) {
        File file=new File(sourcePath);
        if(!file.exists()) Assert.notNull(null,sourcePath+"不存在");
        return read(file);
    }

    public void  write(String data, File destFile) {
        write(data,destFile,null);
    }

    public void write(String data, File destFile, String encoding) {
        try {
            FileUtils.write(destFile,data,encoding);
        } catch (IOException e) {
            e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("写入文件 "+destFile.getAbsolutePath()+" 失败："+e);
            }
        }
    }

    public void write(String data, String destPath) {
               write(data,destPath,null);
    }
    public void write(String data,String destPath,String encoding){
        File file=new File(destPath);
        if(!file.getParentFile().exists()){
            try {
                FileUtils.forceMkdir(file.getParentFile());
                write(data,file,encoding);
            } catch (IOException e) {
                e.printStackTrace();
                if (logger.isErrorEnabled()) {
                    logger.error("写入文件 "+file.getAbsolutePath()+"时，创建父文件夹失败 ");
                }
            }
        }
    }
    public Collection<File> list(File directory, String[] extensions) {
       return FileUtils.listFiles(directory,extensions,true);
    }

    public void copyTo(File file, File destDirectory) {
        try {
            FileUtils.copyFileToDirectory(file,destDirectory);
        } catch (IOException e) {
            e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("复制文件 "+file.getName()+" 到 "+destDirectory.getAbsolutePath()+" 时，出错");
            }
        }
    }
}
