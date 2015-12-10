package com.iamyjx.markdownc.gui;

import javax.swing.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;

/**
 * @autor iamyjx
 * @since 15-12-9
 */
public class LogPane extends Observable {
    private final JScrollPane logPane = new JScrollPane();
    private final JTextArea logTextArea = new JTextArea();

    public LogPane() {

        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        logPane.getViewport().add(logTextArea, null);

        //把System.out.println()输出的东西 写在JTextArea里面
//        MyPrintStream out = null;
//        try {
//            out = new MyPrintStream(logTextArea);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.setOut(out);

        //output logs to a JTextArea using Log4j2
        JTextAreaAppender.addTextArea(logTextArea);
    }

    public JScrollPane get() {
        return logPane;
    }


    public void appendLog(String log) {
        logTextArea.setText(logTextArea.getText()+"\n"+log);
    }
}
//class MyPrintStream extends PrintStream {
//    private JTextArea jta;
//    private static final OutputStream DUMMY_OUT = new ByteArrayOutputStream(0);
//
//    public MyPrintStream(JTextArea jta) throws FileNotFoundException {
//        super(DUMMY_OUT);
//        this.jta = jta;
//    }
//
//    public void print(String s) {
//        if (s == null) {
//            s = "null";
//        }
//        jta.append(s);
//    }
//}
