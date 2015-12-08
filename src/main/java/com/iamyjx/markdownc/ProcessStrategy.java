package com.iamyjx.markdownc;

/**
 * @autor iamyjx
 * @since 15-12-8
 */
public interface ProcessStrategy {
    String beforeProcess(String markdownStr);
    String process(String markdownStr);
    String afterProcess(String markdownStr);
    String getDefaultPath();
}
