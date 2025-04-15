import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
public class T2RPanel extends JPanel implements MouseListener{
    Image trainBG;
    Image t2r_map;
    Image rules1;
    Image rules2;
    Image station, train;
    int gameState;
    int turnState;
    
    
    JButton startbutton;
    JButton rulesbutton;
    Game gameAccess;  

    //claimRoute IVs
    int claimRouteState;
    City city1;
    City city2;

    //pickTicket IVs
    int pickTicketState;
    Ticket DestinationTicket1;
    Ticket DestinationTicket2;
    Ticket DestinationTicket3;
    Boolean viewingTickets;
    ArrayList<Ticket> ticketsOnScreen;
    boolean DestinationTicket1Selected;
    boolean DestinationTicket2Selected;
    boolean DestinationTicket3Selected;


    Font origionalFont;

    public T2RPanel()
    {
        gameAccess = new Game();
        gameState = 0;
        turnState = 0;

        ticketsOnScreen = new ArrayList<>();
        generateTicketsOnScreen();

        viewingTickets = false;
        origionalFont = new Font("Monospaced", Font.PLAIN, Math.abs((int)( 0.18947416762342135*getHeight() -  0.16991963260619977*getHeight()))); //sets the standard font for printing things
        claimRouteState = 0;
        pickTicketState = 0;
        System.out.println();
        System.out.println("testing");
       
        addMouseListener(this);
       

        trainBG = ImageLoader.get("/Images/trainBG.png");
        t2r_map = ImageLoader.get("/Images/t2r map.png");
        rules1 = ImageLoader.get("/Images/rules1.jpg");
        rules2 = ImageLoader.get("/Images/rules2.jpg");
        station = ImageLoader.get("/Images/station.png");
        train = ImageLoader.get("/Images/train.png");
        city1 = null;
        city2 = null;
        DestinationTicket1 = null;
        DestinationTicket2 = null;
        DestinationTicket3 = null;
        
        boolean DestinationTicket1Selected = false;
        boolean DestinationTicket2Selected = false;
        boolean DestinationTicket3Selected = false;
    }

    public void generateTicketsOnScreen(){
        ticketsOnScreen.clear();
        ticketsOnScreen.add(gameAccess.drawLongTicket());
        ticketsOnScreen.add(gameAccess.drawTicket());
        ticketsOnScreen.add(gameAccess.drawTicket());
        ticketsOnScreen.add(gameAccess.drawTicket());
    }

    public void paint(Graphics g)
    {
        super.paint(g);
       
        if(viewingTickets){
            paintViewingTickets(g);
        } else { //basically saying if it's not showing the tickets print other stuff cause the tickets rnt included in the gamestate
            if (gameState ==0){    
                g.drawImage(trainBG, 0, 0, getWidth(), getHeight(), null);
            }
                
            else if(gameState == -1) {
                g.drawImage(rules1, 0, 0, getWidth(), getHeight(), null);
            }
            else if(gameState == -2) {
                g.drawImage(rules2, 0, 0, getWidth(), getHeight(), null);
            }
            
            
            //g.drawImage(trainBG, 0, 0, getWidth(), getHeight(), null);
            else if (gameState == 1){
                g.drawImage(t2r_map, 0, 0, (int)(getWidth() * 0.6), (int)(getHeight()  * 0.7) ,null);
                paintPlayerHand(g); //paints the player whos turn it is
                if (turnState ==0)
                {
                    beginTurnUI(g);
                }
        
                if (turnState ==1)
                {
                    claimRouteUI(g);
                }
        
                else if ( turnState == 2)
                {
                    pickTicketUI(g);
                }

                else if(turnState == -10){
                    paintTicketChoosing(g);
                }// choosing ticket screen
            }
        }

    }//end of paint

