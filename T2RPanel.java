import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
public class T2RPanel extends JPanel implements MouseListener{
    Image trainBG;
    Image t2r_map;
    int gameState;
    int turnState;
    JButton startbutton;
    JButton rulesbutton;

    public T2RPanel()
    {
        gameState = 0;
        turnState = 0;
        System.out.println();
        System.out.println("testing");
       
        addMouseListener(this);
       

        trainBG = ImageLoader.get("/Images/trainBG.png");
        t2r_map = ImageLoader.get("/Images/t2r map.png");
        //city generator


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
           if (turnState ==0)
        {
            beginTurnUI(g);
       
        }

            if (turnState ==1)
            {
                claimRouteUI(g);
            }
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
            if (gameState == 0){        
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

        
            



        }
       
        if (gameState == 1)
        {
                if (turnState ==0)
                {
                    if (rectangularInBounds(x, y, (int)(0.70711*getWidth()), (int)(0.80711*getWidth()), (int)(0.500000*getHeight()),  (int)(0.550000*getHeight())))
                        {
                         System.out.println("claim route was clicked");
                         turnState = 1;
                    
                        }
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

    public void beginTurnUI(Graphics g)
    {

        g.setColor(Color.black);
            
          
           
        g.drawString("Points:", (int) (0.76196*getWidth()), (int) (0.17630*getHeight()));

        g.drawString("Points:", (int) (0.76196*getWidth()), (int) (0.25239*getHeight()));

        g.drawString("Points:", (int) (0.76196*getWidth()), (int) (0.32848*getHeight()));


        //beginningOfTurnDisplay(g);
   
    //g.drawImage(t2r_map, 0, 0, getWidth(), getHeight(), null);
    //Font font = new Font("Aerial",Font.BOLD,20);
        g.setColor(Color.orange);
        g.fillRoundRect((int)(0.70711*getWidth()), (int)(0.500000*getHeight()),(int)( 0.1*getWidth()), (int)(0.05*getHeight()), (int)(0.01*getWidth()), (int)(0.1*getWidth()));
        g.setColor(Color.black);
        g.drawString("claim route", (int) (0.73711*getWidth() ), (int) (0.5227272727272727*getHeight()));
       
        g.setColor(Color.orange);
        g.fillRoundRect((int)(0.82711*getWidth()), (int)(0.500000*getHeight()),(int)( 0.1*getWidth()), (int)(0.05*getHeight()), (int)(0.01*getWidth()), (int)(0.1*getWidth()));
        g.setColor(Color.black);
        g.drawString("pick train card", (int) (0.82711*getWidth() ), (int) (0.5227272727272727*getHeight()));
               
        g.setColor(Color.orange);
        g.fillRoundRect((int)(0.70711*getWidth()), (int)(0.620000*getHeight()),(int)( 0.1*getWidth()), (int)(0.05*getHeight()), (int)(0.01*getWidth()), (int)(0.1*getWidth()));
        g.setColor(Color.black);
        g.drawString("pick ticket card", (int) (0.73711*getWidth() ), (int) (0.640*getHeight()));
       

        
        g.setColor(Color.orange);
        g.fillRoundRect((int)(0.82711*getWidth()), (int)(0.620000*getHeight()),(int)( 0.1*getWidth()), (int)(0.05*getHeight()), (int)(0.01*getWidth()), (int)(0.1*getWidth()));
        g.setColor(Color.black);
        g.drawString("build a station", (int) (0.82711*getWidth() ), (int) (0.640*getHeight()));
               




    }

    public void claimRouteUI(Graphics g)
    {
        g.setColor(Color.black);
        g.drawString("Claim Route", (int) (0.65942*getWidth()), (int) (0.04785*getHeight()));

        g.drawString("Click two cities to claim the route that", (int) (0.65942*getWidth()), (int) (0.09785*getHeight()) );
        g.drawString("connects them", (int) (0.65942*getWidth()), (int) (0.1205*getHeight()) );

        g.drawString("City 1:", (int) (0.65942*getWidth()), (int) (0.1605*getHeight()) );

         g.drawString("City 2:", (int) (0.65942*getWidth()), (int) (0.205*getHeight()) );
    }

   
}
