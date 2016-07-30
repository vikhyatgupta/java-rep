package main.com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/** 
author :- Parth and Vikhyat
*/
public class Battleship_OurBoard extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    PositionLabel pl[][];
    final int WIDTH = 10, HEIGHT = 10;
    int ship[] = {0, 1, 1, 1, 1};
    int sh = 4;

    Battleship_OurBoard() {
        pl = new PositionLabel[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                pl[i][j] = new PositionLabel(i, j);
                this.add(pl[i][j]);
            }
        }

        //GridBagLayout gb=new GridBagLayout();
        GridLayout gd = new GridLayout(WIDTH, HEIGHT);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setLayout(gd);
        this.setPreferredSize(new Dimension(WIDTH * 50, HEIGHT * 50));
        this.setOpaque(true);

    }

    public void loss()
    {
       JOptionPane.showMessageDialog(this,"You loss the Game","Lose",JOptionPane.INFORMATION_MESSAGE);
       
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {

        int b = e.getButton();

        System.out.println(b);

        int px = e.getX();
        int py = e.getY();

        int y = px / 50;
        int x = py / 50;

        System.out.println(px + " " + py);
        System.out.println(x + " " + y);

        if (ship[sh] > 0 && sh > 0) {
            if (pl[x][y].canPutShip(b, sh+1)) {

                ship[sh]--;

                pl[x][y].setship(b, sh + 1);

                if (ship[sh] == 0) {
                    sh--;
                }
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//      
//            int px=e.getX();
//            int py=e.getY();
//            
//            int y=px/50;
//            int x=py/50;
//          
//            System.out.println(px+ " "+py);
//            System.out.println(x+ " "+y);
//            
//        try {
//            BufferedImage mid=ImageIO.read(new File("ship_mid.jpg"));
//            BufferedImage hleft=ImageIO.read(new File("ship_h_left.jpg"));
//            BufferedImage hright=ImageIO.read(new File("ship_h_right.jpg"));
//            BufferedImage vtop=ImageIO.read(new File("ship_v_top.jpg"));
//            BufferedImage vbottom=ImageIO.read(new File("ship_v_bottom.jpg"));
//            
//            Image imid= mid.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//            Image ihleft = hleft.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//            Image ihright = hright.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//            Image ivtop = vtop.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//            Image ivbottom = vbottom.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//            
////            pl[x][y].setIcon(new ImageIcon(ivtop));
////            pl[x+1][y].setIcon(new ImageIcon(imid));
////            pl[x+2][y].setIcon(new ImageIcon(imid));
////            pl[x+3][y].setIcon(new ImageIcon(imid));
////            pl[x+4][y].setIcon(new ImageIcon(ivbottom));
//            
//            
//        } catch (Exception ex) {
//            Logger.getLogger(Battleship_OurBoard.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public boolean hit(int x,int y)
    {
        try {
            BufferedImage miss = ImageIO.read(getClass().getResourceAsStream("/images/op_clicked.jpg"));
            Image imiss = miss.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            
            BufferedImage hit = ImageIO.read(getClass().getResourceAsStream("/images/Hit.png"));
            Image ihit = hit.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            
            if(pl[x][y].isShip)
            {
                pl[x][y].setIcon(new ImageIcon(ihit));
            }
            else
            {
                  pl[x][y].setIcon(new ImageIcon(imiss));
            }
        } catch (IOException ex) {
            Logger.getLogger(Battleship_OurBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
        revalidate();
        return pl[x][y].isShip;
    }
    
    class PositionLabel extends JLabel {

        BufferedImage myPicture, clicked;
        int x, y;
        boolean isShip = false;

        public PositionLabel(int x, int y) {

            try {
                this.x = x;
                this.y = y;
                myPicture = ImageIO.read(getClass().getResourceAsStream("/images/water.jpg"));
                Image tmp = myPicture.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                this.setSize(50, 50);
                this.setIcon(new ImageIcon(tmp));
            } catch (IOException ex) {
                Logger.getLogger(Battleship_Oponent.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public boolean canPutShip(int op, int length) {

            boolean ans = true;

            if ((op == 1 && x + length > 9) || (op == 3 && y + length > 9)) {
                ans = false;
            }
            else
            {
            for (int i = 0; i < length; i++) {
                if (pl[op == 1 ? x + i : x][op == 1 ? y : y + i].isShip == true) {
                    ans = false;
                }
            }
            }
            return ans;
        }

        public void setship(int op, int length) {
            try {
                BufferedImage mid = ImageIO.read(getClass().getResourceAsStream("/images/ship_mid.jpg"));
                Image imid = mid.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                
                BufferedImage hleft = ImageIO.read(getClass().getResourceAsStream("/images/ship_h_left.png"));
                Image ihleft = hleft.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                
                BufferedImage hright = ImageIO.read(getClass().getResourceAsStream("/images/ship_h_right.png"));
                Image ihright = hright.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                
                BufferedImage vtop = ImageIO.read(getClass().getResourceAsStream("/images/ship_v_top.png"));
                Image ivtop = vtop.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                
                BufferedImage vbottom = ImageIO.read(getClass().getResourceAsStream("/images/ship_v_bottom.png"));
                Image ivbottom = vbottom.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                
                
                if (op == 1) {
                    int i = 0;

                    
                    this.setIcon(new ImageIcon(ivtop));
                    this.isShip=true;
                    // this.setBackground(Color.BLACK);
                    for (i = 1; i < length-1; i++) {
                        // pl[x+i][y].setBackground(Color.BLACK);
                         
                        pl[x + i][y].setIcon(new ImageIcon(imid));
                        pl[x + i][y].isShip = true;
                    } 
                    pl[x+length-1][y].setIcon(new ImageIcon(ivbottom));
                    pl[x+length-1][y].isShip=true;
//            pl[x][y].setIcon(new ImageIcon(ivtop));
//            pl[x+1][y].setIcon(new ImageIcon(imid));
//            pl[x+2][y].setIcon(new ImageIcon(imid));
//            pl[x+3][y].setIcon(new ImageIcon(imid));
//            pl[x+4][y].setIcon(new ImageIcon(ivbottom));

                } else if (op == 3) {
                    int i;
                    
                    this.setIcon(new ImageIcon(ihleft));
                    this.isShip=true;
                    
                    for (i = 1; i < length-1; i++) {
                        // pl[x+i][y].setBackground(Color.BLACK);
                        pl[x][y + i].setIcon(new ImageIcon(imid));
                        pl[x][y + i].isShip = true;
                    }
                    pl[x][y+length-1].setIcon(new ImageIcon(ihright));
                    pl[x][y+length-1].isShip=true;
                }
                
                this.repaint();
                this.revalidate();
            } catch (Exception ex) {
                Logger.getLogger(Battleship_OurBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
