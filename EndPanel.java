import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;

public class EndPanel extends JPanel implements MouseListener{
	private T2RFrame frame;
	Image endscreen;
	JButton startbutton;
    JButton rulesbutton;
    Game gameAccess;  

    boolean isEnd;
    boolean isLast;
    int lastTurn;
    boolean firstlast;
    static boolean isFirst = false;

    Font origionalFont;

    Graphics g;

	T2RPanel gamePanel;
    
    public EndPanel(Game M, T2RPanel p) {
    	gameAccess = M;
    	endscreen = ImageLoader.get("/Images/end screen.jpg");
		gamePanel = p;
		if(!isFirst){
			isFirst = true;
			ArrayList<Player> plays = new ArrayList<>();
			plays.addAll(gameAccess.getPlayers());
			for(Player x : plays) {
				x.addPoints(x.getNumTrainStations()*4);
			 // needs to check destination tickets
				for(Ticket y: x.getTickets()) {
					if(x.isTicketCompleted(y)) {
						x.addPoints(y.getPointValue());
					}
					else 
						x.addPoints(-y.getPointValue());
				}
				if(x.hasEuropeanExpress())
					x.addPoints(10);
				
			System.out.println("Player "+x.getPlayerNum()+":"+x.getPoints());
			}
		}

		addMouseListener(this);
    }
    
    public void paint(Graphics g) {
    	endGame(g);
    }

	// public void go2MapButton(Graphics g){
	// 	g.setColor(getBackground());
	// 	g.drawRoundRect(ALLBITS, ABORT, WIDTH, HEIGHT, WIDTH, HEIGHT);
	// }
    
