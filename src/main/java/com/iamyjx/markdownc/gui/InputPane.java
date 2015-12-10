package com.iamyjx.markdownc.gui;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

/**
 * Scrolled text area where will be inputed markdown data to be converted.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class InputPane extends Observable {
	private final JScrollPane inputPane = new JScrollPane();
	private final JTextArea inputTextArea = new JTextArea();

	/**
	 * Creates the text area and add a key listener to call observer every time a key is released.
	 */
	public InputPane() {
		inputPane.getViewport().add(inputTextArea, null);
		inputTextArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				setChanged();
				notifyObservers(inputTextArea.getText());
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	/**
	 * Returns the JScrollPane object.
	 *
	 * @return the JScrollPane object.
	 */
	public JScrollPane get() {
		return inputPane;
	}
}
