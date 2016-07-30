package worddrag;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

/**
 * This class is of type JLabel. It defines all the labels.
 *
 */
public class BoxWord extends JLabel implements MouseMotionListener, MouseListener {

    int mouseX;
    int mouseY;
    int randX, randY;
    Random rand = new Random();

    /**
     * This constructor sets the values of the labels.
     */
    BoxWord(String name, Color c, Font font, int width, int height) {
        randX=rand.nextInt(1000);
        randY=rand.nextInt(400);
        setText(name);
        setOpaque(true);
        setFont(font);
        setBounds(randX,randY,width, height);
        setBackground(c);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            if(JOptionPane.showConfirmDialog(null, "Remove Drag?", "Word Remove Dialog",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                setVisible(false);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setBackground(Color.YELLOW);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setBackground(Color.PINK);
    }

    /**
     * Responsible for the drag and drop operation.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX += e.getX();
        mouseY += e.getY();
        this.setLocation(mouseX, mouseY);
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
    }
}
