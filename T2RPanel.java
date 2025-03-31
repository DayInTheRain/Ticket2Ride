import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
public class T2RPanel extends JPanel implements MouseListener{
    BufferedImage trainBG;

    public T2RPanel()
    {
        System.out.println("testing");
        try {
            trainBG = ImageIO.read(T2RPanel.class.getResource("backgroundImages\\trainBG.png"));
        } catch (Exception e) {
            System.out.println("Exception  ");
        }
    }

    public void paint(Graphics g)
    {
            g.drawImage(trainBG, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
}
