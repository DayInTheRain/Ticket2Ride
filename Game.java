import java.io.InputStream;
import static java.lang.System.*;
import java.util.*;

public class Game {
    private boolean lastRound;
    private ArrayList<Player> players;
    private ArrayList<TrainCard> trainCardFiles;
    private Deque<TrainCard> trainCards, discard;
    private Deque<Ticket> longTickets, tickets;
    private MapGraph mapGraph;
    private int playerTurn;
    private ArrayList<City> cityList;

    public Game(){
        lastRound = false;
        playerTurn = 1;

        players = new ArrayList<Player>();
        //Initialize players
        for(int i = 0; i < 4; i++){
            players.add(new Player(i+1));
        }

        //initialize the map with routes
        mapGraph = new MapGraph();
        cityList = new ArrayList<City>();

        //initialize the decks
        trainCards = new LinkedList<TrainCard>();
        discard = new LinkedList<TrainCard>();
        longTickets = new LinkedList<Ticket>();
        tickets = new LinkedList<Ticket>();
        trainCardFiles = new ArrayList<>();

        //Initialize all cards
        trainCardGenerator();
        ticketGenerator();
        cityGenerator();

        shuffleDecks();
        dealoutstartcards();
    }//end of constructor

    public void trainCardGenerator(){
        try {
		    // Load card names from the jar resource
		    Scanner scanner = new Scanner(getClass().getResourceAsStream("/TextFiles/TrainCardText.txt"));
		    while (scanner.hasNextLine()) {
		        String name = scanner.nextLine();
		        TrainCard nextCard = new TrainCard(name);
                if(nextCard.getColor().equals("wild")){
                    for(int i = 0; i < 14; i++){
                        trainCards.add(nextCard);
                    }
                } else {
                    for (int i = 0; i < 12; i++){
                        trainCards.add(nextCard);
                    }
                }
                trainCardFiles.add(nextCard);
                out.println("added trainCards to deck");
		    }
		    scanner.close();

		    
		} catch (Exception e) {
		    System.out.println("Error adding cards to deck: GAME CLASS");
		    e.printStackTrace();
		}
    }

    public void ticketGenerator(){
        try{
            Scanner scanner = new Scanner(getClass().getResourceAsStream("/TextFiles/TicketText.txt"));
		    while (scanner.hasNextLine()) {
		        String name = scanner.nextLine();
                System.out.println(name);
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
    
    public void cityGenerator(){
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




        


    }//cityGenerator

    public void runGame(){
        //uncomment cause it works
        // if(players.get(playerTurn).getTurnState() == 2){
        //     players.get(playerTurn).turnState(0);
        //     incrementTurn();
        // }
    }

    public void shuffleDecks(){
        Collections.shuffle((LinkedList<Ticket>) tickets);
        Collections.shuffle((LinkedList<Ticket>) longTickets);
        Collections.shuffle((LinkedList<TrainCard>) trainCards);
    }
    
    public void dealoutstartcards() {
    	for(Player x:players) {
    		LinkedList<TrainCard> list = new LinkedList<>();
    		for(int i = 0; i< 4; i++) {
    			list.add(trainCards.pop());    			
    		}
    		x.addtrainCards(list);
    		System.out.println(list);
    	}
    }

    public int getPlayerTurn(){return playerTurn; }
    public void incrementTurn(){
        playerTurn += 1;
        if(playerTurn == 5)
            playerTurn = 1;
    }
    public Ticket drawTicket(){
        return tickets.pop();
    }
    public Ticket drawLongTicket(){
        return longTickets.pop();
    }
    public TrainCard drawTrainCard(){
    	if(trainCards.isEmpty()) {
    		redoDeck();
    	}
        return trainCards.pop();
    }
    public void redoDeck() {
    	for(TrainCard x: discard) {
    		trainCards.push(x);
    		discard.pop();
    	}
    Collections.shuffle((LinkedList<TrainCard>) trainCards);
    }
    public void discardTrainCard(TrainCard card){
        discard.push(card);
    }
    
    public ArrayList<Player> getPlayers(){ return players; }
    public ArrayList<TrainCard> getTCFiles(){ return trainCardFiles; }


    public ArrayList<City> getCities()
    {
        return cityList;
    }

}//end of class
