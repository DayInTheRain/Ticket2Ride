import java.util.*;
import static java.lang.System.*;
import java.io.InputStream;
public class Game {
    private boolean lastRound;
    private ArrayList<Player> players;
    private Deque<TrainCard> trainCards, discard;
    private Deque<Ticket> longTickets, tickets;
    private MapGraph mapGraph;
    private int playerTurn;

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

        //initialize the decks
        trainCards = new LinkedList<TrainCard>();
        discard = new LinkedList<TrainCard>();
        longTickets = new LinkedList<Ticket>();
        tickets = new LinkedList<Ticket>();

        //Initialize all cards
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

		    // Load ticket names from the jar resource
		    scanner = new Scanner(getClass().getResourceAsStream("/TextFile/TicketText.txt"));
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

		} catch (Exception e) {
		    System.out.println("Error adding cards to deck: GAME CLASS");
		    e.printStackTrace();
		}

        try {
            //Load cities for the jar resource
            Scanner scanner = new Scanner(getClass().getResourceAsStream("/TextFile/T2R_cities.txt"));
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

        shuffleDecks();


    }//end of constructor

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
        return trainCards.pop();
    }
    public void discardTrainCard(TrainCard card){
        discard.push(card);
    }

    



}
