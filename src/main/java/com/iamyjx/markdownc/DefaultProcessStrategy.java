package com.iamyjx.markdownc;

import org.markdownj.MarkdownProcessor;

/**
 * @autor iamyjx
 * @since 15-12-8
 */
public class DefaultProcessStrategy implements ProcessStrategy {
    public String beforeProcess(String markdownStr) {
        return markdownStr;
    }

    public String process(String markdownStr) {
        MarkdownProcessor processor = new MarkdownProcessor();
        return processor.markdown(markdownStr);
    }

    public String afterProcess(String markdownStr) {
        return markdownStr;
    }

    public String getDefaultPath() {
        return "classpath:";
    }
}
