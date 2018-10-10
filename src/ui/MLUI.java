package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import neural.AssociativeNetwork;
import neural.MLNetwork;

import javax.swing.JButton;

import res.AppData;
import res.LearningData;
import acm.graphics.GCanvas;

public class MLUI extends JFrame {
	
	private JPanel contentPanel;
	private JButton button_learn;
	private JButton button_refresh;
	private JButton button_test;
	private JButton button_gen;
	private GCanvas screen;
	private Grid grid;
	
	private int count = 0;
	
	public MLUI() {
		super("Pattern Detector");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		buildGUI();
		registerListeners();
		pack();
		setVisible(true);
	}
	
	private void buildGUI() {
		contentPanel = new JPanel(new MigLayout("fill","[grow][grow][grow]","[][]"));
		
		button_learn = new JButton("Learn");
		button_refresh = new JButton("Refresh");
		button_test = new JButton("Test");
		button_gen = new JButton("Generate code");
		grid = new Grid();
		screen = new GCanvas();
		screen.setBackground(getBackground());
		
		//contentPanel.add(button_learn);
		contentPanel.add(button_refresh);
		contentPanel.add(button_test,"wrap");
		//contentPanel.add(button_gen,"wrap");
		contentPanel.add(screen,"spanx,spany,grow");
		setContentPane(contentPanel);
	}
	
	public void registerListeners() {
		screen.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				screen.remove(grid);
				screen.add(grid,(screen.getWidth()-grid.getWidth())/2,(screen.getHeight()-grid.getHeight())/2);
			}
		});
		
		button_refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.refresh();
			}
		});
		
		button_learn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		button_test.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int[] vec = grid.vector();
				for(int i=0; i<vec.length; i++) {
					if(vec[i] < 0) vec[i] = 0;
				}
				int[] op = MLNetwork.test_output(vec);
				String str;
				if(op[0] == 0 && op[1] == 0) {
					str = "0";
				}
				else if(op[0] == 0 && op[1] == 1) {
					str = "1";
				}
				else if(op[0] == 1 && op[1] == 0) {
					str = "2";
				}
				else {
					str = "3";
				}
				JOptionPane.showMessageDialog(null, "Recognized character: "+str);
			}
		});
		
		button_gen.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
}

