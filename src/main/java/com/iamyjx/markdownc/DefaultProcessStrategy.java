package com.iamyjx.markdownc;

import org.markdownj.MarkdownProcessor;

import java.io.File;

/**
 * 对md文件的内容进行操作
 * 个性化策略
 * @autor iamyjx
 * @since 15-12-8
 */
public class DefaultProcessStrategy implements ProcessStrategy {
    private final static String DOCTYPE="<!DOCTYPE html>";
    private final static String PRE_HEAD="<!DOCTYPE html>\n" +
            "<html lang=\"zh-CN\">\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n"+
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"${relativePath}css/main.css\"/>\n";
    private final static String SUF_HEAD="</head>\n" +
            "<body>\n";
    private final static String SUF_HTML="</body>\n" +
            "</html>\n";
    public String beforeProcess(String markdownStr) {
        return markdownStr;
    }

    public String process(String markdownStr) {
        MarkdownProcessor processor = new MarkdownProcessor();
        return processor.markdown(markdownStr);
    }

    public String afterProcess(String markdownStr) {
        //.md改写为.html
        markdownStr=markdownStr.replaceAll("(\\shref=\"[^(http://)(https://)(ftp://)]+.*\\.)md","$1html");
        return PRE_HEAD+SUF_HEAD+markdownStr+SUF_HTML;
    }

    public String getDefaultPath() {
        return "classpath:";
    }

    /**
     * 处理html的相对路径问题
     *
     * @param relativePath 相对于指定根目录，或者默认目录的路径问题
     * @return
     */
    public String processRelativePath(String markdownStr,String relativePath) {
        //处理css文件的相对目录问题
        String separator=File.separator.equals("\\")?"\\\\":File.separator;
        relativePath=relativePath.replaceFirst(separator,"");
        String[] paths=relativePath.split(separator);

        String path="";
        for (int i = 0; i < paths.length - 1; i++) {
            path+="../";
        }

        return markdownStr.replace("${relativePath}",path);
    }
}
