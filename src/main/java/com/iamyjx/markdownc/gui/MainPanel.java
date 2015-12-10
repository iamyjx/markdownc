package com.iamyjx.markdownc.gui;

import com.iamyjx.markdownc.*;
import com.iamyjx.markdownc.util.StringUtils;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

/**
 * Provides the main panel of the application, which will contain all the components.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MainPanel {
    private Log logger= LogFactory.getLog(this.getClass());
    private String path="";
    private String runPath="";

	private final MigLayout layout = new MigLayout(
            "",      // Layout Constraints
            "[][]10[grow]", // column constraints
            "[]10[grow]" // Row constraints
            );
	private final JPanel mainPanel = new JPanel(layout);


//    private final PathPane pathTextArea=new PathPane();
    private final LogPane log=new LogPane();
    private final JTextArea pathTextArea = new JTextArea("");
    private final JButton selSourceBtn = new JButton( "选择源");
    private final JButton convertBtn = new JButton( "转换");



	/**
	 * Creates the main panel, adding observer to the input and building the GUI.
	 */
	public MainPanel() {
        pathTextArea.setEditable(false);
        pathTextArea.setLineWrap(true);
        pathTextArea.setWrapStyleWord(true);
//		// Add observer

//		// Build GUI
        mainPanel.add(convertBtn);
        mainPanel.add(selSourceBtn);
        mainPanel.add(pathTextArea,"wrap,grow");
        mainPanel.add(log.get(),"span, growx,growy");
        addListener();
    }
    private void addListener(){
        convertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (StringUtils.isEmpty(path)) {
                    if (logger.isInfoEnabled()) {
                        logger.info("未选择源");
                    }
                }else {
                     convert(path);
                }

            }
        });
        selSourceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                if (chooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
                    path=chooser.getSelectedFile().getAbsolutePath();
                    pathTextArea.setText(path);
                    if (logger.isInfoEnabled()) {
                        logger.info((chooser.getSelectedFile().isDirectory()?"已选定源文件夹：":"已选定源文件")+path);
                    }
                }
            }
        });
    }
    private void convert(String path){
        FileHelper fileHelper=new DefaultFileHelper();
        ProcessStrategy strategy=new DefaultProcessStrategy();
        String[] extensions=new String[]{"md"};

        Converter converter=new DefaultConverter(fileHelper,strategy,extensions);
        converter.convert(path, path);
    }

    /**
	 * Returns the JPanel object.
	 *
	 * @return the JPanel object.
	 */
	public JPanel get() {
		return mainPanel;
	}
}
