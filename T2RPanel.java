import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JButton;
public class T2RPanel extends JPanel implements MouseListener, ActionListener{
    BufferedImage trainBG;
    int gameState;
    Image im;
    JButton startbutton;
    JButton rulesbutton;

    public T2RPanel()
    {
        gameState = 0;
        System.out.println();
        System.out.println("testing");
        
        // try {
        //     trainBG = ImageIO.read(T2RPanel.class.getResource("backgroundImages\\start screen.jpg"));
        //     System.out.println("train");
        //     trainBG = ImageIO.read(T2RPanel.class.getResource("Images\\trainBG.png"));
        // } catch (Exception e) {
        //     System.out.println("Exception  ");
        // }

        addMouseListener(this);
       

        im = ImageLoader.get("/Images/trainBG.png");

    }

    public void paint(Graphics g)
    {
        super.paint(g);
       
        if (gameState ==0){    
        g.drawImage(im, 0, 0, getWidth(), getHeight(), null);
        }
       
            //g.drawImage(trainBG, 0, 0, getWidth(), getHeight(), null);
        else if (gameState == 1){
            g.drawImage(im, 0, 0, getWidth() - 405, getHeight() - 190 ,null);
            g.setColor(Color.black);
            
            System.out.println("Width__________" + getWidth());
           
            g.drawString("Points:", (int) (0.76196*getWidth()), (int) (0.17630*getHeight()));

            g.drawString("Points:", (int) (0.76196*getWidth()), (int) (0.25239*getHeight()));

            g.drawString("Points:", (int) (0.76196*getWidth()), (int) (0.32848*getHeight()));


            //beginningOfTurnDisplay(g);
       
    	g.drawImage(im, 0, 0, getWidth(), getHeight(), null);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("start"));{
		System.out.println("start");}
		if(e.getActionCommand().equals("rules"));{
		System.out.println("rules");}
		
	}
}
