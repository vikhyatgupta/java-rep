package main.com;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * author :- Parth and Vikhyat
 */


public class Battleship_Oponent extends JPanel {

    PositionButton gb[][];
    final int WIDTH = 10, HEIGHT = 10;

    public void win()
    {
       JOptionPane.showMessageDialog(this,"You Won the Game","Win",JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    Battleship_Oponent() {
        GridLayout gd = new GridLayout(WIDTH, HEIGHT);
        gb = new PositionButton[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                gb[i][j] = new PositionButton(i, j);
                this.add(gb[i][j]);
            }
        }

        this.setLayout(gd);
        this.setPreferredSize(new Dimension(500, 500));
        this.setOpaque(true);
    }

    public void hit(boolean h, int x, int y) {
        try {
            BufferedImage miss = ImageIO.read(getClass().getResourceAsStream("/images/op_clicked.jpg"));
            Image imiss = miss.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            BufferedImage hit = ImageIO.read(getClass().getResourceAsStream("/images/Hit.png"));
            Image ihit = hit.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            if (h) {
                gb[x][y].setIcon(new ImageIcon(ihit));
                gb[x][y].setEnabled(false);
            } else {
                gb[x][y].setIcon(new ImageIcon(imiss));
                gb[x][y].setEnabled(false);
            }
        } catch (IOException ex) {
            Logger.getLogger(Battleship_OurBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
        revalidate();
    }

    class PositionButton extends JButton implements ActionListener {

        BufferedImage myPicture, clicked;
        int xlocation, ylocation;

        public PositionButton(int x, int y) {
            xlocation = x;
            ylocation = y;
            try {
                myPicture = ImageIO.read(getClass().getResourceAsStream("/images/water.jpg"));
                clicked = ImageIO.read(getClass().getResourceAsStream("/images/op_clicked.jpg"));

                Image tmp = myPicture.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                this.setSize(50, 50);
                addActionListener(this);
                this.setIcon(new ImageIcon(tmp));
            } catch (IOException ex) {
                Logger.getLogger(Battleship_Oponent.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Batleship_frame.server == null) {
                Batleship_frame.client.hit(xlocation, ylocation);
            } else {
                Batleship_frame.server.hit(xlocation, ylocation);
            }
        }

    }

}
