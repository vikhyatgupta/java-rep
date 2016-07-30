/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.com;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jdk.nashorn.internal.ir.Flags;

/**
 * author :- Parth and Vikhyat
 */
public class Batleship_frame extends JFrame implements ActionListener {

    private JPanel p;
    public static Battleship_Oponent op;
    public static Battleship_OurBoard our;
    private JMenuBar jmenubar;
    private JMenu jmenu;
    private JMenuItem ext, s_ply, c_ply;
    public static Battleship_Socket_Server server = null;
    public static Battleship_Socket_Client client = null;

    public Batleship_frame() {
        setTitle("Battle Game");

        p = new JPanel();
        p.setLayout(new FlowLayout());
        jmenubar = new JMenuBar();
        jmenu = new JMenu("Select Option");
        jmenubar.add(jmenu);

        s_ply = new JMenuItem("Start the Game as Server");
        jmenu.add(s_ply);
        s_ply.addActionListener(this);

        c_ply = new JMenuItem("Connect to the Game");
        jmenu.add(c_ply);
        c_ply.addActionListener(this);

        ext = new JMenuItem("Quit");
        jmenu.add(ext);
        ext.addActionListener(this);

        this.setJMenuBar(jmenubar);

        our = new Battleship_OurBoard();
        op = new Battleship_Oponent();
      //  JLabel pl=new JLabel("Player's Board");
        //  JLabel ol=new JLabel("Oponent's Board");
        // pl.setSize(500,20);
        //   ol.setSize(500,20);

      //  p.add(pl);
        //  p.add(ol);
        p.add(our);
        p.add(op);

        this.add(p);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        JMenuItem src = (JMenuItem) ae.getSource();

        Battleship_OurBoard.PositionLabel[][] pl=our.pl;
        
        for(int i=0;i<pl.length;i++)
        {
           for(int j=0;j<pl[i].length;j++)
           {
               System.out.print(pl[i][j].isShip+" ");
           }
           System.out.println();
        }
        
        if (src == s_ply) {
            server = new Battleship_Socket_Server(9090);
            server.start();
        } else if (src == c_ply) {
            JTextField xField = new JTextField(15);
            JTextField yField = new JTextField(4);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("IP Adress:"));
            myPanel.add(xField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Port:"));
            myPanel.add(yField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Please enter Ip Adress and port No:", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                System.out.println("IP value: " + xField.getText());
                System.out.println("Port value: " + yField.getText());
                client = new Battleship_Socket_Client(xField.getText(),Integer.parseInt(yField.getText()));
                client.start();
            }

            
        } else if (src == ext) {
            if(server!=null)
            {
                server.close();
            }
            else
            {
                client.close();
            }
            System.exit(0); // closing application on clicking "Quit"
        }
    }

}
