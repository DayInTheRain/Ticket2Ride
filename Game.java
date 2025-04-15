import java.util.*;
import static java.lang.System.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;

//syncing issues, lovely.

public class Game {
    private boolean lastRound;
    private ArrayList<Player> player;
    private Deque<TrainCard> trainCards, discard;
    private Deque<Ticket> longTickets, tickets;
    private MapGraph mapGraph;

    public Game(){
        lastRound = false;

        player = new ArrayList<Player>();
        //Initialize players
        for(int i = 0; i < 4; i++){
            player.add(new Player(i+1));
        }

        //initialize the map with routes
        mapGraph = new MapGraph();

        //initialize the decks
        trainCards = new LinkedList<TrainCard>();
        discard = new LinkedList<TrainCard>();
        longTickets = new LinkedList<Ticket>();
        tickets = new LinkedList<Ticket>();

        //Initialize all cards
        trainCardGenerator();
        ticketGenerator();
        cardBackGenerator();

        //initialize cities and railroad
        cityGenerator();
        railroadGenerator();

        shuffleDecks();
        dealStartCards();

        //initialize mapGraph
        //mapGraph = new MapGraph(cityList, railroadList);
    }//end of constructor

    private void cardBackGenerator(){
        trainCardBack = ImageLoader.get("/Images/CardBacks/TrainCardBack.jpg");
        ticketBack = ImageLoader.get("/Images/CardBacks/TicketBack.jpg");
        europeanExpressCard = ImageLoader.get("/Images/CardBacks/EuropeanExpress.jpg");
    }//cardBackGenerator

    public Image getTrainCardBack() {return trainCardBack;}
    public Image getTicketBack() {return ticketBack;}
    public Image getEuropeanExpress() {return europeanExpressCard;}


    private void trainCardGenerator(){
        try {
		    // Load card names from the jar resource
		    Scanner scanner = new Scanner(getClass().getResourceAsStream("/TextFile/TrainCardText.txt"));
		    while (scanner.hasNextLine()) {
		        String name = scanner.nextLine();
		        TrainCard nextCard = new TrainCard(name);
		        trainCards.add(nextCard);
                out.println("added trainCards to deck");
		    }
		    scanner.close();

		    
		} catch (Exception e) {
		    System.out.println("Error adding cards to deck: GAME CLASS");
		    e.printStackTrace();
		}
    }//trainCardGenerator

    private void ticketGenerator(){
        try{
            Scanner scanner = new Scanner(getClass().getResourceAsStream("/TextFiles/TicketText.txt"));
		    while (scanner.hasNextLine()) {
		        String name = scanner.nextLine();
                out.println(name);
		        Ticket nextTicket = new Ticket(name);
                if(nextTicket.isLong()){
                    longTickets.add(nextTicket);
                }else{
                    tickets.add(nextTicket);
                }
		    }
		    scanner.close();


        }catch(Exception e){
            out.println("Error adding tickets into deck: GAME CLASS");
        }
    }//ticketGenerator
    
    private void cityGenerator(){
        try {
            System.out.println("cityGenerator called");
		    // Load card names from the jar resource
		    InputStream cityStream = getClass().getResourceAsStream("/TextFiles/T2R_cities.txt");
		    Scanner scanner = new Scanner(cityStream);
		    while (scanner.hasNextLine()) {
		        String cityInfo = scanner.nextLine();
                City nextCity = new City(cityInfo);
                System.out.println(nextCity);
		        cityList.add(nextCity);
		    }
		    scanner.close();

		    }catch (Exception e) {
		    System.out.println("Error creating cities and adding them into arraylist: GAME CLASS");
		    e.printStackTrace();
		}

    private void railroadGenerator(){
        try {
            System.out.println("railroadGenerator called");
            InputStream railroadStream = getClass().getResourceAsStream("/TextFiles/T2R_railroads.txt");
            Scanner scanner = new Scanner(railroadStream);
            int count = 0;

            while(scanner.hasNextLine()){
                String name = scanner.nextLine();
                out.println(name);
                City nextCity = new City(name);
                mapGraph.addCity(nextCity.getName(), nextCity);
            }
            scanner.close();

        } catch (Exception e) {
            System.out.println("Error initializing cities and adding them to the mapGraph");
            e.printStackTrace();
        }
    }//railroadGenerator

    public void runGame(){
        //uncomment cause it works
        // if(players.get(playerTurn).getTurnState() == 2){
        //     players.get(playerTurn).turnState(0);
        //     incrementTurn();
        // }
    }//runGame

    public void shuffleDecks(){
        Collections.shuffle((LinkedList<Ticket>) tickets);
        Collections.shuffle((LinkedList<Ticket>) longTickets);
        Collections.shuffle((LinkedList<TrainCard>) trainCards);
    }//shuffleDecks
    
    public void dealStartCards() {
    	for(Player x:players) {
    		LinkedList<TrainCard> list = new LinkedList<>();
    		for(int i = 0; i< 4; i++) {
    			list.add(trainCards.pop());    			
    		}
    		x.addtrainCards(list);
    		System.out.println(list);
    	}
    }//dealStartCards

    public int getPlayerTurn(){return playerTurn; }

    public void incrementTurn(){
        playerTurn += 1;
        if(playerTurn == 5)
            playerTurn = 1;
    }//incrementTurn
     
    public Ticket drawTicket(){
        if(tickets.size() > 0)
            return tickets.pop();
        return null;
    }
     
    public Ticket drawLongTicket(){
        if(longTickets.size() > 0)
            return longTickets.pop();
        return null;
    }//drawTicket

    public TrainCard drawTrainCard(){
    	if(trainCards.isEmpty()) {
    		redoDeck();
    	}
        return trainCards.pop();
    }//drawTrainCard

    public void redoDeck() {
    	for(TrainCard x: discard) {
    		trainCards.push(x);
    		discard.pop();
    	}
    Collections.shuffle((LinkedList<TrainCard>) trainCards);
    }//redoDeck

    public void discardTrainCard(TrainCard card){
        discard.push(card);
    }//discardTrainCard

    public ArrayList<Player> getPlayers(){ return players; }
    public ArrayList<TrainCard> getTCFiles(){ return trainCardFiles; }


    public ArrayList<City> getCities()
    {
        return cityList;
    }//getCities

    public MapGraph getMap(){
        return mapGraph;
    }

}//end of class
