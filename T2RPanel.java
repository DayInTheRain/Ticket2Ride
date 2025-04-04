import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
public class T2RPanel extends JPanel implements MouseListener{
    Image trainBG;
    Image t2r_map;
    int gameState;

    JButton startbutton;
    JButton rulesbutton;

    public T2RPanel()
    {
        gameState = 0;
        System.out.println();
        System.out.println("testing");
       
        addMouseListener(this);
       

        trainBG = ImageLoader.get("/Images/trainBG.png");
        t2r_map = ImageLoader.get("/Images/t2r map.png");

    }

    public void paint(Graphics g)
    {
        super.paint(g);
       
        if (gameState ==0){    
        g.drawImage(trainBG, 0, 0, getWidth(), getHeight(), null);
        }
       
            //g.drawImage(trainBG, 0, 0, getWidth(), getHeight(), null);
        else if (gameState == 1){
            g.drawImage(t2r_map, 0, 0, (int)(getWidth() * 0.6), (int)(getHeight()  * 0.7) ,null);
            g.setColor(Color.black);
            
            System.out.println("Width__________" + getWidth());
           
            g.drawString("Points:", (int) (0.76196*getWidth()), (int) (0.17630*getHeight()));

            g.drawString("Points:", (int) (0.76196*getWidth()), (int) (0.25239*getHeight()));

            g.drawString("Points:", (int) (0.76196*getWidth()), (int) (0.32848*getHeight()));


            //beginningOfTurnDisplay(g);
       
    	//g.drawImage(t2r_map, 0, 0, getWidth(), getHeight(), null);
    	//Font font = new Font("Aerial",Font.BOLD,20);
       
        
        
           
      
        }















    }//end of paint

   
    @Override
    public void mouseClicked(MouseEvent e) {
    	
       System.out.println("gamestate " + gameState);
        double x = e.getX();
        double y= e.getY();
        System.out.println("Coords:");
        System.out.println(x + ", " + y);
        System.out.println((x/getWidth()) + " , " + (y/getHeight()));
        System.out.println();
        

       if(gameState == 0)
        {
            //start button            
            if (rectangularInBounds(x, y, 0.13846153846*getWidth(),0.32307692307*getWidth(), 0.11483*getHeight(), 0.264354*getHeight()))
            {
                System.out.println("start game");
                gameState = 1;
            }

              //rules button            
              if (rectangularInBounds(x, y, 0.67692307692*getWidth(), 0.86153846153*getWidth(), 0.45714285714*getHeight(),  0.68571428571*getHeight()))
              {
                  System.out.println("rules screen");
                  gameState = -1;
              }
            
            
            



        }
       
    
        repaint();
    }//mouse clicked

    // public void beginningOfTurnDisplay(Graphics g)
    // {
    //     g.setColor(Color.black);
    //     System.out.println("points---------------------------------------------------------------");
    //     g.drawString("Points aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa: ", (int) 0.76196*getWidth(), (int) 0.17630*getHeight());
        




    // }
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