 public void endGame(Graphics g) {
    	g.drawImage(endscreen, 0, 0, getWidth(), getHeight(), null);
    	Player first = null;
    	Player second = null;
    	Player third = null;
    	Player fourth = null;
    	
    	
    	/*
    	for(Player x : plays) {
    		x.addPoints(x.getNumTrainStations()*4);
    	 // needs to check destination tickets
    		for(Ticket y: x.getTickets()) {
    			if(x.isTicketCompleted(y)) {
    				x.addPoints(y.getPointValue());
    			}
    			else 
    				x.addPoints(-y.getPointValue());
    		}
    		if(x.hasEuropeanExpress())
    			x.addPoints(10);
    		
    	System.out.println("Player "+x.getPlayerNum()+":"+x.getPoints());
    	}
    	*/
    	ArrayList<Integer> places = new ArrayList<>();
    	int p1points = gameAccess.getPlayers().get(0).getPoints();
    	int p2points = gameAccess.getPlayers().get(1).getPoints();
    	int p3points = gameAccess.getPlayers().get(2).getPoints();
    	int p4points = gameAccess.getPlayers().get(3).getPoints();
    	
    	places.add(p1points);
    	places.add(p2points);
    	places.add(p3points);
    	places.add(p4points);
    	
    	Collections.sort(places);
    
    	
    	if(places.get(3) == p1points) 
    		first = gameAccess.getPlayers().get(0);
    	else if(places.get(3) == p2points) 
    		first = gameAccess.getPlayers().get(1);
    	else if(places.get(3) == p3points) 
    		first = gameAccess.getPlayers().get(2);
    	else if(places.get(3) == p4points) 
    		first = gameAccess.getPlayers().get(3);
    	
    	if(places.get(2) == p1points) 
    		second = gameAccess.getPlayers().get(0);
    	else if(places.get(2) == p2points) 
    		second = gameAccess.getPlayers().get(1);
    	else if(places.get(2) == p3points) 
    		second = gameAccess.getPlayers().get(2);
    	else if(places.get(2) == p4points) 
    		second = gameAccess.getPlayers().get(3);
    	
    	if(places.get(1) == p1points) 
    		third = gameAccess.getPlayers().get(0);
    	else if(places.get(1) == p2points) 
    		third = gameAccess.getPlayers().get(1);
    	else if(places.get(1) == p3points) 
    		third = gameAccess.getPlayers().get(2);
    	else if(places.get(1) == p4points) 
    		third = gameAccess.getPlayers().get(3);
    	
    	if(places.get(0) == p1points) 
    		fourth = gameAccess.getPlayers().get(0);
    	else if(places.get(0) == p2points) 
    		fourth = gameAccess.getPlayers().get(1);
    	else if(places.get(0) == p3points) 
    		fourth = gameAccess.getPlayers().get(2);
    	else if(places.get(0) == p4points) 
    		fourth = gameAccess.getPlayers().get(3);
    		
    	// NOT DONE
    		
    	/*first = getCurrentPlayer();
    	second = getCurrentPlayer();
    	third  = getCurrentPlayer();
    	fourth  = getCurrentPlayer();*/
    	
    	
    	Font font = new Font("Monospaced", Font.PLAIN, Math.abs((int)( 0.20947416762342135*getHeight() -  0.16991963260619977*getHeight()))); 
    	g.setFont(font);
    	g.drawString("Player " +first.getPlayerNum(),(int)(0.0853079552517091361*getWidth()),(int)( 0.3908133971291866*getHeight()));
    	g.drawString("Player " +second.getPlayerNum(),(int)(0.331541330018645*getWidth()),(int)( 0.5418660287081339*getHeight()));
    	g.drawString("Player " +third.getPlayerNum(),(int)(0.6026041666666667*getWidth()),(int)( 0.6188200589970502*getHeight()));
    	g.drawString("Player " +fourth.getPlayerNum(),(int)(0.8344791666666667*getWidth()),(int)( 0.7887610619469026*getHeight()));
    	
    	g.drawString("Points: "+first.getPoints(),(int)(0.08633312616532007*getWidth()),(int)( 0.5143540669856459*getHeight()));
    	g.drawString("Points: "+second.getPoints(),(int)(0.331541330018645*getWidth()),(int)( 0.618421052631579*getHeight()));
    	g.drawString("Points: "+third.getPoints(),(int)(0.6026041666666667*getWidth()),(int)( 0.7105263157894737*getHeight()));
    	g.drawString("Points: "+fourth.getPoints(),(int)(0.8344791666666667*getWidth()),(int)( 0.8444976076555024*getHeight()));
    	
    	g.drawString("Click for",(int)(0.08633312616532007*getWidth()),(int)( 0.6303827751196173*getHeight()));
    	g.drawString("breakdown",(int)(0.08633312616532007*getWidth()),(int)( 0.6674641148325359*getHeight()));
    	g.drawRect((int)(0.09384711000621504*getWidth()),(int)(0.7476076555023924*getHeight()),(int)(0.18272218769422002*getWidth())-(int)(0.09384711000621504*getWidth()),(int)(0.8074162679425837*getHeight())-(int)(0.7476076555023924*getHeight()));
    	
    	g.drawString("Click for",(int)(0.331541330018645*getWidth()),(int)( 0.6901913875598086*getHeight()));
    	g.drawString("breakdown",(int)(0.331541330018645*getWidth()),(int)( 0.7234528347329473*getHeight()));
    	g.drawRect((int)(0.341541330018645*getWidth()),(int)(0.7990430622009569*getHeight()),(int)(0.42272218769422002*getWidth())-(int)(0.331541330018645*getWidth()),(int)(0.8582384782*getHeight())-(int)(0.7990430622009569*getHeight()));
    	
    	g.drawString("Click for",(int)(0.6026041666666667*getWidth()),(int)( 0.7691387559808612*getHeight()));
    	g.drawString("breakdown",(int)(0.6026041666666667*getWidth()),(int)( 0.802354456*getHeight()));
    	g.drawRect((int)(0.6128589185829708*getWidth()),(int)(0.8564593301435407*getHeight()),(int)(0.704164077066501*getWidth())-(int)(0.6128589185829708*getWidth()),(int)(0.9127751196172249*getHeight())-(int)(0.8564593301435407*getHeight()));
    	
    	g.drawString("Click for",(int)(0.8344791666666667*getWidth()),(int)( 0.8820095693779905*getHeight()));
    	g.drawString("breakdown",(int)(0.8344791666666667*getWidth()),(int)( 0.9120095693779905*getHeight()));
    	g.drawRect((int)(0.84000*getWidth()),(int)(0.9413875598086124*getHeight()),(int)(0.93324654*getWidth())-(int)(0.8400000*getWidth()),(int)(0.9900000*getHeight())-(int)(0.9413875598086124*getHeight()));
    	


		//go back button

		g.setColor(Color.white);
		g.fillRect(0,0, (int)(0.2*getWidth()), (int) (0.1*getHeight()));

		g.setColor(Color.black);
		g.drawString("Back to map", (int)(0.025*getWidth()), (int) (0.05*getHeight()));  
    }


	@Override
	public void mouseClicked(MouseEvent e) {
 		double x = e.getX();
		int y = e.getY();
		System.out.println("x: " + x);
		System.out.println("y: " + y);
		if (rectangularInBounds(x, y, 0, (int)(0.2*getWidth()), 0, (int)(0.1*getHeight())))
		{
			System.out.println("in bounds----------------------------");
			gamePanel.setIsLookingAtMap(true);
			this.getFrame().gameScreen();
		}
 
		repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setFrame(T2RFrame f){
		frame = f;
	}

	public T2RFrame getFrame(){
		return frame;
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


    }//rectangularInBounds


}