    public void paintTicketChoosing(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        String toPrint = "";
        for(int i = 0; i < gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getTickets().size(); i++){
            toPrint += gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getTickets().get(i).getFirstCity() + " " + gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getTickets().get(i).getSecondCity() + ", ";
        }
        g.drawString("Player " + gameAccess.getPlayerTurn() + ": " + toPrint, (int)(0.35301*getWidth()), (int)(0.15909*getHeight()));

        int cardWidth = Math.abs((int)(0.2264449968924798*getWidth() - 0.0074580484773151025*getWidth()));
        int cardHeight = Math.abs((int)( 0.9856459330143541*getHeight() -  0.6476076555023924*getHeight()));

        
        for(int i = 0; i < 4; i++){
            if(ticketsOnScreen.get(i) != null)
                g.drawImage(ticketsOnScreen.get(i).getImage(), (int)(0.017402*getWidth()) + cardWidth*i, (int)( 0.240430*getHeight()), cardWidth, cardHeight, null);
            if(!gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).checkTickets(ticketsOnScreen.get(i))){
                g.setColor(Color.GREEN);
                g.drawRect((int)(0.017402*getWidth()) + cardWidth*i, (int)( 0.240430*getHeight()), cardWidth, cardHeight);
            }
        }

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((int)(0.88440*getWidth()), (int)(0.85047*getHeight()), getWidth()-(int)(0.88440*getWidth()), getHeight()-(int)(0.85047*getHeight()));
        g.setColor(Color.black);
        g.drawString("next player", (int)(0.91112*getWidth()), (int)(0.93421*getHeight()));

    }//choosing tickets at the beginning

    public void paintViewingTickets(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        ArrayList<Ticket> tickets = gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getTickets();
        g.drawImage(tickets.get(0).getImage(), (int)(0.4139216904909882*getWidth()), (int)(0.29545454545454547*getHeight()), (int)(0.600994406463642*getWidth()), (int)(0.757177033492823*getHeight()), null);
    }//screen where they can see their tickets

    public void paintPlayerHand(Graphics g){
    
        Font origionalFont = new Font("Monospaced", Font.PLAIN, Math.abs((int)( 0.18947416762342135*getHeight() -  0.16991963260619977*getHeight())));
        Font font = new Font("Monospaced", Font.BOLD, Math.abs((int)( 0.784688995215311*getHeight() - 0.7488038277511961*getHeight())));
        g.setFont(font);
        g.drawString("Player " + gameAccess.getPlayerTurn(), (int)(0.0074580484773151025*getWidth()), (int)(0.7284688995215312*getHeight()));
        
        //prints all the card images
        int cardWidth = Math.abs((int)(0.0764449968924798*getWidth() - 0.0074580484773151025*getWidth()));
        int cardHeight = Math.abs((int)( 0.9856459330143541*getHeight() -  0.7476076555023924*getHeight()));
        for(int i = 0; i < gameAccess.getTCFiles().size(); i++){
            g.drawImage(gameAccess.getTCFiles().get(i).getImage(), (int)(0.0074580484773151025*getWidth()) + cardWidth*i, (int)( 0.7476076555023924*getHeight()), cardWidth, cardHeight, null);
            g.setColor(Color.WHITE);
            g.drawString("" + gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getTrainCards().get(gameAccess.getTCFiles().get(i).getColor()), (int)(0.03356121814791796*getWidth() + (cardWidth*i)), (int)(0.8983253588516746*getHeight()));
        }
        g.drawImage(gameAccess.getTicketBack(), (int)(0.0074580484773151025*getWidth()) + cardWidth*gameAccess.getTCFiles().size(), (int)( 0.7476076555023924*getHeight()), cardWidth, cardHeight, null);
        g.setColor(Color.BLACK);
        g.drawString("click", (int)(0.634041640770665*getWidth()), (int)(0.84688995215311*getHeight()));
        g.drawString("to", (int)(0.6514437538844002*getWidth()), (int)(0.8851674641148325*getHeight()));
        g.drawString("view", (int)(0.6401491609695463*getWidth()), (int)( 0.930622009569378*getHeight()));
        g.drawImage(train,(int)0.7085146053449347*getWidth(),(int)0.7559808612440191*getHeight(),(int)0.7694220012430081*getWidth(),(int)0.8313397129186603*getHeight(),null);
        g.drawImage(station, (int)0.7085146053449347*getWidth(),(int)0.7559808612440191*getHeight(),(int)0.7694220012430081*getWidth(),(int)0.8313397129186603*getHeight(),null);
        	//doesn't work
        g.setFont(origionalFont);
    }//end of paintPlayerHand

   
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
                    turnState = -10;
                    repaint();
                }

              //rules button            
              if (rectangularInBounds(x, y,  0.13846153846*getWidth(), 0.32307692307*getWidth(), 0.34285714285*getHeight(),  0.51234434*getHeight()))
              {
                  System.out.println("rules screen");
                  gameState = -1;
              }

            
            }  
  
        }//gamestate == 0
       
        if (gameState == 1)
        {

            //if user clicks to view tickets
            if(rectangularInBounds(x, y, (int)(0.00745*getWidth()), (int)(0.69670*getWidth()), (int)( 0.74760*getHeight()), (int)( 0.98444*getHeight())) && turnState != -10){
                System.out.println("view tickets was clicked");
                viewingTickets = true;
                repaint();
            }

            //choosing tickets
            double cardWidth = ((0.23430 - 0.01615)*getWidth());
            if(turnState == -10){
                for(int i = 0; i < 4; i++){
                    if (rectangularInBounds(x, y,  0.01615*getWidth() + (i*cardWidth), 0.23430*getWidth() + (i*cardWidth), 0.23684*getHeight(),  0.57655*getHeight()))
                    {
                        gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).addtickets(ticketsOnScreen.get(i));
                        for(Ticket t: gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getTickets()){
                            System.out.println(t.getFirstCity() + t.getSecondCity());
                        }
                    }
                }

                if (rectangularInBounds(x, y,  0.88377*getWidth(), getWidth(), 0.85047*getHeight(),  getHeight()) && gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getTickets().size() >= 2){
                    if(gameAccess.getPlayerTurn() == 4){
                        turnState = 0;
                    }
                    gameAccess.incrementTurn();
                    generateTicketsOnScreen();
                }
            }//still incomplete

            

            else if (turnState ==0)
            {
                if (rectangularInBounds(x, y, (int)(0.70711*getWidth()), (int)(0.80711*getWidth()), (int)(0.500000*getHeight()),  (int)(0.550000*getHeight())))
                    {
                     System.out.println("claim route was clicked");
                     turnState = 1;
                
                    }

                    if (rectangularInBounds(x, y, 0.7116221*getWidth(), 0.801118 * getWidth(), 0.622009 * getHeight(), 0.66985 * getHeight()))
                    {
                      System.out.println("pick destination ticket clicked");
                      turnState = 2;
                      DestinationTicket1 = gameAccess.drawTicket();
                      DestinationTicket2 = gameAccess.drawTicket();
                      DestinationTicket3 = gameAccess.drawTicket();
                    }

                

           
             }


             if (turnState ==0)
             {
                if (rectangularInBounds(x, y, 0.711622125543816*getWidth(), 0.8017402113113735 * getWidth(),  0.6208133971291866 * getHeight(), 0.6650717703349283 * getHeight()))
                {
                  System.out.println("pick destination ticket clicked");
                  turnState = 2;
                  DestinationTicket1 = gameAccess.drawTicket();
                  DestinationTicket2 = gameAccess.drawTicket();
                  DestinationTicket3 = gameAccess.drawTicket();
                }
            
              }

             else if (turnState == 1)
             {
                if (rectangularInBounds(x,y, (int) (0.8442*getWidth()), (int) (0.8942*getWidth()), (int) (0.0431*getHeight()), (int) (0.0931*getHeight()))) // if (reset button clicked)
                {
                    System.out.println("clear button clicked");
                    claimRouteState = 0;
                    city1 = null;
                    city2 = null;
                    repaint();
                    return;
                }
                if (claimRouteState == 0)
                {
                 city1 = CityDetector(x, y);
                     if (city1 != null)
                    {
                    claimRouteState = 1;

                     }
                }

                else if (claimRouteState == 1)
                {
                    city2 = CityDetector(x, y);
                    
                    if (city2 != null)
                    {
                        claimRouteState = 2;


                    }



                }
             }

             else if (turnState == 2)
             {
                if (rectangularInBounds(x, y, (int) (0.2001243 * getWidth()), (int) (0.33685 * getWidth()), (int) (0.29784 * getHeight()), (int)(0.4593*getHeight())))
                {
                    System.out.println("left DT clicked");
                    DestinationTicket1Selected = ! DestinationTicket1Selected;
                    
                }

                else if (rectangularInBounds(x, y, (int) (0.599751 * getWidth()), (int) (0.735239 * getWidth()), (int) (0.29904 * getHeight()), (int)(0.4629186*getHeight())))
                {
                    System.out.println("right DT clicked");
                    DestinationTicket2Selected = ! DestinationTicket2Selected;
                }

                else if (rectangularInBounds(x, y, (int) (0.3996 * getWidth()), (int) (0.54008 * getWidth()), (int) (0.599282 * getHeight()), (int)(0.7655502*getHeight())))
                {
                    System.out.println("bottom DT clicked");
                    DestinationTicket3Selected = ! DestinationTicket3Selected;
                }

                else if ( rectangularInBounds(x, y, (int) (getWidth() * 0.7383), (int) (getWidth() * 0.8986), (int) (getHeight() * 0.7416), (int) (getHeight() * 0.9007)))
                {
                    System.out.println("End turn clicked");

                    if ((!DestinationTicket1Selected) && (!DestinationTicket2Selected) && (!DestinationTicket3Selected))
                    {
                        System.out.println("select a card first");
                    }

                    else 
                    {
                        System.out.println("pickTicket turn ended");
                        turnState = 0;
                    }
                }

             }
            



        }
        else if(gameState == -1)  {   	  
            if(rectangularInBounds(x,y,0.8744561839651958*getWidth(),0.988755980861244 *getWidth(),0.888755980861244*getHeight(), 0.9760765550239234*getHeight()))
            { System.out.println("clicked next page");
            // clicked next page
            gameState = -2;}
        }//gamestate == -1

       else if(gameState == -2) {
    	   if(rectangularInBounds(x,y,0.867619639527657*getWidth(),0.988755980861244 *getWidth(),0.868755980861244*getHeight(), 0.9760765550239234*getHeight())) {
    		    System.out.println("go back to home screen");
    	        gameState = 0;
        }
    	   if(rectangularInBounds(x,y,0.7414543194530765*getWidth(),0.8297078931013051 *getWidth(),0.8588516746411483*getHeight(),  0.9389952153110048*getHeight())) {
    		   System.out.println("g previous page");
    		   gameState = -1;
    	   }
    	  
       }
        repaint();
    }//mouse clicked
     
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
        g.drawString("claim route", (int) (0.713486*getWidth() ), (int) (0.5227272727272727*getHeight()));
       
        g.setColor(Color.orange);
        g.fillRoundRect((int)(0.82711*getWidth()), (int)(0.500000*getHeight()),(int)( 0.1*getWidth()), (int)(0.05*getHeight()), (int)(0.01*getWidth()), (int)(0.1*getWidth()));
        g.setColor(Color.black);
        g.drawString("pick train card", (int) (0.83711*getWidth() ), (int) (0.5227272727272727*getHeight()));
               
        g.setColor(Color.orange);
        g.fillRoundRect((int)(0.70711*getWidth()), (int)(0.620000*getHeight()),(int)( 0.1*getWidth()), (int)(0.05*getHeight()), (int)(0.01*getWidth()), (int)(0.1*getWidth()));
        g.setColor(Color.black);
        g.drawString("pick ticket card", (int) (0.713486*getWidth() ), (int) (0.640*getHeight()));
       

        
        g.setColor(Color.orange);
        g.fillRoundRect((int)(0.82711*getWidth()), (int)(0.620000*getHeight()),(int)( 0.1*getWidth()), (int)(0.05*getHeight()), (int)(0.01*getWidth()), (int)(0.1*getWidth()));
        g.setColor(Color.black);
        g.drawString("build a station", (int) (0.83711*getWidth() ), (int) (0.640*getHeight()));
               




    }

    public void claimRouteUI(Graphics g)
    {
        g.setColor(Color.black);
        g.drawString("Claim Route", (int) (0.65942*getWidth()), (int) (0.04785*getHeight()));

        g.drawString("Click two cities to claim the route that", (int) (0.65942*getWidth()), (int) (0.09785*getHeight()) );
        g.drawString("connects them", (int) (0.65942*getWidth()), (int) (0.1205*getHeight()) );

        g.drawString("City 1:", (int) (0.65942*getWidth()), (int) (0.1605*getHeight()) );

        if (city1 != null)
        {
            g.drawString( city1.getName(),(int) (0.68942*getWidth()), (int) (0.1605*getHeight()) );
            g.setColor(Color.green);
            g.fillOval( (int) (city1.getCoords()[0] * getWidth() * 0.6 * 205 / 154.792222) - (int)(getWidth()*0.025)/2, (int)(city1.getCoords()[1] * getHeight() * 0.7 * 172 / 133.694) - (int)(getHeight()* 0.04)/2, (int)(getWidth()*0.025), (int)(getHeight()* 0.04) );
            g.setColor(Color.black);
        }

         g.drawString("City 2:", (int) (0.65942*getWidth()), (int) (0.205*getHeight()) );

         if (city2 != null)
         {
            g.drawString( city2.getName(),(int) (0.68942*getWidth()), (int) (0.205*getHeight()) );        
            g.setColor(Color.green);
            g.fillOval( (int) (city2.getCoords()[0] * getWidth() * 0.6 * 205 / 154.792222) - (int)(getWidth()*0.025)/2, (int)(city2.getCoords()[1] * getHeight() * 0.7 * 172 / 133.694) - (int)(getHeight()* 0.04)/2, (int)(getWidth()*0.025), (int)(getHeight()* 0.04) );
            g.setColor(Color.black);
        }

         g.drawRect((int) (0.8442*getWidth()), (int) (0.0431*getHeight()), (int) (getWidth()*0.05), (int) (getHeight()*0.05)); //draws clear button
    }

    public void pickTicketUI(Graphics g)
    {
        
        g.setColor((Color.white));
        g.fillRect(getWidth()/10, getHeight()/10, (int)(getWidth()*0.8), (int)(getHeight()*0.8));
        g.setColor(Color.black);
        g.drawString("Pick at least one ticket to keep", (int)(0.30640149*getWidth()), (int)(0.16507 * getHeight()));
        g.drawRect((int)(0.738020833 * getWidth()) , (int) (0.7413962635201573 * getHeight()), (int) (0.16 * getWidth()), (int) (0.16 * getHeight()));
        g.drawString("End Turn", (int) (0.763206 * getWidth()), (int) (getHeight() * 0.76794));
        g.drawImage(DestinationTicket1.getImage(), (int) (getWidth() * 0.2), (int) (getHeight()*0.3), null);
        g.drawImage(DestinationTicket2.getImage(), (int) (getWidth() * 0.6), (int) (getHeight()*0.3), null);
        g.drawImage(DestinationTicket3.getImage(), (int) (getWidth() * 0.4), (int) (getHeight()*0.6), null);

       
        
            if (DestinationTicket1Selected)
            {
                g.setColor(Color.green);
                g.drawRect( (int) (getWidth() * 0.2), (int) (getHeight()*0.3), (int)(getWidth() * 0.144), (int) (getHeight() * 0.16));
                System.out.println("ticket 1 is selected");
                g.setColor(Color.black);

            }

             if (DestinationTicket2Selected)
            {
                g.setColor(Color.green);
                g.drawRect( (int) (getWidth() * 0.6), (int) (getHeight()*0.3), (int)(getWidth() * 0.14), (int) (getHeight() * 0.16));
                System.out.println("ticket 2 is selected");

                g.setColor(Color.black);

            }

             if (DestinationTicket3Selected)
            {
                g.setColor(Color.green);
                g.drawRect( (int) (getWidth() * 0.4), (int) (getHeight()*0.6), (int)(getWidth() * 0.14), (int) (getHeight() * 0.16));
                System.out.println("ticket 3 is selected");
                

            }


        

    }


    public City CityDetector(double  x, double  y)
    {
        for (City c: gameAccess.getCities())
        {
            //City c = gameAccess.getCities().get(0);
            double scaledCityX = c.getCoords()[0] * getWidth() * 0.6 * 205 / 154.792222; 
            double scaledCityY = c.getCoords()[1] * getHeight() * 0.7 * 172 / 133.694;

            double distance = Math.sqrt(Math.pow(scaledCityX - x, 2) + Math.pow(scaledCityY - y, 2));
            /*  System.out.println(distance + " : distance from " + c.getName());
            System.out.println("ScaledX: " + scaledCityX + " and X: " + x + "and ScaledY " + scaledCityY + "and Y: " + y); */
            
            if (distance < 10)
            {
                System.out.println("City detected: " + c.getName());
                return c;
            }





        }



        return null;
    }

}
