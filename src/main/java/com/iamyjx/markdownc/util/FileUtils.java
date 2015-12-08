package com.iamyjx.markdownc.util;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @autor iamyjx
 * @since 15-12-1
 */
public final class FileUtils {
    private static  final Log logger= LogFactory.getLog(FileUtils.class.getSimpleName());
    public static final String CLASSPATH_URL_PREFIX="classpath:";


    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = FileUtils.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                }
                catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl;
    }

    public static File getFile(String location) {
          return getFile(location,null);
    }
    public static File getFile(String location,Class cl) {
        Assert.notNull(location, "Location must not be null");
        File file=null;
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            location=location.substring(CLASSPATH_URL_PREFIX.length());
            cl=cl==null?getDefaultClassLoader().getClass():cl;
            try {
                file= new File(new URL(cl.getResource("/")+location).toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                // Try to parse the location as a URL...
                URL url = new URL(location);
                file= new File(url.toURI());
            }
            catch (MalformedURLException ex) {
                // No URL -> resolve as resource path.
                file= new File(location);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        if (file != null && file.exists()) {
            try {
                file=file.getCanonicalFile();
            } catch (IOException e) {
                e.printStackTrace();
                if (logger.isInfoEnabled()) {
                    logger.info("文件 "+file.getAbsolutePath()+"存在错误");
                }
            }
        }
        return file;
    }
    public static File[] local(File dir, final String regex) {
        return dir.listFiles(new FilenameFilter() {
            private Pattern pattern=Pattern.compile(regex);
            public boolean accept(File dir, String name) {
                return pattern.matcher(new File(name).getName()).matches();
            }
        });
    }

    public static File[] local(String path, final String regex) {
        return local(new File(path),regex);
    }


    public static TreeInfo walk(String start, String regex) {
        return recurseDirs(new File(start), regex);
    }

    static TreeInfo recurseDirs(File startDir, String regex) {
        TreeInfo result=new TreeInfo();
        for (File item : startDir.listFiles()) {
            if (item.isDirectory()) {
                result.dirs.add(item);
                result.addAll(recurseDirs(item,regex));
            }else{
                if(item.getName().matches(regex)){
                    result.files.add(item);
                }
            }
        }
        return result;
    }

    //A two-tuple for returning a pair of objects;
    public static class TreeInfo implements Iterable<File>{
        public List<File> files = new ArrayList<File>();
        public List<File> dirs = new ArrayList<File>();
        //The default iterable element is the file list

        public Iterator<File> iterator() {
            return files.iterator();
        }

        void addAll(TreeInfo other) {
            files.addAll(other.files);
        }
        public String toString(){
            return "dirs: "+pformat(dirs)+
                    "\n\nfiles: "+pformat(files);
        }
        public static String pformat(Collection<?> c){
            if(c.size()==0)return "[]";
            StringBuilder result=new StringBuilder("[");
            for (Object elem : c) {
                if(c.size()!=1)
                    result.append("\n ");
                result.append(elem);
            }
            if(c.size()!=1)
                result.append("\n");
            result.append("]");
            return  result.toString();
        }
    }

    public static String getExt(File file) {
        if(file.isDirectory())return "";
        String name=file.getName();
        return name.substring(name.lastIndexOf(".")+1);
    }
}
