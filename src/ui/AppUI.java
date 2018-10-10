package ui;

import acm.graphics.GCanvas;
import net.miginfocom.swing.MigLayout;
import neural.AssociativeNetwork;
import res.AppData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AppUI extends JFrame {

    private JPanel contentPanel;

    private JButton button_learn;
    private JButton button_refresh;
    private JButton button_test;
    private JButton button_gen;
    private GCanvas screen;
    private Grid grid;

    private int count = 0;

    public AppUI() {
        super("Pattern Detector");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildGUI();
        registerListeners();
        pack();
        setVisible(true);
    }

    private void buildGUI() {
        contentPanel = new JPanel(new MigLayout("fill", "[grow][grow][grow]", "[][]"));

        button_learn = new JButton("Learn");
        button_refresh = new JButton("Refresh");
        button_test = new JButton("Test");
        button_gen = new JButton("Generate code");
        grid = new Grid();
        screen = new GCanvas();
        screen.setBackground(getBackground());

        contentPanel.add(button_learn);
        contentPanel.add(button_refresh);
        contentPanel.add(button_test);
        contentPanel.add(button_gen, "wrap");
        contentPanel.add(screen, "spanx,spany,grow");
        setContentPane(contentPanel);
    }

    public void registerListeners() {
        screen.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                screen.remove(grid);
                screen.add(grid, (screen.getWidth() - grid.getWidth()) / 2, (screen.getHeight() - grid.getHeight()) / 2);
            }
        });

        button_refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                grid.refresh();
            }
        });

        button_learn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] vec = grid.vector();
                AssociativeNetwork.learn(vec);
            }
        });

        button_test.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int[] vec = grid.vector();
                grid.displayVector(AssociativeNetwork.output(vec));
            }
        });

        button_gen.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int[] vec = grid.vector();
                StringBuilder vecString = new StringBuilder("INPUT[" + count++ + "] = new int[]{");
                for (int i = 0; i < vec.length; i++) {
                    if (i % AppData.GRID_COLS == 0) {
                        vecString.append("\n");
                    }
                    vecString.append(vec[i] > 0 ? "1" : "0");
                    if (i < vec.length - 1) vecString.append(", ");
                }
                vecString.append("};");
                System.out.println(vecString);
                String arraylist = "AppData.LEARNING_DATA";
                String ins = arraylist + ".add(";
            }
        });
    }

    public static void main(String args[]) {
        new AppUI();
    }
}
