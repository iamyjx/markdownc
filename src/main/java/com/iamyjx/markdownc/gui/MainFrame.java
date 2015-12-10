package com.iamyjx.markdownc.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Provides the main window of the application.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MainFrame {
	private final JFrame mainFrame = new JFrame("Markdown converter");
	private final MenuBar menu = new MenuBar();
	private final MainPanel panel = new MainPanel();

	/**
	 * Creates the main window and makes it visible.
	 */
	public MainFrame() {
		Dimension frameSize = new Dimension(640, 440);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(frameSize);
		mainFrame.setMinimumSize(frameSize);

		mainFrame.setJMenuBar(menu.get());
		mainFrame.getContentPane().add(panel.get());
		mainFrame.setLocationRelativeTo(null); // Center main frame
		mainFrame.setVisible(true);
	}
}
