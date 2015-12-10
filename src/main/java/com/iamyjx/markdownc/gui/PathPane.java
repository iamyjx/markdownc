package com.iamyjx.markdownc.gui;

import javax.swing.*;
import java.util.Observable;

/**
 * @autor iamyjx
 * @since 15-12-9
 */
public class PathPane extends Observable {
    //TODO ....
    private final JScrollPane pathPane = new JScrollPane();
    private final JLabel previewLabel = new JLabel();
    private final JButton selectBtn=new JButton("选择");

    public PathPane() {
        previewLabel.setHorizontalAlignment(JButton.LEFT);
        pathPane.getViewport().add(selectBtn, null);
        pathPane.getViewport().add(previewLabel, null);

    }
    public JScrollPane get() {
        return pathPane;
    }
}
