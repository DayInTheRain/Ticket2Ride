import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JButton;
import java.awt.FlowLayout;
public class T2RPanel extends JPanel implements MouseListener{
    BufferedImage trainBG;
    int gameState;

    public T2RPanel()
    {
        gameState = 0;
        System.out.println();
        System.out.println("testing");
        
        try {
            trainBG = ImageIO.read(T2RPanel.class.getResource("backgroundImages\\trainBG.png"));
            System.out.println("train");
        } catch (Exception e) {
            System.out.println("Exception  ");
        }

        addMouseListener(this);
    }

    public void paint(Graphics g)
    {
            g.drawImage(trainBG, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       
        double x = e.getX();
        double y= e.getY();
        System.out.println("x: " + x/getWidth() + ". y: " + y/getHeight());

        if(gameState == 0)
        {
            if (rectangularInBounds(x, y, 0.07686*getWidth(), 0.23818*getWidth(), 0.11337*getHeight(), 0.26077*getHeight()))
            {
                System.out.println("start game");
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

    public boolean rectangularInBounds(double x, double y, double minX, double maxX, double minY, double maxY)
    {
        if ( minX < x && x < maxX)
        {
            if (minY < y && y < maxY)
            {
                return true;
            }
        }
        return false;


    }
}
