package com.iamyjx.markdownc;



import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @autor iamyjx
 * @since 15-11-30
 */
public class App {
    //TODO 如果文件存在，根据修改时间，决定是否重新解析生成
    //TODO 参数自定义
    //TODO gui
    //TODO gui还是先不写，先写命令行参数输入

    public static void main(String[] args) throws IOException, URISyntaxException {
        MarkdownConverter converter=new MarkdownConverter(new DefaultStrategy(),"md");
        converter.start(new String[]{"classpath:wiki"},"");

    }

}
