import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JButton;
public class T2RPanel extends JPanel implements MouseListener{
    BufferedImage trainBG;
    int gameState;
    Image im;

    public T2RPanel()
    {
        gameState = 0;
        System.out.println();
        System.out.println("testing");
        
        try {
            trainBG = ImageIO.read(T2RPanel.class.getResource("backgroundImages\\trainBG.png"));
            System.out.println("train");
            trainBG = ImageIO.read(T2RPanel.class.getResource("Images\\trainBG.png"));
        } catch (Exception e) {
            System.out.println("Exception  ");
        }

        addMouseListener(this);

        im = ImageLoader.get("/Images/t2r map.png");

    }

    public void paint(Graphics g)
    {
        g.setColor( Color.white);
       
        if (gameState ==0){    
        g.drawImage(trainBG, 0, 0, getWidth(), getHeight(), null);
        }
        g.drawRect(0, 0, WIDTH, HEIGHT);
    
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
       System.out.println("gamestate " + gameState);
        double x = e.getX();
        double y= e.getY();
        System.out.println("x: " + x/getWidth() + ". y: " + y/getHeight());

        if(gameState == 0)
        {
            //start button            
            if (rectangularInBounds(x, y, 0.07686*getWidth(), 0.23818*getWidth(), 0.11337*getHeight(), 0.26077*getHeight()))
            {
                System.out.println("start game");
                gameState = 1;
            }

              //rules button            
              if (rectangularInBounds(x, y, 0.07686*getWidth(), 0.23818*getWidth(), 0.34921*getHeight(), 0.49887*getHeight()))
              {
                  System.out.println("rules screen");
                  gameState = -1;
              }
            
            
            



        }

        repaint();

        System.out.println("Coords:");
        System.out.println(x + ", " + y);
        System.out.println((x/getWidth()) + " , " + (y/getHeight()));
        System.out.println();
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
