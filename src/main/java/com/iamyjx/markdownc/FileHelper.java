package com.iamyjx.markdownc;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

/**
 * @autor iamyjx
 * @since 15-12-8
 */
public interface FileHelper {
    String read(File sourceFile);
    String read(File sourceFile,String encoding);
    String read(String sourcePath);
    void write(String data,File destFile);
    void write(String data,File destFile,String encoding);
    void write(String data,String destPath,String encoding);
    void write(String data,String destPath);
    Collection<File> list(File directory,String[] extensions);
}
