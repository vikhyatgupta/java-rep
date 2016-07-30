package worddrag;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * This class represents a program to move labels around a container.
 *
 * @author Vikhyat Gupta (0985873)
 * @version NetBeans IDE 7.4
 */
public class WordDrag {

    JPanel panel = new JPanel();

    ArrayList<BoxWord> label;
    ArrayList<String> label_names;
    int width, height;
    JFrame frame;
    JMenuBar menuBar;
    JMenuItem addWord;
    JMenuItem quit;

    /**
     * This constructor defines and initializes all the widgets used in the
     * application. It is related to the user interface of the GUI
     */
    public WordDrag() {

        frame = new JFrame();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        //Creating menu bar
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Select Option");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);
        addWord = new JMenuItem("Add Word", KeyEvent.VK_A);
        quit = new JMenuItem("Quit", KeyEvent.VK_A);

        MenuItemListener menuItemListener = new MenuItemListener();

        addWord.addActionListener(menuItemListener);
        quit.addActionListener(menuItemListener);

        menu.add(addWord);
        menu.add(quit);
        frame.setJMenuBar(menuBar);

        //Initializing the array list which displays the label names.
        label_names = new ArrayList<String>();
        label_names.add("Java");
        label_names.add("Red");
        label_names.add("Drag");
        label_names.add("Face");
        label_names.add("Java");
        label_names.add("Sad");
        label_names.add("Cloudy");
        label_names.add("Very");
        label_names.add("Great");
        label_names.add("Hands");
        label_names.add("Through");
        label_names.add("WSAD 5.1");
        label_names.add("Word");
        label_names.add("Skippy");
        label_names.add("Hands");
        label_names.add("Run");
        label_names.add("J2EE");

        label = new ArrayList<BoxWord>();

        for (int i = 0; i < label_names.size(); ++i) {
            label.add(new BoxWord(label_names.get(i), Color.PINK, Font.decode(Font.SANS_SERIF), 50, 30));
            panel.add(label.get(i));
        }
       
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This is the event handling class. It is called when a menu button is
     * pressed
     *
     */
    class MenuItemListener implements ActionListener {

        /**
         * @param e The ActionEvent object which holds the data about the event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addWord) {
                String newLabel = (String) JOptionPane.showInputDialog(null, "New Word Entry", "Enter new word", JOptionPane.QUESTION_MESSAGE);
                label.add(new BoxWord(newLabel, Color.PINK, Font.decode(Font.SANS_SERIF), 50, 30));
                label_names.add(newLabel);
                label.get(label_names.size() - 1).addMouseMotionListener(label.get(label_names.size() - 1));
                panel.add(label.get(label_names.size() - 1));
                SwingUtilities.updateComponentTreeUI(frame);
            } else if (e.getSource() == quit) {
                frame.setVisible(false);
                frame.dispose();
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WordDrag();
            }
        });
    }
}
