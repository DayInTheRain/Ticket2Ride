import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.*;

public class T2RPanel extends JPanel implements MouseListener{
    Image trainBG;
    Image t2r_map;
    Image rules1;
    Image rules2;
    Image endscreen;
    Image station, train;
    Image woodenBg;
    int gameState;
    int turnState;
    
    JButton startbutton;
    JButton rulesbutton;
    Game gameAccess;  

    //claimRoute IVs
    int claimRouteState;
    City city1;
    City city2;
    ArrayList<TrainCard> tunnelCards;
    boolean isgrey;
    boolean tunnel;
    boolean canPurchaseTunnel;
    boolean continueButtonClicked;
    String color;
    boolean isDouble;
    boolean invalidColor;
    boolean colorChosen;
    boolean hasOther;
    boolean taken;

    //pickTicket IVs
    int pickTicketState;
    Ticket destinationTicket1;
    Ticket destinationTicket2;
    Ticket destinationTicket3;
    Boolean viewingTickets;
    ArrayList<Ticket> ticketsOnScreen;
    boolean destinationTicket1Selected;
    boolean destinationTicket2Selected;
    boolean destinationTicket3Selected;

    //pickTrainCard IVs
    int pickTrainCardState;

    //buildStation IVs
    City buildStationCity;
    int buildStationState;
    String buildStationColor;

    //pickStationRoutes IVs
    boolean ableToPurchase;
   
    //ending game
    boolean isEnd;
    boolean isLast;
    int lastTurn;
    boolean firstlast;
    

    Font origionalFont;

    Graphics g;
    ArrayList<String> ColorsPicked;

    T2RFrame frame;

    Timer timer1;
    int time1;

    Timer timer2;
    int time2;

    Timer timer3;
    int time3;


    double heading;
	double pitch;

    Matrix3 headingTransform;
	 Matrix3 pitchTransform;
	 Matrix3 transform;


     Square DestinationCardBackSquare;

     BufferedImage DestinationCardBack;


