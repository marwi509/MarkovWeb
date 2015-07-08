package markov.gui;

import com.google.common.base.Stopwatch;
import markov.lyricsGenerator.LyricsFacade;
import markov.util.HashSetTable;
import markov.util.io.FileReader;
import markov.util.io.FileStringWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class MarkovMainFrame extends JFrame{

	Vector<String> theFiles = new Vector<>();
	JList<String> list;
	JRadioButton rdbtnCharacter;
	JRadioButton rdbtnWord;
	JLabel lblNewLabel = new JLabel();
	JSlider slider;
	String standardDirectory = "";
	String theFilesString = "";
	
	public MarkovMainFrame() {
		if(System.getProperty("os.name").equalsIgnoreCase("Linux"))
			standardDirectory = System.getenv("HOME") + "/Dokument/Mars Volta/";
		
		FileReader theFileReader = new FileReader();
		theFileReader.readFile(standardDirectory + "files.collection");
		String theSavedFiles = theFileReader.getContent();
		String[] Lines = theSavedFiles.split("\n");
        Collections.addAll(theFiles, Lines);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		sourceTextField = new JTextField(standardDirectory);
		panel.add(sourceTextField);
		sourceTextField.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(arg0 -> {
            theFiles.add(sourceTextField.getText());
            theFilesString = "";
            for (String theFile : theFiles) {
                theFilesString += theFile + "\n";
            }
            FileStringWriter.toFile(theFilesString, standardDirectory + "files.collection");
            list.setListData(theFiles);
        });
		
		panel.add(btnAdd);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		outputTextField = new JTextField(standardDirectory + "output/guisong.txt");
		panel_1.add(outputTextField);
		outputTextField.setColumns(10);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(e -> {
            LyricsFacade theFacade = new LyricsFacade(new Random(), new HashSetTable<>());
            if(rdbtnCharacter.isSelected())
            {
                theFacade.setCharacterSequenceLength(slider.getValue());
                theFacade.setUseCharacter();
            }
            else
            {
                theFacade.setWordSequenceLength(slider.getValue());
                theFacade.setUseWord();
            }
            Stopwatch sw = Stopwatch.createStarted();
            int[] selected = list.getSelectedIndices();
            for (int aSelected : selected) {
                theFacade.addSong(theFiles.get(aSelected));
            }

            theFacade.generateSong();
            long timeElapsed = sw.elapsed(TimeUnit.MILLISECONDS);
            System.out.println("Time taken = " + (double)timeElapsed / 1000.0 +" seconds");
            theFacade.toFile(outputTextField.getText());

        });
		panel_1.add(btnGenerate);
		
		list = new JList<>();
		list.setListData(theFiles);
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_DELETE)
				{
					int[] index = list.getSelectedIndices();
                    for (int anIndex : index) {
                        theFiles.remove(anIndex);
                    }
					theFilesString = "";
                    for (String theFile : theFiles) {
                        theFilesString += theFile + "\n";
                    }
					FileStringWriter.toFile(theFilesString, standardDirectory + "files.collection");
					list.setListData(theFiles);
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(list);
		
		
		
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		scrollPane.setRowHeaderView(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.NORTH);
		
		rdbtnCharacter = new JRadioButton("Character");
		rdbtnCharacter.setSelected(true);
		
		
		rdbtnWord = new JRadioButton("Word");
		
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rdbtnWord);
		btnGroup.add(rdbtnCharacter);
		panel_3.add(rdbtnCharacter);
		panel_3.add(rdbtnWord);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.SOUTH);
		
		slider = new JSlider();
		slider.addChangeListener(arg0 -> lblNewLabel.setText(""+slider.getValue()));
		slider.setValue(4);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setMaximum(20);
		panel_4.add(slider);
		
		lblNewLabel = new JLabel(""+slider.getValue());
		panel_4.add(lblNewLabel);
		
		this.setMinimumSize(new Dimension(600,300));
		this.setVisible(true);
		this.setEnabled(true);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4805740937012221389L;
	private JTextField sourceTextField;
	private JTextField outputTextField;

}
