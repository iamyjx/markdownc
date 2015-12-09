package com.iamyjx.markdownc;

/**
 * 转换markdown标记到html标记
 * @autor iamyjx
 * @since 15-12-8
 */
public interface ProcessStrategy {
    String beforeProcess(String markdownStr);
    String process(String markdownStr);
    String afterProcess(String markdownStr);
    String getDefaultPath();

    /**
     * 处理html的相对路径问题
     * @param relativePath 相对于指定根目录，或者默认目录的路径问题
     * @return
     */
    String processRelativePath(String markdownStr,String relativePath);
}
