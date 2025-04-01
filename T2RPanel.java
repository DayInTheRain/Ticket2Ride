import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
public class T2RPanel extends JPanel implements MouseListener{
    BufferedImage trainBG;
    Image im;

    public T2RPanel()
    {
        System.out.println("testing");
        try {
            trainBG = ImageIO.read(T2RPanel.class.getResource("Images\\trainBG.png"));
        } catch (Exception e) {
            System.out.println("Exception  ");
        }

        im = ImageLoader.get("/Images/t2r map.png");

    }

    public void paint(Graphics g)
    {
            //g.drawImage(trainBG, 0, 0, getWidth(), getHeight(), null);

            g.drawImage(im, 0, 0, getWidth() - 405, getHeight() - 190 ,null);

            g.setColor(Color.cyan);
            for(int x = 0; x < getWidth(); x += 25){
                    g.drawLine(x, 0, x, getHeight());
            }

            g.setColor(Color.magenta);
            for(int y = 0; y < getHeight(); y += 25){
                g.drawLine(0, y, getWidth(), y);
        }


    }//end of paint

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