     private boolean isLookingAtMap;
    public T2RPanel()
    {
        gameAccess = new Game();
        gameState = 0;
        //END TURN NOT FINISHED (GAMESTATE = 2)
        turnState = 0;
        tunnel = false;
        canPurchaseTunnel = false;
        continueButtonClicked = false;
        color = null;
        isDouble = false;
        invalidColor = false;
        colorChosen = false;
        hasOther = false;
        ableToPurchase = false;
        taken = false;

        ticketsOnScreen = new ArrayList<>();
        tunnelCards = new ArrayList<>();
        generateTicketsOnScreen();

        viewingTickets = false;
        origionalFont = new Font("Cantarell Extra Bold", Font.BOLD, Math.abs((int)( 0.18947416762342135*getHeight() -  0.16991963260619977*getHeight()))); //sets the standard font for printing things
        claimRouteState = 0;
        pickTicketState = 0;
        buildStationState = 0;
        System.out.println();
        System.out.println("testing");
       
        addMouseListener(this);
       

        trainBG = ImageLoader.get("/Images/trainBG.png");
        t2r_map = ImageLoader.get("/Images/t2r map.png");
        rules1 = ImageLoader.get("/Images/rules1.jpg");
        rules2 = ImageLoader.get("/Images/rules2.jpg");
        station = ImageLoader.get("/Images/station.png");
        train = ImageLoader.get("/Images/train.png");
        endscreen = ImageLoader.get("/Images/end screen.jpg");
        woodenBg = ImageLoader.get("/Images/WoodenBackground.jpg");
        city1 = null;
        city2 = null;
        destinationTicket1 = null;
        destinationTicket2 = null;
        destinationTicket3 = null;
        //isgrey = false;
        //colorChoosen = "";
        buildStationColor = null;
        ColorsPicked = new ArrayList<String>();
        
        
        isEnd = false;
        isLast = false;
        lastTurn = -1;
        firstlast = false;

        timer1 = new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            
            @Override
            public void run() {
               
                if (destinationTicket1Selected || destinationTicket2Selected || destinationTicket3Selected) {
                   
                    time1 += 4;
                    SwingUtilities.invokeLater(() -> repaint());
                }
            }
        }, 0, 10);

        

           try {
        	  DestinationCardBack = ImageIO.read(T2RPanel.class.getResource("/Images/CardBacks/TicketBack.jpg"));
        	  
          }
          
          catch(Exception E)
          {
        	  System.out.println("Exception Error");
        	  return;
          }
        new Vertex(0,0,0);
        DestinationCardBackSquare = new Square(new Vertex(0,0,0), new Vertex (DestinationCardBack.getWidth(),0,0), new Vertex(DestinationCardBack.getWidth(), DestinationCardBack.getHeight(), 0), new Vertex(0,DestinationCardBack.getHeight(),0), Color.black);
          
          isLookingAtMap = false;

    }//end of constructor

    public void generateTicketsOnScreen(){
        ticketsOnScreen.clear();
        ticketsOnScreen.add(gameAccess.drawLongTicket());
        ticketsOnScreen.add(gameAccess.drawTicket());
        ticketsOnScreen.add(gameAccess.drawTicket());
        ticketsOnScreen.add(gameAccess.drawTicket());
    }//generateTicketsOnScreen

    public void paint(Graphics f)
    {
        //System.out.println("dt1 selected? " +destinationTicket1Selected);
        f.drawImage(woodenBg, 0, 0, getWidth(), getHeight(), null);
       
        System.out.println("the # of railroads I currently have " +getCurrentPlayer().getRailroadList().size());
        System.out.println("Discard Pile RN: " + gameAccess.getDiscardPile());
        //for(TrainCard train : gameAccess.getDiscardPile()){System.out.println(train.getColor());}

       
        g = f;
        super.paint(g);
       
        if(viewingTickets){
            paintViewingTickets(g);
        }
        else  { //basically saying if it's not showing the tickets print other stuff cause the tickets rnt included in the gamestate
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
                g.drawImage(woodenBg, 0, 0, getWidth(), getHeight(), null);
                g.drawImage(t2r_map, 0, 0, (int)(getWidth() * 0.6), (int)(getHeight()  * 0.7) ,null);
                paintClaimedRailroads(g); //paints the claimed railroads in the color of the player
                paintPlayerHand(g); //paints the player whos turn it is
                if (firstlast && turnState == 0)
                {
                    paintSkipButton(g);
                }
                if (turnState ==0)
                {
                    beginTurnUI(g);
                    paintGridOnScreen(g);
                    drawStations(g);
                }
        
                if (turnState ==1)
                {
                    claimRouteUI(g);
                    drawStations(g);
                }
        
                else if ( turnState == 2)
                {
                    pickTicketUI(g);
                }
                else if (turnState == 3)
                {
                    pickTrainCardUI(g);
                }

                else if (turnState == 4)
                {
                    buildStationUI(g);
                    drawStations(g);
                }
                else if(turnState == 10){
                    pickStationRoutesUI(g);
                }

                else if(turnState == -10){
                    paintTicketChoosing(g);
                }// choosing ticket screen
                 //the drawn stations are permanent changes to the map.
            }
            else if(gameState == 2) {
            // endscreen
            	//g.drawImage(endscreen, 0, 0, (int)(getWidth()), (int)(getHeight()), null);
            	endGame(g);
            	
            }
        }

    }//end of paint

    private void paintClaimedRailroads(Graphics g){
        int radius = 10;
        for(Player p : gameAccess.getPlayers()){
            for(Railroad r : p.getRailroadList()){
                g.setColor(p.getColor());
                g.fillOval( (int)(r.getCoords()[0] * getWidth()) - radius, (int)(r.getCoords()[1] * getHeight()) - radius, radius*2, radius*2);
            }
        }
    }//paintalimedRailroads

    public void paintTicketChoosing(Graphics g){
        // g.setColor(Color.white);
        // g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(woodenBg, 0, 0, getWidth(), getHeight(), null);

        g.setColor(Color.WHITE);
        String toPrint = "";
        for(int i = 0; i < getCurrentPlayer().getTickets().size(); i++){
            toPrint += getCurrentPlayer().getTickets().get(i).getFirstCity() + " " + getCurrentPlayer().getTickets().get(i).getSecondCity() + ", ";
        }
        g.drawString("Player " + gameAccess.getPlayerTurn() + ": " + toPrint, (int)(0.35301*getWidth()), (int)(0.15909*getHeight()));

        int cardWidth = Math.abs((int)(0.2264449968924798*getWidth() - 0.0074580484773151025*getWidth()));
        int cardHeight = Math.abs((int)( 0.9856459330143541*getHeight() -  0.6476076555023924*getHeight()));

        
        for(int i = 0; i < 4; i++){
            if(ticketsOnScreen.get(i) != null)
                g.drawImage(ticketsOnScreen.get(i).getImage(), (int)(0.017402*getWidth()) + cardWidth*i, (int)( 0.240430*getHeight()), cardWidth, cardHeight, null);
            if(!getCurrentPlayer().checkTickets(ticketsOnScreen.get(i))){
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
        //background
        // g.setColor(Color.WHITE);
        // g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(woodenBg, 0, 0, getWidth(), getHeight(), null);


        //draws the ticket
        ArrayList<Ticket> tickets = getCurrentPlayer().getTickets();
        g.drawImage(tickets.get(0).getImage(), (int)(0.29272*getWidth()), (int)(0.20574*getHeight()), (int)((0.68924-0.29272)*getWidth()), (int)((0.75239-0.20574)*getHeight()), null);
        System.out.println(tickets.get(0).getFirstCity());
        //next card
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((int)(0.73834*getWidth()), (int)(0.41387*getHeight()), (int)((0.81106-0.73834)*getWidth()), (int)((0.555020-0.41387)*getHeight()));
        g.setColor(Color.black);
        g.drawString("next ticket", (int)(0.75201*getWidth()), (int)(0.49521*getHeight()));

        //exit
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((int)(0.92977*getWidth()), (int)(0.88875*getHeight()), (int)(getWidth()), (int)(getHeight()));
        g.setColor(Color.black);
        g.drawString("back to game", (int)(0.94465*getWidth()), (int)(0.95380*getHeight()));
        
    }//screen where they can see their tickets

    public void incrementTicket(boolean f) {
        gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).rotateTickets(f);
    }//helper method for viewing tickets

    public void paintPlayerHand(Graphics g){
    
        Font origionalFont = new Font("Cantarell Extra Bold", Font.BOLD, Math.abs((int)( 0.18947416762342135*getHeight() -  0.16991963260619977*getHeight())));
        Font font = new Font("Cantarell Extra Bold", Font.BOLD, Math.abs((int)( 0.784688995215311*getHeight() - 0.7488038277511961*getHeight())));
        g.setFont(font);
        g.setColor(getCurrentPlayer().getColor());
        g.drawString("Player " + gameAccess.getPlayerTurn(), (int)(0.0074580484773151025*getWidth()), (int)(0.7284688995215312*getHeight()));
        g.setColor(Color.BLACK);
        //prints all the card images
        int cardWidth = Math.abs((int)(0.0764449968924798*getWidth() - 0.0074580484773151025*getWidth()));
        int cardHeight = Math.abs((int)( 0.9856459330143541*getHeight() -  0.7476076555023924*getHeight()));
        for(int i = 0; i < gameAccess.getTCFiles().size(); i++){
            g.drawImage(gameAccess.getTCFiles().get(i).getImage(), (int)(0.0074580484773151025*getWidth()) + cardWidth*i, (int)( 0.7476076555023924*getHeight()), cardWidth, cardHeight, null);
            g.setColor(Color.WHITE);
            g.drawString("" + getCurrentPlayer().getTrainCards().get(gameAccess.getTCFiles().get(i).getColor()), (int)(0.03356121814791796*getWidth() + (cardWidth*i)), (int)(0.8983253588516746*getHeight()));
        }
        g.drawImage(gameAccess.getTicketBack(), (int)(0.0074580484773151025*getWidth()) + cardWidth*gameAccess.getTCFiles().size(), (int)( 0.7476076555023924*getHeight()), cardWidth, cardHeight, null);
        g.setColor(Color.BLACK);
        g.drawString("click", (int)(0.634041640770665*getWidth()), (int)(0.84688995215311*getHeight()));
        g.drawString("to", (int)(0.6514437538844002*getWidth()), (int)(0.8851674641148325*getHeight()));
        g.drawString("view", (int)(0.6401491609695463*getWidth()), (int)( 0.930622009569378*getHeight()));
        g.drawImage(train,(int)(0.7085146053449347*getWidth()),(int)(0.7559808612440191*getHeight()),(int)((0.7588564325668117-0.688514605)*getWidth()),(int)((0.92105263157894733-0.8241626794258373)*getHeight()),null);
        g.drawImage(station,(int)(0.711622125543816*getWidth()),(int)(0.8588516746411483*getHeight()),(int)((0.7694220012430081-0.711622125543816)*getWidth()),(int)((0.8313397129186603-0.75598086124)*getHeight()),null);
        g.drawString(""+getCurrentPlayer().getNumTrains(),(int)(0.785581106277190*getWidth()), (int)(0.7978468899521531*getHeight()));	
        g.drawString(""+getCurrentPlayer().getNumTrainStations(),(int)(0.79055313859590*getWidth()), (int)( 0.889952153110031*getHeight()));
        g.setFont(origionalFont);
    }//end of paintPlayerHand

    public void paintGridOnScreen(Graphics g){
        double cardWidth = 0.665-0.60285;
        double cardHeight = 0.17703-0.00598;
        g.drawImage(gameAccess.getTrainCardBack(), (int)(0.60285*getWidth()), (int)(0.00598*getHeight()), (int)(cardWidth*getWidth()), (int)(cardHeight*getHeight()), null);
        for(int i = 0; i < 5; i++){
            g.drawImage(gameAccess.getGrid().get(i).getImage(), (int)((0.67060 + cardWidth*i)*getWidth()), (int)((0.00598)*getHeight()), (int)(cardWidth*getWidth()), (int)(cardHeight*getHeight()), null);
        }
        g.drawString("Discard Pile", (int)(0.62274704*getWidth()), (int)(0.2344497*getHeight()));
        if(gameAccess.hasDiscard())
            g.drawImage(gameAccess.getDiscardCard().getImage(), (int)(0.62274704*getWidth()), (int)(0.2344497*getHeight()), (int)(cardWidth*getWidth()), (int)(cardHeight*getHeight()), null);
    }//paintGridOnScreen

    public boolean canPurchase(){
        Railroad railroad = gameAccess.getMap().getRailroad(city1, city2);
        int numWild = railroad.getNumWild();
        int num = railroad.getNumTrains();
        if(color == null && !railroad.isDouble()){
            color = railroad.getColor();
        }
        if(color != null && !color.equals("grey")){
            int numOfColor = getCurrentPlayer().getTrainCards().get(color);
            int numOfWild  = getCurrentPlayer().getTrainCards().get("wild");
            int numWildForUse = numOfWild-numWild;
            if(numOfWild >= numWild && (numOfColor >= num || numOfColor+numWildForUse >= num)) {
                return true;
            }
        }
        return false;
    }//helper method for claimroute

   
    @Override
    public void mouseClicked(MouseEvent e) {
    	
       System.out.println("gamestate " + gameState);
       System.out.println("turnstate: " + turnState);
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
              if (rectangularInBounds(x, y, (int)( 0.13846153846*getWidth()),(int)( 0.32307692307*getWidth()), (int)(0.34285714285*getHeight()), (int)( 0.51234434*getHeight())))
              {
                  System.out.println("rules screen");
                  gameState = -1;
                  repaint();
              }
            

            
            }  
        }
       else if(gameState == -1)  {   	  
     	   if(rectangularInBounds(x,y,0.8744561839651958*getWidth(),0.988755980861244 *getWidth(),0.888755980861244*getHeight(), 0.9760765550239234*getHeight()))
     	   { System.out.println("clicked next page");
     	   // clicked next page
     	   gameState = -2;}
        }
        else if(gameState == -2) {
     	   if(rectangularInBounds(x,y,0.8744561839651958*getWidth(),0.988755980861244 *getWidth(),0.888755980861244*getHeight(), 0.9760765550239234*getHeight())) {
     		   System.out.println("go back to home screen");
     	   gameState = 0;}
     	   if(rectangularInBounds(x,y,0.7414543194530765*getWidth(),0.8297078931013051 *getWidth(),0.8588516746411483*getHeight(),  0.9389952153110048*getHeight())) {
     		   System.out.println("g previous page");
     		   gameState = -1;
     	   }
 
        } repaint();
        if(viewingTickets){
            if(rectangularInBounds(x, y, (int)(0.73834*getWidth()), (int)(0.81106*getWidth()), (int)( 0.41387*getHeight()), (int)( 0.55502*getHeight()))){
                incrementTicket(true);
                System.out.println("next clicked");
            }
            if(rectangularInBounds(x, y, (int)(0.92977*getWidth()), (int)(getWidth()), (int)( 0.88875*getHeight()), (int)(getHeight()))){
                viewingTickets = false;
            }
            repaint();
        }//viewing tickets for any gamestate
       
        if (gameState == 1 && !viewingTickets)
        {

            g.fillRoundRect((int)(0.82711*getWidth()), (int)(0.740000*getHeight()),(int)( 0.1*getWidth()), (int)(0.05*getHeight()), (int)(0.01*getWidth()), (int)(0.1*getWidth()));
            if ( turnState == 0 && firstlast  &&  rectangularInBounds(x, y,(int)(0.82711*getWidth()), (int)(0.92711*getWidth()), (int)(0.740000*getHeight()), (int)(0.790000*getHeight())) )
            {
                System.out.println("player skipped turn");
                
                 // i think there's some stuff missing here to add
                if (lastTurn >4)
                {
                    gameState = 2;
                }
                lastTurn++;
                System.out.println("lastturn = " + lastTurn);
                gameAccess.incrementTurn();
            }
            
            
            //if user clicks to view tickets
            if(rectangularInBounds(x, y, (int)(0.62834*getWidth()), (int)(0.69484*getWidth()), (int)( 0.74880*getHeight()), (int)( 0.98205*getHeight())) && turnState != -10){
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
                        if(!getCurrentPlayer().checkTickets(ticketsOnScreen.get(i))){
                            getCurrentPlayer().removeTicket(ticketsOnScreen.get(i));
                        } else {
                            getCurrentPlayer().addtickets(ticketsOnScreen.get(i));
                        }
                        for(Ticket t: getCurrentPlayer().getTickets()){
                            System.out.println(t.getFirstCity() + t.getSecondCity());
                        }
                    }
                }

                if (rectangularInBounds(x, y,  0.88377*getWidth(), getWidth(), 0.85047*getHeight(),  getHeight()) && getCurrentPlayer().getTickets().size() >= 2){
                    if(gameAccess.getPlayerTurn() == 4){
                        turnState = 0;
                    }
                   
                    gameAccess.incrementTurn();
                    generateTicketsOnScreen();
                }
            }

            
            else if (turnState ==0)
            {
                if (rectangularInBounds(x, y, (int)(0.70711*getWidth()), (int)(0.80711*getWidth()), (int)(0.500000*getHeight()),  (int)(0.550000*getHeight())))
                    {
                     System.out.println("claim route was clicked");
                     turnState = 1;
                
                    }

                if (rectangularInBounds(x, y, 0.7116221*getWidth(), 0.801118 * getWidth(), 0.622009 * getHeight(), 0.66985 * getHeight()) && gameAccess.hasTickets())//if click destination ticket
                    {
                      System.out.println("pick destination ticket clicked");
                      turnState = 2;
                      if(gameAccess.hasTickets())
                        destinationTicket1 = gameAccess.drawTicket();
                      if(gameAccess.hasTickets())
                        destinationTicket2 = gameAccess.drawTicket();
                      if(gameAccess.hasTickets())
                        destinationTicket3 = gameAccess.drawTicket();
                    }
                if(rectangularInBounds(x, y, 0.83095*getWidth(), 0.92231 * getWidth(), 0.50239 * getHeight(), 0.54545 * getHeight()))
                    {
                        System.out.println("pick ticket clicked");
                        turnState = 3;
                    }
                if(rectangularInBounds(x, y, 0.83095*getWidth(), 0.92231 * getWidth(), 0.62209 * getHeight(), 0.66985 * getHeight()))
                 {
                        System.out.println("build Station clicked");
                        turnState = 4;
                 }

       
             }

             else if (turnState == 1)
             {

                
       

                 if (rectangularInBounds(x, y, (int)(0.9242*getWidth()) , (int)(0.9742*getWidth()), (int)(0.0931*getHeight()), (int)(0.1331*getHeight())) && !((claimRouteState == 3 && canPurchase()) || ((isDouble || isgrey) && claimRouteState == 3 && colorChosen)))
                     {
                 System.out.println("clear button clicked");
                 
                 city1 = null;
                 city2 = null;
                 claimRouteState = 0;
                 tunnel = false;
                 canPurchaseTunnel = false;
                 continueButtonClicked = false;
                 color = null;
                 isgrey = false;
                 isDouble = false;
                 colorChosen = false;
                 hasOther = false;
                 taken = false;
                 repaint();
                 return;
                 
                 }
            	
                if (rectangularInBounds(x,y, (int) (0.9235550031075*getWidth()), (int) (0.97327532628*getWidth()), (int) ( 0.022727272727*getHeight()), (int) (0.0610047846*getHeight())) && !((claimRouteState == 3 && canPurchase()) || ((isDouble || isgrey) && claimRouteState == 3 && colorChosen))) // if (reset button clicked)
                {
                    System.out.println("go back button clicked");
                    city1 = null;
                    city2 = null;
                    claimRouteState = 0;
                    turnState = 0;
                    tunnel = false;
                    canPurchaseTunnel = false;
                    continueButtonClicked = false;
                    color = null;
                    isgrey = false;
                    isDouble = false;
                    colorChosen = false;
                    hasOther = false;
                    taken = false;
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
                    
                    if (city2 != null && !gameAccess.getMap().railroadExists(city1, city2).equals("false"))
                    {
                        claimRouteState = 2;
                        Railroad r = gameAccess.getMap().getRailroad(city1, city2);
                        if(r.isTunnel() && r.getPlayer() == null /*&& canPurchase()*/){ //uncomment that little thing
                            claimRouteState = 3;
                            tunnel = true;
                            generateTunnelCards();
                            System.out.println("tunnel");
                        }
                    }
                    else
                    	city2 = null;
                    	System.out.println("Cities are not connected");

                }

                else if (claimRouteState == 2)
                {
                    System.out.println("Claim route state 2");
                	 Railroad railroad = gameAccess.getMap().getRailroad(city1, city2);
                     isDouble = railroad.isDouble();
                     if(railroad.getColor().equals("grey"))
                        isgrey = true;
                    if(color != null && railroad.isDouble() && !isgrey){
                        if(!railroad.getColor().equals(color))
                            railroad = railroad.getSisterRailroad();
                    } else if(color != null && railroad.isDouble()){
                        if(railroad.getPlayer() != null){
                            if(railroad.getSisterRailroad().getPlayer() == null){
                                railroad = railroad.getSisterRailroad();
                            }
                        }
                    }
                     int num = 0;
                     int numWild = 0;
                     if(isDouble){
                        hasOther = railroad.getSisterRailroad() != null && railroad.getSisterRailroad().getPlayer() != null && railroad.getSisterRailroad().getPlayer().equals(getCurrentPlayer());
                     }
                     if((railroad.getPlayer() == null || railroad.getSisterRailroad() != null && railroad.getSisterRailroad().getPlayer() == null) && !hasOther) {
                        System.out.println("IS DOUBLE" + railroad.isDouble() + railroad.getColor());
                        if(color == null && !railroad.isDouble()){
                            color = railroad.getColor();
                        }
                        System.out.println("COLR" + color);
                        if(railroad.getColor().equals("grey") || isDouble) {
                            System.out.println("grey train or double");
                            if (colorPicked(x, y) != null)
                            {
                                color = colorPicked(x, y);
                                System.out.println("Color picked: " + color);
                            }
                            if(color != null && !isgrey){
                                colorChosen = true;
                                if(isDouble){
                                    if(!railroad.getColor().equals(color) && !railroad.getSisterRailroad().getColor().equals(color)){
                                        invalidColor = true;
                                    }else if(railroad.getColor().equals(color)){
                                        invalidColor = false;
                                    }else if(railroad.getSisterRailroad().getColor().equals(color)){
                                        invalidColor = false;
                                        railroad = railroad.getSisterRailroad();
                                    }
                                }
                            } else if(color != null && isgrey && isDouble){
                                invalidColor = false;
                            }
                            
                        }
                        Railroad r1 = gameAccess.getMap().getRailroad(city1, city2);
                        Railroad r2 = null;
                        if(r1.getSisterRailroad() != null){
                            r2 = r1.getSisterRailroad();
                            if(r1.getColor().equals(color)){
                                if(colorChosen && r1.getPlayer() != null){
                                    taken = true;
                                } 
                            }
                            else if (r2.getColor().equals(color)){
                                if(colorChosen && r2.getPlayer() != null){
                                    taken = true;
                                } 
                            } else {
                                taken = false;
                            }
                        }
                        // if(colorChosen && gameAccess.getMap().getRailroad(city1, city2, color).getPlayer() != null){
                        //     taken = true;
                        // }else{
                        //     taken = false;
                        // }
                        System.out.println("\nInvalidColor: " + invalidColor +"\nColor: " + color + "\n");
                        if(!invalidColor && color != null && !taken && (city1 != null && city2 != null && color != null && getCurrentPlayer().getNumTrains() >= gameAccess.getMap().getRailroad(city1, city2, color).getNumTotalTrains())){
                            numWild = railroad.getNumWild();
                            num = railroad.getNumTrains();
                            int numOfColor = getCurrentPlayer().getTrainCards().get(color);
                            System.out.println(""+numOfColor);
                            int numOfWild  = getCurrentPlayer() .getTrainCards().get("wild");
                            int numWildForUse = numOfWild-numWild;
                            
                            if (rectangularInBounds(x, y, (int)(0.8154133001864512 * getWidth()), (int)(0.8954133001864512 * getWidth()), (int)(0.777511961722488 * getHeight()), (int) (0.902511961722488 * getHeight())))
                            {
                                System.out.println("Finish turn button clicked");
                                
                                if(true) {
                                    System.out.println("HERE");
                                    numWild = railroad.getNumWild();
                                    num = railroad.getNumTrains();
                                    //color = railroad.getColor();
                                
                                    
                                    System.out.println("numOfWild" + numOfWild + " numwild" + numWild + " numofcolor" + numOfColor + " num" + num);
                                    if(numOfWild >= numWild && (numOfColor >= num || numOfColor+numWildForUse >= num)) {
                                        ArrayList<TrainCard> cardsToDiscard = new ArrayList<>();
                                        for(int i = 0; i < numWild; i++){
                                            cardsToDiscard.add(new TrainCard("wild"));
                                        }
                                        getCurrentPlayer().getTrainCards().replace("wild", numOfWild- numWild);
                                        if(numOfColor >= num){
                                            for(int i = 0; i < num; i++){
                                                cardsToDiscard.add(new TrainCard(color));
                                            }
                                            getCurrentPlayer() .getTrainCards().replace(color, numOfColor- num);
                                            gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1) .getTrainCards().replace(color, numOfColor- num);
                                        }   
                                        else if(numOfColor+numWildForUse >= num) {
                                            int numLeft = num-numOfColor;
                                            for(int i = 0; i < getCurrentPlayer().getTrainCards().get(color); i++){
                                                cardsToDiscard.add(new TrainCard(color));
                                            }
                                            getCurrentPlayer().getTrainCards().replace(color, 0);
                                            for(int i = 0; i < numLeft; i++){
                                                cardsToDiscard.add(new TrainCard("wild"));
                                            }
                                            getCurrentPlayer().getTrainCards().replace("wild", numWildForUse-numLeft);
                                        }
                                        //getCurrentPlayer().addRailroad(railroad);
                                        railroad.claim(getCurrentPlayer());
                                        getCurrentPlayer().addPoints(railroad.getPoints());
                                        getCurrentPlayer().placetrains(railroad.getNumTotalTrains());
                                        System.out.println("\nrailroads: " + getCurrentPlayer().getRailroadList().size() + "\n");

                                        city1 = null;
                                        city2 = null;
                                        claimRouteState = 0;
                                        turnState = 0;
                                        color = null;
                                        isgrey = false;
                                        isDouble = false;
                                        colorChosen = false;
                                        hasOther = false;
                                        taken = false;
                                        
                                        System.out.println("EHUWHHS");
                                        if(gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getNumTrains() <=2) {
                                        	isLast = true;
                                        	
                                        	System.out.println("lastTurn");
                                        	if(!firstlast) {
                                                lastTurn = 0;
                                                firstlast = true;
                                                System.out.println("first person has reached last");
                                        	lastTurn = -1;
                                        	firstlast = true;
                                        	System.out.println("first person has reached last");

                                        	}
                                        }
                                   	 if(isLast) {
                                   		 lastTurn++;
                                   		 System.out.println("players last turn" + lastTurn);
                                   	 }
                                   	 if(isLast && lastTurn >= 4) {
                                   		 	//turnState = 10;
                                        		gameState = 2;
                                        		System.out.println("end game");
                                        }
                                        gameAccess.incrementTurn();
                                        for(TrainCard t: cardsToDiscard)
                                            gameAccess.discardTrainCard(t); 
                                    }
                                }   
                                g.drawRect((int)(0.8154133001864512 * getWidth()) , (int)(0.777511961722488 * getHeight()), (int) (0.08 * getWidth()), (int) (0.125 * getHeight())) ;
                            }
                        }
                    }
                }
            
                     
             else if(claimRouteState == 3){
                System.out.println("Claim route state 2");
                	 Railroad railroad = gameAccess.getMap().getRailroad(city1, city2);
                     isDouble = railroad.isDouble();
                     if(railroad.getColor().equals("grey"))
                        isgrey = true;
                    if(color != null && railroad.isDouble()){
                        if(!railroad.getColor().equals(color))
                            railroad = railroad.getSisterRailroad();
                    }
                     int num = 0;
                     int numWild = 0;
                if(railroad.getColor().equals("grey")) {
                    System.out.println("grey train");
                    if (colorPicked(x, y) != null && !colorChosen)
                    {
                        color = colorPicked(x, y);
                        colorChosen = true;
                        
                    }
                }
                if(color == null && !railroad.isDouble()){
                    color = railroad.getColor();
                }
                System.out.println("COLR" + color);
                if(railroad.getColor().equals("grey") || isDouble) {
                    System.out.println("grey train or double");
                    if (colorPicked(x, y) != null)
                    {
                        color = colorPicked(x, y);
                        System.out.println("Color picked: " + color);
                    }
                    if(color != null){
                        colorChosen = true;
                        if(isDouble){
                            if(!railroad.getColor().equals(color) && !railroad.getSisterRailroad().getColor().equals(color)){
                                invalidColor = true;
                            }else if(railroad.getColor().equals(color)){
                                invalidColor = false;
                            }else if(railroad.getSisterRailroad().getColor().equals(color)){
                                invalidColor = false;
                                railroad = railroad.getSisterRailroad();
                            }
                        }
                    }
                    
                }
                
                int counter = 0;
    
                if(!invalidColor && color != null && (city1 != null && city2 != null && color != null && getCurrentPlayer().getNumTrains() >= gameAccess.getMap().getRailroad(city1, city2, color).getNumTotalTrains())){
                    numWild = railroad.getNumWild();
                            num = railroad.getNumTrains();
                            int numOfColor = getCurrentPlayer().getTrainCards().get(color);
                            System.out.println(""+numOfColor);
                            int numOfWild  = getCurrentPlayer() .getTrainCards().get("wild");
                            int numWildForUse = numOfWild-numWild;
                    for(TrainCard t: tunnelCards){
                        System.out.println(t.getColor());
                        if(t.getColor().equals(color) || t.getColor().equals("wild")){
                            counter++;
                        }
                    }
                    num += counter;
                    System.out.println("here is the num "+ num);
                    if(numOfWild >= numWild && numOfColor >= num || numOfColor+numWildForUse >= num) {
                        if(numOfColor >= num){
                            canPurchaseTunnel = true;
                        }else if(numOfColor+numWildForUse >= num) {
                            canPurchaseTunnel = true;
                        }
                    }
                    
                    if (rectangularInBounds(x, y, (int)(0.8154133001864512 * getWidth()), (int)(0.8954133001864512 * getWidth()), (int)(0.777511961722488 * getHeight()), (int) (0.902511961722488 * getHeight())) && !continueButtonClicked)
                    {
                        continueButtonClicked = true;
                    }
                    else if (rectangularInBounds(x, y, (int)(0.8154133001864512 * getWidth()), (int)(0.8954133001864512 * getWidth()), (int)(0.777511961722488 * getHeight()), (int) (0.902511961722488 * getHeight())) && continueButtonClicked)
                    {
                        if(isDouble){
                            hasOther = railroad.getSisterRailroad() != null && railroad.getSisterRailroad().getPlayer() != null && railroad.getSisterRailroad().getPlayer().equals(getCurrentPlayer());
                        }
                        if((railroad.getPlayer() == null || railroad.getSisterRailroad() != null && railroad.getSisterRailroad().getPlayer() == null) && !hasOther) {
                            System.out.println("numOfWild" + numOfWild + " numwild" + numWild + " numofcolor" + numOfColor + " num" + num);
                            if(numOfWild >= numWild && (numOfColor >= num || numOfColor+(numWild-numOfWild) >= num)) {
                                ArrayList<TrainCard> cardsToDiscard = new ArrayList<>();
                                for(int i = 0; i < numWild; i++){
                                    cardsToDiscard.add(new TrainCard("wild"));
                                }
                                getCurrentPlayer().getTrainCards().replace("wild", numOfWild- numWild);
                                if(numOfColor >= num){
                                    System.out.println("purchasinggg");
                                    for(int i = 0; i < num; i++){
                                        cardsToDiscard.add(new TrainCard(color));
                                    }
                                    getCurrentPlayer() .getTrainCards().replace(color, numOfColor- num);
                                    gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1) .getTrainCards().replace(color, numOfColor- num);
                                }   
                                else if(numOfColor+numOfWild >= num) {
                                    System.out.println("purchasinggg");
                                    int numLeft = num-numOfColor;
                                    for(int i = 0; i < getCurrentPlayer().getTrainCards().get(color); i++){
                                        cardsToDiscard.add(new TrainCard(color));
                                    }
                                    getCurrentPlayer().getTrainCards().replace(color, 0);
                                    for(int i = 0; i < numLeft; i++){
                                        cardsToDiscard.add(new TrainCard("wild"));
                                    }
                                    getCurrentPlayer().getTrainCards().replace("wild", numOfWild-numLeft);
                                }
                        
                                //getCurrentPlayer().addRailroad(railroad);
                                railroad.claim(getCurrentPlayer());
                                getCurrentPlayer().addPoints(railroad.getPoints());
                                getCurrentPlayer().placetrains(railroad.getNumTotalTrains());
                                city1 = null;
                                city2 = null;
                                claimRouteState = 0;
                                turnState = 0; 
                                for(TrainCard t: cardsToDiscard)
                                    gameAccess.discardTrainCard(t);
                            }
                                city1 = null;
                                city2 = null;
                                claimRouteState = 0;
                                turnState = 0; 
                                tunnel = false;
                                clearTunnelCards();
                                canPurchaseTunnel = false;
                        
                            }
                            city1 = null;
                            city2 = null;
                            claimRouteState = 0;
                            turnState = 0;
                            tunnel = false;
                            clearTunnelCards();
                            canPurchaseTunnel = false;
                            continueButtonClicked = false;
                            isDouble = false;
                            colorChosen = false;
                            color = null;
                            isgrey = false;
                            hasOther = false;
                            System.out.println("EHUWHHS");
                            if(gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getNumTrains() <=2) {
                            	isLast = true;
                            	
                            	System.out.println("lastTurn");
                            	if(!firstlast) {
                            	lastTurn = 0;
                            	firstlast = true;
                            	System.out.println("first person has reached last");
                            	}
                            }
                       	 if(isLast) {
                       		 lastTurn++;
                       		 System.out.println("players last turn" + lastTurn);
                       	 }
                       	 if(isLast && lastTurn >= 4) {
                            		gameState = 2;
                       		// turnState = 10;
                            		System.out.println("end game");
                            }
                            gameAccess.incrementTurn();

                        }
                    }
                    
                }

                if (getCurrentPlayer().getTickets().size() >0)
                {
                    System.out.println("Please be true god " +  getCurrentPlayer().getTickets().get(0));
                    for (Ticket t: getCurrentPlayer().getTickets())
                   System.out.println( getCurrentPlayer().isTicketCompleted(
                        t
                        ));
                }
        
             }
             

             else if (turnState == 2)//destination ticket
             {
               
                
                if (rectangularInBounds(x, y, (int) (0.2001243 * getWidth()), (int) (0.33685 * getWidth()), (int) (0.29784 * getHeight()), (int)(0.4593*getHeight())) && destinationTicket1 != null)
                {
                    System.out.println("left DT clicked");
                    destinationTicket1Selected = ! destinationTicket1Selected;
                    
                    
                }

                else if (rectangularInBounds(x, y, (int) (0.599751 * getWidth()), (int) (0.735239 * getWidth()), (int) (0.29904 * getHeight()), (int)(0.4629186*getHeight())) && destinationTicket2 != null)
                {
                    System.out.println("right DT clicked");
                    destinationTicket2Selected = ! destinationTicket2Selected;

                  
                }

                else if (rectangularInBounds(x, y, (int) (0.3996 * getWidth()), (int) (0.54008 * getWidth()), (int) (0.599282 * getHeight()), (int)(0.7655502*getHeight())) && destinationTicket3 != null)
                {
                    System.out.println("bottom DT clicked");
                    destinationTicket3Selected = ! destinationTicket3Selected;

                   
                }

                if (! (destinationTicket1Selected || destinationTicket2Selected || destinationTicket3Selected))
                {
                    time1 = 0;
                }

                else if ( rectangularInBounds(x, y, (int) (getWidth() * 0.7383), (int) (getWidth() * 0.8986), (int) (getHeight() * 0.7416), (int) (getHeight() * 0.9007)))
                {
                    System.out.println("End turn clicked");

                    if ((!destinationTicket1Selected) && (!destinationTicket2Selected) && (!destinationTicket3Selected))
                    {
                        System.out.println("select a card first");
                       
                    }

                    else 
                    {
                        System.out.println("pickTicket turn ended");
                        if(destinationTicket1Selected)
                            getCurrentPlayer().addtickets(destinationTicket1);
                        if(destinationTicket2Selected)
                            getCurrentPlayer().addtickets(destinationTicket2);
                        if(destinationTicket3Selected)
                            getCurrentPlayer().addtickets(destinationTicket3);
                        turnState = 0;
                        destinationTicket1Selected = false;
                        destinationTicket2Selected = false;
                        destinationTicket3Selected = false;
                        destinationTicket1 = null;
                        destinationTicket2 = null;
                        destinationTicket3 = null;
                        
                        System.out.println("EHUWHHS");
                        if(gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getNumTrains() <=2) {
                        	isLast = true;
                        	
                        	System.out.println("lastTurn");
                        	if(!firstlast) {
                        	lastTurn = 0;
                        	firstlast = true;
                        	System.out.println("first person has reached last");
                        	}
                        }
                   	 if(isLast) {
                   		 lastTurn++;
                   		 System.out.println("players last turn" + lastTurn);
                   	 }
                   	 if(isLast && lastTurn >= 4) {
                        		gameState = 2;
                   		 //turnState = 10;
                        		System.out.println("end game");
                        }
                        gameAccess.incrementTurn();
                    }
                }

             }
             else if(turnState == 3){

                if (rectangularInBounds(x, y, (int) (0.8300*getWidth()), (int) (0.9*getWidth()), (int) (0.10224 * getHeight()),(int) (0.19224 * getHeight())) && getCurrentPlayer().getCardsPicked().size() <1)
                {
                    System.out.println("go back button clicked");
                    turnState = 0;
                }
                
                if(getCurrentPlayer().getCardsPicked().size() >= 2){
                    pickTrainCardState = 1;
                }//stops at 2 cards picked
                if(getCurrentPlayer().getCardsPicked().size() == 1){
                    if(getCurrentPlayer().getCardsPicked().get(0).getColor().equals("wild") && pickTrainCardState != 2){
                        pickTrainCardState = 1;
                    }
                }//wild case
                for(int i = 0; i < 6; i++){
                    if(rectangularInBounds(x, y, (int)((0.12927 + (0.23741-0.12927)*i)*getWidth()), (int)((0.23555 + (0.23741-0.12927)*i)*getWidth()), (int)(0.18468*getHeight()), (int)(0.45933*getHeight())) && pickTrainCardState != 1){
                        if(i == 0){
                            getCurrentPlayer().addToCardsPicked(gameAccess.drawTrainCard());
                            pickTrainCardState = 2;
                        }
                        else if(gameAccess.getGrid().get(i-1).getColor().equals("wild")){
                            if(!(getCurrentPlayer().getCardsPicked().size() > 0)){
                                getCurrentPlayer().addToCardsPicked(gameAccess.getGrid().remove(i-1));
                            }//wild case (doesnt let choose a wild as second card)
                        } else {
                            getCurrentPlayer().addToCardsPicked(gameAccess.getGrid().remove(i-1));
                        }
                    }
                }
                gameAccess.fillGrid();
                if ( rectangularInBounds(x, y, (int) (getWidth() * 0.7383), (int) (getWidth() * 0.8986), (int) (getHeight() * 0.7416), (int) (getHeight() * 0.9007)) && pickTrainCardState == 1)
                {
                    System.out.println("End turn clicked");
                    getCurrentPlayer().addTrainCards(getCurrentPlayer().getCardsPicked());
                    getCurrentPlayer().clearCardsPicked();
                    turnState = 0;
                    pickTrainCardState = 0;
                    System.out.println("EHUWHHS");
               	
               	 if(gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getNumTrains() <=2) {
                 	isLast = true;
                 	
                 	System.out.println("lastTurn");
                 	if(!firstlast) {
                 	lastTurn = 0;
                 	firstlast = true;
                 	System.out.println("first person has reached last");
                 	}
                 }
            	 if(isLast) {
            		 lastTurn++;
            		 System.out.println("players last turn" + lastTurn);
            	 }
            	 if(isLast && lastTurn >= 4) {
                 		gameState = 2;
            		 //turnState = 10;
                 		System.out.println("end game");
                 }
                 gameAccess.incrementTurn();
                }
             }//pick train card
            
             

             else if (turnState == 4) //building station 
            {
                if (rectangularInBounds(x, y, (int)(0.86575 * getWidth()), (int)(0.96575 * getWidth()), (int) (0.722009 * getHeight()), (int) (0.822009 * getHeight())))
                {
                    System.out.println("back button clicked");
                    buildStationCity = null;
                    buildStationState = 0;
                    turnState = 0;
                    buildStationColor = null;
                    for (String s: ColorsPicked)
                    {
                        int coloramt = getCurrentPlayer().getTrainCards().get(s) +1;
                                        getCurrentPlayer().getTrainCards().put(s,  coloramt );
                    }
                    ColorsPicked.clear();
                    repaint();
                    return;
                }
                if (getCurrentPlayer().getNumTrainStations() == 0)
                {
                    System.out.println("Sorry, you don't have any stations left to build");
                    return;
                }
               
                System.out.println("building a station");
                if ( CityDetector(x,y) != null && CityDetector(x, y).getStation() != 0)
                {
                    System.out.println(CityDetector(x, y).getName() + " has already been claimed");
                }
                else if (CityDetector(x, y) != null)
                {               
                        buildStationCity = CityDetector(x, y);
                }
                if (buildStationCity != null)
                {
                System.out.println("The selected city is " + buildStationCity.getName());
                

                   buildStationState = 1;
                }
                if (buildStationState == 1) // pay for station here
                {
                    
                   
                    int price = 4 - getCurrentPlayer().getNumTrainStations();
                 
                   {
                    
                        if (ColorsPicked.size() == price)
                        {
                            buildStationState = 2;
                            System.out.println("cost met");
                        }
                        else if (colorPicked(x, y) != null)
                            {
                                if (getCurrentPlayer().getTrainCards().get(colorPicked(x, y)) == 0)
                                {
                                    System.out.println("too broke");
                                }
                                else if (colorPicked(x, y).equals("wild"))
                                {
                                    ColorsPicked.add(colorPicked(x, y));
                                    int coloramt = getCurrentPlayer().getTrainCards().get(colorPicked(x, y)) -1;
                                    getCurrentPlayer().getTrainCards().put(colorPicked(x, y),  coloramt );
                                }
                               else if (ColorsPicked.size() == 0)
                               { 
                                System.out.println("hello please work");
                                ColorsPicked.add(colorPicked(x, y));

                                int coloramt = getCurrentPlayer().getTrainCards().get(colorPicked(x, y)) -1;
                                getCurrentPlayer().getTrainCards().put(colorPicked(x, y),  coloramt );
                                }
                                else
                                {
                                    boolean onlyOneColor = true;
                                    for (String s:ColorsPicked)
                                    {
                                        if (! s.equals("wild"))
                                        {
                                            System.out.println("we entered");
                                            if (! s.equals(colorPicked(x,y)))
                                            {
                                                System.out.println(s +" is not equal to " +colorPicked(x, y));
                                                onlyOneColor = false;
                                            }
                                        }
                                        else
                                        {
                                            System.out.println("It is wild");
                                        }

                                       
                                        
                                    }
                                    if (onlyOneColor)
                                    {
                                        System.out.println("the color we are adding is" + colorPicked(x, y));
                                        ColorsPicked.add(colorPicked(x, y));
                                        int coloramt = getCurrentPlayer().getTrainCards().get(colorPicked(x, y)) -1;
                                        getCurrentPlayer().getTrainCards().put(colorPicked(x, y),  coloramt );
                                    }


                                   

                                }
                               
                               

                            } 
                }
                    
                    }

                   if (buildStationState == 2)
                    {
                       
                    }
            if (buildStationState == 2)
            {

                if (rectangularInBounds(x, y, (int) (0.865133 * getWidth()), (int) (0.9645 * getWidth()), (int) (0.87200 * getHeight()), (int) (0.97009 * getHeight())))
                {
                    System.out.println("end turn clicked");
                    if (buildStationCity != null)
                    {

                    
                    System.out.println("End turn clicked and city not null");
                    

                    turnState = 0;

                    for (City c: gameAccess.getCities())
                    {
                        if (c.equals(buildStationCity))
                        {
                            System.out.println("We're going to mark " + c.getName());
                            System.out.println("The current player turn is " + gameAccess.getPlayerTurn());
                            c.placeStation(getCurrentPlayer());
                        }
                    }
                    buildStationCity = null;
                    for (String s: ColorsPicked)
                    {
                        gameAccess.discardTrainCard(new TrainCard(s));
                    }
                    ColorsPicked.clear();
                    getCurrentPlayer().decrementTrainStations();
                    
                    System.out.println("EHUWHHS");
                                   	
                   
               	 if(gameAccess.getPlayers().get(gameAccess.getPlayerTurn()-1).getNumTrains() <=2) {
                 	isLast = true;
                 	
                 	System.out.println("lastTurn");
                 	if(!firstlast) {
                 	lastTurn = 0;
                 	firstlast = true;
                 	System.out.println("first person has reached last");
                 	}
                 }
            	 if(isLast) {
            		 lastTurn++;
            		 System.out.println("players last turn" + lastTurn);
            	 }
            	 if(isLast && lastTurn >= 4) {
                 		gameState = 2;
            		 //turnState = 10;
                 		System.out.println("end game");
                 }
                 gameAccess.incrementTurn();
                    }
                }





            }
            }
            else if(turnState == 10){
                if (rectangularInBounds(x, y, (int)(0.8154133001864512 * getWidth()), (int)(0.8954133001864512 * getWidth()), (int)(0.777511961722488 * getHeight()), (int) (0.902511961722488 * getHeight())) && ableToPurchase)
                {
                    Railroad railroad = gameAccess.getMap().getRailroad(city1, city2);
                    getCurrentPlayer().addRailroad(railroad);
                    ableToPurchase = false;
                    gameAccess.incrementTurn();
                    if(getCurrentPlayer().getPlayerNum() == 4) {
                    	gameState = 2;
                    }
                }
                
            }//NOT DONE
               
            
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



        }
    }//mouse clicked
     
    @Override
    public void mousePressed(MouseEvent e) {
       
    }//mousePressed

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }//mouseRelease

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }//mouseEntered

    @Override
    public void mouseExited(MouseEvent e) {

    }//mouseExited

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


    }//rectangularInBounds

    public void beginTurnUI(Graphics g)
    {

        g.setColor(Color.black);
        
        g.setColor(gameAccess.getPlayers().get(0).getColor());
        g.drawString("Player "+1+" Points: "+gameAccess.getPlayers().get(0).getPoints(), (int) (0.76196*getWidth()), (int) (0.20630*getHeight()));
        g.setColor(gameAccess.getPlayers().get(1).getColor());
        g.drawString("Player "+2+" Points: "+gameAccess.getPlayers().get(1).getPoints(), (int) (0.76196*getWidth()), (int) (0.27630*getHeight()));
        g.setColor(gameAccess.getPlayers().get(2).getColor());
        g.drawString("Player "+3+" Points: "+gameAccess.getPlayers().get(2).getPoints(), (int) (0.76196*getWidth()), (int) (0.35239*getHeight()));
        g.setColor(gameAccess.getPlayers().get(3).getColor());
        g.drawString("Player "+4+" Points: "+gameAccess.getPlayers().get(3).getPoints(), (int) (0.76196*getWidth()), (int) (0.42848*getHeight()));
        g.setColor(Color.BLACK);

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
               
    }//beginTurnUI

    public void claimRouteUI(Graphics g)
    {
        g.setColor(Color.black);
        g.drawString("Claim Route", (int) (0.65942*getWidth()), (int) (0.04785*getHeight()));

        g.drawString("Click two cities to claim the route that", (int) (0.65942*getWidth()), (int) (0.09785*getHeight()) );
        g.drawString("connects them", (int) (0.65942*getWidth()), (int) (0.1205*getHeight()) );

        g.drawString("City 1:", (int) (0.65942*getWidth()), (int) (0.1605*getHeight()) );

        if (city1 != null)
        {
            g.drawString( city1.getName(),(int) (0.70942*getWidth()), (int) (0.1605*getHeight()) );
            g.setColor(setCurrentPlayerColor());
            g.fillOval( (int) (city1.getCoords()[0] * getWidth() * 0.6 * 205 / 154.792222) - (int)(getWidth()*0.025)/2, (int)(city1.getCoords()[1] * getHeight() * 0.7 * 172 / 133.694) - (int)(getHeight()* 0.04)/2, (int)(getWidth()*0.025), (int)(getHeight()* 0.04) );
            g.setColor(Color.black);
        }

         g.drawString("City 2:", (int) (0.65942*getWidth()), (int) (0.205*getHeight()) );

         if (city2 != null)
         {
            g.drawString( city2.getName(),(int) (0.70942*getWidth()), (int) (0.205*getHeight()) );        
            g.setColor(setCurrentPlayerColor());
            g.fillOval( (int) (city2.getCoords()[0] * getWidth() * 0.6 * 205 / 154.792222) - (int)(getWidth()*0.025)/2, (int)(city2.getCoords()[1] * getHeight() * 0.7 * 172 / 133.694) - (int)(getHeight()* 0.04)/2, (int)(getWidth()*0.025), (int)(getHeight()* 0.04) );
            g.setColor(Color.black);
        }
         if( city1 != null && city2 == null) {
        	 g.drawString( "Cities are not connected, please pick again",(int) (0.70942*getWidth()), (int) (0.205*getHeight()) );
         }

         g.drawRect((int) (0.9242*getWidth()), (int) (0.0231*getHeight()), (int) (getWidth()*0.05), (int) (getHeight()*0.04)); 
         g.drawString("Go Back",(int) (0.9262*getWidth()),(int) (0.0471*getHeight()));//draws clear button

         g.drawRect((int) (0.9242*getWidth()), (int) (0.0931*getHeight()), (int) (getWidth()*0.05), (int) (getHeight()*0.04)); 
         g.drawString("Clear",(int) (0.9292*getWidth()),(int) (0.1171*getHeight()));//draws go back button


         g.drawRect((int)(0.8154133001864512 * getWidth()) , (int)(0.777511961722488 * getHeight()), (int) (0.08 * getWidth()), (int) (0.125 * getHeight())) ;
         if(continueButtonClicked == false)
            g.drawString("Continue",(int)(0.8254133001864512 * getWidth()),(int)(0.839511961722488 * getHeight()));// draws contineu button
         else  
            g.drawString("End Turn",(int)(0.8254133001864512 * getWidth()),(int)(0.839511961722488 * getHeight()));// draws end turn button

            if (city1 != null && city2 != null)
         {
            City alphaFirst;
            City alphaSecond;
            if (city1.getName().compareTo(city2.getName()) < 0)
            {
                alphaFirst = city1;
                alphaSecond = city2;
                System.out.println("The first city is "+alphaFirst.getName());

            }

            else 
            {
                alphaFirst = city2;
                alphaSecond = city1;
                System.out.println("The first city is " + alphaFirst.getName());

            }

            String combineCityNames = alphaFirst.getName() + " " + alphaSecond.getName();

           System.out.println(combineCityNames);
            for (Railroad r : gameAccess.getRailroads())
            {
                String railRoad = r.getFirst() + " " + r.getSecond();
                if (railRoad.equals(combineCityNames))
                {
                    System.out.println("Match detected");
                }


            }
            if(gameAccess.getMap().railroadExists(city1, city2) != null) {
                if(gameAccess.getMap().getRailroad(city1, city2).getColor().equals("grey") && (color == null || color.equals("grey")) || gameAccess.getMap().getRailroad(city1, city2).isDouble() && color == null){
               		if(gameAccess.getMap().getRailroad(city1, city2).getColor().equals("grey"))
                        isgrey = true;
               		g.drawString("Please choose a color from your hand", (int)(0.65783320*getWidth()), (int)(0.5271428571*getHeight()));     	
                }
                else if(gameAccess.getMap().getRailroad(city1, city2).getColor().equals("grey") && (!color.equals("grey") || color != null) || (gameAccess.getMap().getRailroad(city1, city2).isDouble() && color != null)){
                    g.drawString("Color chosen: " + color, (int)(0.65783320*getWidth()), (int)(0.5271428571*getHeight()));     	
                }
                 else
                  isgrey = false;
 
            }
            
      
               
            }
            
         if(claimRouteState == 3 && canPurchase()){
            if(canPurchase()){
                double cardWidth = 0.69578-0.61570;
                double cardHeight = 0.44531-0.26406;
                for(int i = 0; i < tunnelCards.size(); i++){
                    g.drawImage(tunnelCards.get(i).getImage(), (int)((0.61570 + cardWidth*i)*getWidth()), (int)( 0.26406*getHeight()), (int)(cardWidth*getWidth()), (int)(cardHeight*getHeight()), null);
                }
            }
            if(!continueButtonClicked){
                g.drawString("Click continue to find out if you can purchase.", (int)(0.656308*getWidth()), (int)(0.57416*getHeight()));
            }
            else if(canPurchaseTunnel && continueButtonClicked){
                g.drawString("Can Purchase! Click the button to end turn.", (int)(0.656308*getWidth()), (int)(0.57416*getHeight()));
            } else {
                g.drawString("Can't Purchase! Click the button to end turn.", (int)(0.656308*getWidth()), (int)(0.57416*getHeight()));
            }
        }
        if(isDouble && invalidColor && color != null){
            g.drawString("Can't use this color, pick again", (int)(0.6625233064014916*getWidth()), (int)(0.3803827751196172*getHeight())); 
        } else if((city1 != null && city2 != null && color != null && getCurrentPlayer().getNumTrains() < gameAccess.getMap().getRailroad(city1, city2, color).getNumTotalTrains()) || hasOther || city1 != null && city2 != null && !canPurchase() && color != null && !color.equals("grey")){
            g.drawString("You can't afford this, choose something else", (int)(0.6625233064014916*getWidth()), (int)(0.3803827751196172*getHeight())); 
            //claimRouteState = 1;    //change this to something else (maybe make a new claimroutestate)	
            color = null;
            isgrey = false; //this whole thing needs to reset everything
        }else if(taken || (city1 != null && city2 != null && color != null && gameAccess.getMap().getRailroad(city1, city2, color).getPlayer() != null && !colorChosen && (!isgrey && gameAccess.getMap().getRailroad(city1, city2).getSisterRailroad() == null))){ //needs to use railroad(str, str, str (color))
            g.drawString("This route is taken, choose something else", (int)(0.6625233064014916*getWidth()), (int)(0.3803827751196172*getHeight())); 
        } else if(city1 != null && city2 != null && (!gameAccess.getMap().getRailroad(city1, city2).isTunnel() && canPurchase()) && !invalidColor && color != null && !taken){
            g.drawString("Can purchase!", (int)(0.6625233064014916*getWidth()), (int)(0.3803827751196172*getHeight())); 
        }
    }//claimRouteUI

    public void generateTunnelCards(){
        if(tunnelCards.size() == 0)
            for(int i = 0; i < 3; i++)
                tunnelCards.add(gameAccess.drawTrainCard());
        System.out.println("CAN PURCHASE?" + canPurchase());
    }//generateTunnelCards

    public void clearTunnelCards(){
        for(TrainCard t: tunnelCards)
            gameAccess.discardTrainCard(t);
        tunnelCards.clear();
    }//clearTunnelCards

    public void pickTicketUI(Graphics g)
    {
       
        g.setColor((Color.white));
        g.fillRect(getWidth()/10, getHeight()/10, (int)(getWidth()*0.8), (int)(getHeight()*0.8));
        g.setColor(Color.black);
        
        
        g.drawString("Pick at least one ticket to keep. You must select at least one ticket. Selected tickets spin.", (int)(0.30640149*getWidth()), (int)(0.16507 * getHeight()));
        g.drawRect((int)(0.738020833 * getWidth()) , (int) (0.7413962635201573 * getHeight()), (int) (0.16 * getWidth()), (int) (0.16 * getHeight()));
        g.drawString("End Turn", (int) (0.763206 * getWidth()), (int) (getHeight() * 0.76794));
        if(destinationTicket1 != null && !destinationTicket1Selected)
            g.drawImage(destinationTicket1.getImage(), (int) (getWidth() * 0.2), (int) (getHeight()*0.3), null);
        if(destinationTicket2 != null && !destinationTicket2Selected)
            g.drawImage(destinationTicket2.getImage(), (int) (getWidth() * 0.6), (int) (getHeight()*0.3), null);
        if(destinationTicket3 != null && !destinationTicket3Selected)
            g.drawImage(destinationTicket3.getImage(), (int) (getWidth() * 0.4), (int) (getHeight()*0.6), null);

       
        
            if (destinationTicket1Selected)
            {
               // g.setColor(Color.green);
               // g.drawRect( (int) (getWidth() * 0.2), (int) (getHeight()*0.3), (int)(getWidth() * 0.144), (int) (getHeight() * 0.16));
            //    System.out.println("ticket 1 is selected");
                g.setColor(Color.black);
                rotatingDestinationTicket(g, destinationTicket1, -0.2227, -0.102857, time1); 
                
            }

             if (destinationTicket2Selected)
            {
                
               // g.drawRect( (int) (getWidth() * 0.6), (int) (getHeight()*0.3), (int)(getWidth() * 0.14), (int) (getHeight() * 0.16));
          //      System.out.println("ticket 2 is selected");

                
                rotatingDestinationTicket(g, destinationTicket2, +0.1727, -0.122857, time1); 
            }

             if (destinationTicket3Selected)
            {
             //   g.setColor(Color.green);
               // g.drawRect( (int) (getWidth() * 0.4), (int) (getHeight()*0.6), (int)(getWidth() * 0.14), (int) (getHeight() * 0.16));
            //    System.out.println("ticket 3 is selected");
                rotatingDestinationTicket(g, destinationTicket3, -0.03, 0.20, time1); 

            }
    }//pickTicketUI




    public void rotatingDestinationTicket(Graphics g, Ticket t, double xshift, double yshift, int tim)
    {
        
             heading = 0 - Math.toRadians((((double) tim)/4)/200 * 180 - 10);
             //System.out.println("heading: " + heading);
         pitch = 3.0;// Math.toRadians( -(time/4 -100)*(time/4 - 100)*0.003+30);
         headingTransform = new Matrix3(new double[] {
                 Math.cos(heading), 0, -Math.sin(heading),
                 0, 1, 0,
                 Math.sin(heading), 0, Math.cos(heading)
             });
         
         pitchTransform = new Matrix3(new double[] {
                 1, 0, 0,
                 0, Math.cos(pitch), Math.sin(pitch),
                 0, -Math.sin(pitch), Math.cos(pitch)
             });
         transform = headingTransform.multiply(pitchTransform);
        
         BufferedImage img = 
         	    new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
         Square s = DestinationCardBackSquare;

             
     	 Vertex v1 = transform.transform(s.a);
     	 Vertex v2 = transform.transform(s.b);
          Vertex v3 = transform.transform(s.c);
          Vertex v4 = transform.transform(s.d);
     	 
          Vertex ab = new Vertex(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
          Vertex ac = new Vertex(v1.x - v4.x, v1.y - v4.y, v1.z - v4.z);
          Vertex norm = new Vertex(
                  ab.y * ac.z - ab.z * ac.y,
                  ab.z * ac.x - ab.x * ac.z,
                  ab.x * ac.y - ab.y * ac.x
             );
             double normalLength =
                 Math.sqrt(norm.x * norm.x + norm.y * norm.y + norm.z * norm.z);
             norm.x /= normalLength;
             norm.y /= normalLength;
             norm.z /= normalLength;
         
             double angleCos = Math.abs(norm.z);
             Vertex transformedV;
             
             BufferedImage flippedT = flipImageHorizontally((BufferedImage)(t.getImage()));
             for (int y = Math.max(-DestinationCardBack.getHeight()/2, -((BufferedImage)t.getImage()).getHeight()/2); y < Math.min(DestinationCardBack.getHeight()/2, ((BufferedImage)t.getImage()).getHeight()/2); y++)
             {
                for (int x = 
                Math.max(-DestinationCardBack.getWidth()/2, -((BufferedImage)t.getImage()).getWidth()/2);
                x < Math.min(DestinationCardBack.getWidth()/2, ((BufferedImage)t.getImage()).getWidth()/2) ; x++)
            	 {
            		 transformedV = transform.transform(new Vertex(x, y ,0));
            		 int actualy = (int) transformedV.y + getHeight()/2;
            		 int actualx = (int) transformedV.x + getWidth()/2 ;
            		// transformedV = transform.transform(new Vertex(actualx, actualy, 1000));
            		 //System.out.println("actualx :" + actualx + " actualy: " + actualy);
            		 
            		 if (actualx < getWidth() && 0 < actualx  && actualy  < getHeight() && 0 < actualy )
            		 {
            			
            			
            			if (norm.z > 0)
            				 img.setRGB( (int) (actualx),
                             (int) ( actualy ), 
                             getShade((new Color(DestinationCardBack.getRGB(x + DestinationCardBack.getWidth()/2, y + DestinationCardBack.getHeight()/2), true)), angleCos).getRGB());
            			 else
            				 img.setRGB( (int) (actualx), 
                             (int) ( actualy), 
                             getShade((new Color(((BufferedImage) flippedT).getRGB(x + ((BufferedImage) t.getImage()).getWidth()/2, 
                             y +  + ((BufferedImage) flippedT).getHeight()/2), true)), angleCos).getRGB());
            				 
            		 }	 
            		 
            		 
            	 }
            	 
            	 
             }

             g.drawImage(img, (int)(xshift*getWidth()), (int)(yshift*getHeight()) ,null);
    }

    public static Color getShade(Color color, double shade) {
        double redLinear = Math.pow(color.getRed(), 2.4) * shade;
          double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
          double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;

          int red = (int) Math.pow(redLinear, 1/2.4);
          int green = (int) Math.pow(greenLinear, 1/2.4);
          int blue = (int) Math.pow(blueLinear, 1/2.4);

          return new Color(red, green, blue);
  }

  public BufferedImage flipImageHorizontally(BufferedImage original) {
    int width = original.getWidth();
    int height = original.getHeight();

    BufferedImage flipped = new BufferedImage(width, height, original.getType());
    Graphics2D g = flipped.createGraphics();

    // Reflect across the horizontal axis
    AffineTransform transform = AffineTransform.getScaleInstance(1, -1);
    transform.translate(0, -height);

    g.drawImage(original, transform, null);
    g.dispose();

    return flipped;
}
    
    public void pickTrainCardUI(Graphics g)
    {
        int counter = 0;
        for(TrainCard t: gameAccess.getGrid()){
            if(t.getColor().equals("wild")){
                counter++;
            }
        }
        if(counter > 2){
            gameAccess.replaceGrid();
        }
        
        g.setColor((Color.white));
        g.fillRect(getWidth()/10, getHeight()/10, (int)(getWidth()*0.8), (int)(getHeight()*0.8));
        g.drawImage(gameAccess.getTrainCardBack(), (int)(0.12927*getWidth()), (int)(0.18468*getHeight()), (int)((0.23741-0.12927)*getWidth()), (int)((0.56220-0.28468)*getHeight()), null);
        g.setColor(Color.black);

        if(getCurrentPlayer().getCardsPicked().size() < 1){
            g.drawRect((int) (0.8300*getWidth()), (int) (0.10224 * getHeight()), (int) (0.07*getWidth()), (int) (0.09 * getHeight())); // go back button
            g.drawString("Go back", (int) (0.83779*getWidth()), (int) (0.136247*getHeight()));
        }
       
       
        g.drawString("Pick two train cards. If chosen a wild, only one.", (int)(0.30640149*getWidth()), (int)(0.16507 * getHeight()));
        g.drawRect((int)(0.738020833 * getWidth()) , (int) (0.7413962635201573 * getHeight()), (int) (0.16 * getWidth()), (int) (0.16 * getHeight()));
        g.drawString("End Turn", (int) (0.763206 * getWidth()), (int) (getHeight() * 0.76794));
        for(int i = 0; i < 5; i++){
            g.drawImage(gameAccess.getGrid().get(i).getImage(), (int)((0.23741 + (0.23741-0.12927)*i)*getWidth()), (int)(0.18468*getHeight()), (int)((0.23741-0.12927)*getWidth()), (int)((0.56220-0.28468)*getHeight()), null);
        }
        for(int i = 0; i < getCurrentPlayer().getCardsPicked().size(); i++){
            g.drawImage(getCurrentPlayer().getCardsPicked().get(i).getImage(), (int)((0.21379 + (0.23741-0.12927)*i)*getWidth()), (int)(0.52870*getHeight()), (int)((0.23741-0.12927)*getWidth()), (int)((0.56220-0.28468)*getHeight()), null);
        }

    }//train card picking

    public void buildStationUI(Graphics g) 
    {
        System.out.println("We are printing...");

        g.drawString("First, pick your city. Then, pick the cards.", (int) (0.637041 * getWidth()), (int) (0.04784 * getHeight()));

        g.drawString("Selected City: ", (int) (0.637041 * getWidth()), (int) (0.09784 * getHeight()));

        g.drawString("Selected Color: ", (int) (0.637041 * getWidth()), (int) (0.14784 * getHeight()));

        // end turn button
        g.setColor(Color.black);
        g.drawRect((int) (0.86575 * getWidth()), (int) (0.872009 * getHeight()), (int)(getWidth()*0.1), (int)(getHeight()*0.1)); // end turn button
        g.drawString("Claim station", (int) (0.8775 * getWidth()), (int) (0.90909 * getHeight()));


        g.drawRect((int) (0.86575 * getWidth()), (int) (0.722009 * getHeight()), (int)(getWidth()*0.1), (int)(getHeight()*0.1)); // back button
        g.drawString("Go back/Clear", (int) (0.8775 * getWidth()), (int) (0.75209 * getHeight()));
      
        if (buildStationCity != null)
        {
            g.drawString( buildStationCity.getName(), (int) (0.726041 * getWidth()), (int) (0.09784 * getHeight()));
        g.setColor(setCurrentPlayerColor());
            g.fillOval( (int) (buildStationCity.getCoords()[0] * getWidth() * 0.6 * 205 / 154.792222) - (int)(getWidth()*0.025)/2, (int)(buildStationCity.getCoords()[1] * getHeight() * 0.7 * 172 / 133.694) - (int)(getHeight()* 0.04)/2, (int)(getWidth()*0.025), (int)(getHeight()* 0.04) );
            g.setColor(Color.black);
         }

         if (buildStationColor != null)
         {
            System.out.println("We're in, and the buildstation color is" + buildStationColor);
            g.drawString(buildStationColor, (int) (0.726041 * getWidth()), (int) (0.14784 * getHeight()));
         }
         int increment = (int) (0.07*getWidth());

        if (ColorsPicked.size() >0)
         {
            g.drawString(ColorsPicked.toString(), (int) (0.637041 * getWidth()), (int) (0.19784 * getHeight()));
         }

    }//buildStationUI

    public void drawStations(Graphics g)
    {
        
        for (City c : gameAccess.getCities())
        {
           if (c.getStation() != 0)
           {
               g.setColor( gameAccess.getPlayers().get(c.getStation()-1).getColor());
           g.fillOval( (int) (c.getCoords()[0] * getWidth() * 0.6 * 205 / 153.792222) - (int)(getWidth()*0.015)/2, (int)(c.getCoords()[1] * getHeight() * 0.7 * 172 / 132.694) - (int)(getHeight()* 0.024)/2, (int)(getWidth()*0.015), (int)(getHeight()* 0.024) );
           g.setColor(Color.black);
           //(int)(getWidth()*0.0125)/2; - (int)(getHeight()* 0.02)/2
           }
       }
    }//drawStations

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
            
            if (distance < 15)
            {
                System.out.println("City detected: " + c.getName());
                return c;
            }
        }
        return null;
    }//CityDetector

    public Player getCurrentPlayer(){
        return gameAccess.getPlayers().get(gameAccess.getPlayerTurn() - 1);
    }//getCurrentPlayer

    public String colorPicked(double x, double y)
    {
        String color = null;
        if(rectangularInBounds(x,y, (int)(0.008079552517091361*getWidth()),(int)(0.07022995649471722*getWidth()), (int)(0.7523923444976076*getHeight()),(int)(0.9784688995215312*getHeight()))) {
            color = "red";
            System.out.println(color);
        }
        if(rectangularInBounds(x,y, (int)(0.0783095090118*getWidth()),(int)(0.139838649471722*getWidth()), (int)(0.74521531*getHeight()),(int)(0.9760765550239234*getHeight()))) {
            color = "orange";
            System.out.println(color);
        }
        if(rectangularInBounds(x,y, (int)(0.1485394655065258*getWidth()),(int)(0.2113113735239279*getWidth()), (int)(0.7511961722488039*getHeight()),(int)(0.9772727272727273*getHeight()))) {
            color = "yellow";
            System.out.println(color);
        }
        if(rectangularInBounds(x,y, (int)(0.2144188937228092*getWidth()),(int)(0.27967681789931637*getWidth()), (int)(0.7511961722488039*getHeight()),(int)(0.9772727272727273*getHeight()))) {
            color = "green";
            System.out.println(color);
        }
        if(rectangularInBounds(x,y, (int)(0.2865133623368552*getWidth()),(int)(0.3449347420758235*getWidth()), (int)(0.7511961722488039*getHeight()),(int)(0.9772727272727273*getHeight()))) {
            color = "blue";
            System.out.println(color);
        }
        if(rectangularInBounds(x,y, (int)(0.35487880671224364*getWidth()),(int)(0.4170292106898695*getWidth()), (int)(0.7511961722488039*getHeight()),(int)(0.9772727272727273*getHeight()))) {
            color = "pink";
            System.out.println(color);
        }
        if(rectangularInBounds(x,y, (int)(0.4244872591671846*getWidth()),(int)(0.4829086389061529*getWidth()), (int)(0.7511961722488039*getHeight()),(int)(0.9772727272727273*getHeight()))) {
            color = "white";
            System.out.println(color);
        }
        if(rectangularInBounds(x,y, (int)(0.4916096954630205*getWidth()),(int)(0.5550031075201989*getWidth()), (int)(0.7511961722488039*getHeight()),(int)(0.9772727272727273*getHeight()))) {
            color = "black";
            System.out.println(color);
        }
        if(rectangularInBounds(x,y, (int)(0.5587321317588564*getWidth()),(int)(0.6246115599751398*getWidth()), (int)(0.7511961722488039*getHeight()),(int)(0.9772727272727273*getHeight()))) {
            color = "wild";
            System.out.println(color);
        }
        return color;
    }//colorPicked

    public void endGame(Graphics g) 
    {
        if(!isLookingAtMap)
            this.getFrame().endScreen(gameAccess); //this should make the game end and switch to the new endscreen
    	// Player first = null;
    	// Player second = null;
    	// Player third = null;
    	// Player fourth = null;
    	
    	// ArrayList<Player> plays = new ArrayList<>();
    	// plays.addAll(gameAccess.getPlayers());
    	
    	// for(Player x : plays) {
    	// 	x.addPoints(x.getNumTrainStations()*4);
    	//  // needs to check destination tickets
    	// 	for(Ticket y: x.getTickets()) {
    	// 		if(x.checkTickets(y)) {
    	// 			x.addPoints(y.getPointValue());
    	// 		}
    	// 		else 
    	// 			x.addPoints(-y.getPointValue());
    	// 	}
    	// 	if(x.hasEuropeanExpress())
    	// 		x.addPoints(10);
    		
    	// System.out.println("Player "+x.getPlayerNum()+":"+x.getPoints());
    	// }
    	
    	// ArrayList<Integer> places = new ArrayList<>();
    	// int p1points = gameAccess.getPlayers().get(0).getPoints();
    	// int p2points = gameAccess.getPlayers().get(1).getPoints();
    	// int p3points = gameAccess.getPlayers().get(2).getPoints();
    	// int p4points = gameAccess.getPlayers().get(3).getPoints();
    	
    	// places.add(p1points);
    	// places.add(p2points);
    	// places.add(p3points);
    	// places.add(p4points);
    	
    	// Collections.sort(places);
    
    	
    	// if(places.get(3) == p1points) 
    	// 	first = gameAccess.getPlayers().get(0);
    	// else if(places.get(3) == p2points) 
    	// 	first = gameAccess.getPlayers().get(1);
    	// else if(places.get(3) == p3points) 
    	// 	first = gameAccess.getPlayers().get(2);
    	// else if(places.get(3) == p4points) 
    	// 	first = gameAccess.getPlayers().get(3);
    	
    	// if(places.get(2) == p1points) 
    	// 	second = gameAccess.getPlayers().get(0);
    	// else if(places.get(2) == p2points) 
    	// 	second = gameAccess.getPlayers().get(1);
    	// else if(places.get(2) == p3points) 
    	// 	second = gameAccess.getPlayers().get(2);
    	// else if(places.get(2) == p4points) 
    	// 	second = gameAccess.getPlayers().get(3);
    	
    	// if(places.get(1) == p1points) 
    	// 	third = gameAccess.getPlayers().get(0);
    	// else if(places.get(1) == p2points) 
    	// 	third = gameAccess.getPlayers().get(1);
    	// else if(places.get(1) == p3points) 
    	// 	third = gameAccess.getPlayers().get(2);
    	// else if(places.get(1) == p4points) 
    	// 	third = gameAccess.getPlayers().get(3);
    	
    	// if(places.get(0) == p1points) 
    	// 	fourth = gameAccess.getPlayers().get(0);
    	// else if(places.get(0) == p2points) 
    	// 	fourth = gameAccess.getPlayers().get(1);
    	// else if(places.get(0) == p3points) 
    	// 	fourth = gameAccess.getPlayers().get(2);
    	// else if(places.get(0) == p4points) 
    	// 	fourth = gameAccess.getPlayers().get(3);
    		
    	// // NOT DONE
    		
    	// /*first = getCurrentPlayer();
    	// second = getCurrentPlayer();
    	// third  = getCurrentPlayer();
    	// fourth  = getCurrentPlayer();*/
    	
    	
    	// Font font = new Font("Cantarell Extra Bold", Font.BOLD, Math.abs((int)( 0.20947416762342135*getHeight() -  0.16991963260619977*getHeight()))); 
    	// g.setFont(font);
    	// g.drawString("Player " +first.getPlayerNum(),(int)(0.0853079552517091361*getWidth()),(int)( 0.3908133971291866*getHeight()));
    	// g.drawString("Player " +second.getPlayerNum(),(int)(0.331541330018645*getWidth()),(int)( 0.5418660287081339*getHeight()));
    	// g.drawString("Player " +third.getPlayerNum(),(int)(0.6026041666666667*getWidth()),(int)( 0.6188200589970502*getHeight()));
    	// g.drawString("Player " +fourth.getPlayerNum(),(int)(0.8344791666666667*getWidth()),(int)( 0.7887610619469026*getHeight()));
    	
    	// g.drawString("Points: "+first.getPoints(),(int)(0.08633312616532007*getWidth()),(int)( 0.5143540669856459*getHeight()));
    	// g.drawString("Points: "+second.getPoints(),(int)(0.331541330018645*getWidth()),(int)( 0.618421052631579*getHeight()));
    	// g.drawString("Points: "+third.getPoints(),(int)(0.6026041666666667*getWidth()),(int)( 0.7105263157894737*getHeight()));
    	// g.drawString("Points: "+fourth.getPoints(),(int)(0.8344791666666667*getWidth()),(int)( 0.8444976076555024*getHeight()));
    	
    	// g.drawString("Click for",(int)(0.08633312616532007*getWidth()),(int)( 0.6303827751196173*getHeight()));
    	// g.drawString("breakdown",(int)(0.08633312616532007*getWidth()),(int)( 0.6674641148325359*getHeight()));
    	// g.drawRect((int)(0.09384711000621504*getWidth()),(int)(0.7476076555023924*getHeight()),(int)(0.18272218769422002*getWidth())-(int)(0.09384711000621504*getWidth()),(int)(0.8074162679425837*getHeight())-(int)(0.7476076555023924*getHeight()));
    	
    	// g.drawString("Click for",(int)(0.331541330018645*getWidth()),(int)( 0.6901913875598086*getHeight()));
    	// g.drawString("breakdown",(int)(0.331541330018645*getWidth()),(int)( 0.7234528347329473*getHeight()));
    	// g.drawRect((int)(0.341541330018645*getWidth()),(int)(0.7990430622009569*getHeight()),(int)(0.42272218769422002*getWidth())-(int)(0.331541330018645*getWidth()),(int)(0.8582384782*getHeight())-(int)(0.7990430622009569*getHeight()));
    	
    	// g.drawString("Click for",(int)(0.6026041666666667*getWidth()),(int)( 0.7691387559808612*getHeight()));
    	// g.drawString("breakdown",(int)(0.6026041666666667*getWidth()),(int)( 0.802354456*getHeight()));
    	// g.drawRect((int)(0.6128589185829708*getWidth()),(int)(0.8564593301435407*getHeight()),(int)(0.704164077066501*getWidth())-(int)(0.6128589185829708*getWidth()),(int)(0.9127751196172249*getHeight())-(int)(0.8564593301435407*getHeight()));
    	
    	// g.drawString("Click for",(int)(0.8344791666666667*getWidth()),(int)( 0.8820095693779905*getHeight()));
    	// g.drawString("breakdown",(int)(0.8344791666666667*getWidth()),(int)( 0.9120095693779905*getHeight()));
    	// g.drawRect((int)(0.84000*getWidth()),(int)(0.9413875598086124*getHeight()),(int)(0.93324654*getWidth())-(int)(0.8400000*getWidth()),(int)(0.9900000*getHeight())-(int)(0.9413875598086124*getHeight()));
    	
    }//endGame

    public Color setCurrentPlayerColor()
    {
        return getCurrentPlayer().getColor();
    }//setCurrentPlayerColor


    public void pickStationRoutesUI(Graphics g)
    {
        g.setColor(Color.black);
        System.out.println("turn state 10");
        //g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(woodenBg, 0, 0, getWidth(), getHeight(), null);

        g.drawString("Player " + getCurrentPlayer().getPlayerNum(), (int) (0.65942*getWidth()), (int) (0.04785*getHeight()));

        g.drawString("Choose " + (3-getCurrentPlayer().getNumTrainStations()) + " railroads for ", (int) (0.65942*getWidth()), (int) (0.09785*getHeight()) );
        g.drawString("your stations", (int) (0.65942*getWidth()), (int) (0.1205*getHeight()) );

        g.drawString("City 1:", (int) (0.65942*getWidth()), (int) (0.1605*getHeight()) );

        if (city1 != null)
        {
            g.drawString( city1.getName(),(int) (0.70942*getWidth()), (int) (0.1605*getHeight()) );
            g.setColor(setCurrentPlayerColor());
            g.fillOval( (int) (city1.getCoords()[0] * getWidth() * 0.6 * 205 / 154.792222) - (int)(getWidth()*0.025)/2, (int)(city1.getCoords()[1] * getHeight() * 0.7 * 172 / 133.694) - (int)(getHeight()* 0.04)/2, (int)(getWidth()*0.025), (int)(getHeight()* 0.04) );
            g.setColor(Color.black);
        }

         g.drawString("City 2:", (int) (0.65942*getWidth()), (int) (0.205*getHeight()) );

         if (city2 != null)
         {
            g.drawString( city2.getName(),(int) (0.70942*getWidth()), (int) (0.205*getHeight()) );        
            g.setColor(setCurrentPlayerColor());
            g.fillOval( (int) (city2.getCoords()[0] * getWidth() * 0.6 * 205 / 154.792222) - (int)(getWidth()*0.025)/2, (int)(city2.getCoords()[1] * getHeight() * 0.7 * 172 / 133.694) - (int)(getHeight()* 0.04)/2, (int)(getWidth()*0.025), (int)(getHeight()* 0.04) );
            g.setColor(Color.black);
        }
         if(city1 != null && city2 == null) {
        	 g.drawString( "Cities are not connected, please pick again",(int) (0.70942*getWidth()), (int) (0.205*getHeight()) );
         } else if(city1 != null && city2 != null &&!(city1.getStation() == getCurrentPlayer().getPlayerNum()-1 || city2.getStation() == getCurrentPlayer().getPlayerNum()-1)){
            g.drawString( "City not connected to station, pick again",(int) (0.70942*getWidth()), (int) (0.405*getHeight()) );
         } else {
            ableToPurchase = true;
         }

         g.drawRect((int) (0.9242*getWidth()), (int) (0.0231*getHeight()), (int) (getWidth()*0.05), (int) (getHeight()*0.04)); 
         g.drawString("Clear",(int) (0.9262*getWidth()),(int) (0.0471*getHeight()));//draws clear button

        //  g.drawRect((int) (0.9242*getWidth()), (int) (0.0931*getHeight()), (int) (getWidth()*0.05), (int) (getHeight()*0.04)); 
        //  g.drawString("Clear",(int) (0.9292*getWidth()),(int) (0.1171*getHeight()));//draws go back button


         g.drawRect((int)(0.8154133001864512 * getWidth()) , (int)(0.777511961722488 * getHeight()), (int) (0.08 * getWidth()), (int) (0.125 * getHeight()));
         g.drawString("Next Player",(int)(0.8254133001864512 * getWidth()),(int)(0.839511961722488 * getHeight()));// draws contineu button

            
            
        //  if(claimRouteState == 3 && canPurchase()){
        //     if(canPurchase()){
        //         double cardWidth = 0.69578-0.61570;
        //         double cardHeight = 0.44531-0.26406;
        //         for(int i = 0; i < tunnelCards.size(); i++){
        //             g.drawImage(tunnelCards.get(i).getImage(), (int)((0.61570 + cardWidth*i)*getWidth()), (int)( 0.26406*getHeight()), (int)(cardWidth*getWidth()), (int)(cardHeight*getHeight()), null);
        //         }
        //     }
        //     if(!continueButtonClicked){
        //         g.drawString("Click continue to find out if you can purchase.", (int)(0.656308*getWidth()), (int)(0.57416*getHeight()));
        //     }
        //     else if(canPurchaseTunnel && continueButtonClicked){
        //         g.drawString("Can Purchase! Click the button to end turn.", (int)(0.656308*getWidth()), (int)(0.57416*getHeight()));
        //     } else {
        //         g.drawString("Can't Purchase! Click the button to end turn.", (int)(0.656308*getWidth()), (int)(0.57416*getHeight()));
        //     }
        // }
        // if(isDouble && invalidColor && color != null){
        //     g.drawString("Can't use this color, pick again", (int)(0.6625233064014916*getWidth()), (int)(0.3803827751196172*getHeight())); 
        // } else if(hasOther || city1 != null && city2 != null && !canPurchase() && color != null && !color.equals("grey")){
        //     g.drawString("You can't afford this, choose something else", (int)(0.6625233064014916*getWidth()), (int)(0.3803827751196172*getHeight())); 
        //     //claimRouteState = 1;    //change this to something else (maybe make a new claimroutestate)	
        //     color = null;
        //     isgrey = false; //this whole thing needs to reset everything
        // }else if(city1 != null && city2 != null && color != null && gameAccess.getMap().getRailroad(city1, city2).getPlayer() != null && !colorChosen && (!isgrey && !isDouble)){ //needs to use railroad(str, str, str (color))
        //     g.drawString("This route is taken, choose something else", (int)(0.6625233064014916*getWidth()), (int)(0.3803827751196172*getHeight())); 
        // } else if(city1 != null && city2 != null && (!gameAccess.getMap().getRailroad(city1, city2).isTunnel() && canPurchase()) && !invalidColor && color != null){
        //     g.drawString("Can purchase!", (int)(0.6625233064014916*getWidth()), (int)(0.3803827751196172*getHeight())); 
        // }
    }//pickStationRoutesUI
    public void paintSkipButton(Graphics g)
    {
        g.setColor(Color.orange);
        g.fillRoundRect((int)(0.82711*getWidth()), (int)(0.740000*getHeight()),(int)( 0.1*getWidth()), (int)(0.05*getHeight()), (int)(0.01*getWidth()), (int)(0.1*getWidth()));
        g.setColor(Color.black);
        g.drawString("skip", (int) (0.86311*getWidth() ), (int) (0.774*getHeight()));
    }

    public T2RFrame getFrame(){
        return frame;
    }

    public void setFrame(T2RFrame f){
        frame = f;
    }

    public void setIsLookingAtMap(boolean b)
    {
        isLookingAtMap = b;
    }
}//class TR2PAnel
