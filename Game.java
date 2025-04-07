import java.io.InputStream;
import static java.lang.System.*;
import java.util.*;
public class Game {
    private boolean lastRound;
    private ArrayList<Player> player;
    private Deque<TrainCard> trainCards, discard;
    private Deque<Ticket> longTickets, tickets;
    private MapGraph mapGraph;
    private ArrayList<City> cityList;

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

    }

    public void cityGenerator()
    {
         try {
		    // Load card names from the jar resource
		    InputStream cityStream = getClass().getResourceAsStream("/T2R_cities");
		    Scanner scanner = new Scanner(cityStream);
		    while (scanner.hasNextLine()) {
		        String cityInfo = scanner.nextLine();
                String[] cityQualities = cityInfo.split("_");
                double[] coordinates = {Double.parseDouble(cityQualities[1]), Double.parseDouble(cityQualities[2])};
		        City nextCity = new City(cityQualities[0], coordinates);
		        cityList.add(nextCity);
		    }
		    scanner.close();

		
		    }
		   

		 catch (Exception e) {
		    System.out.println("Error adding cards to deck: GAME CLASS");
		    e.printStackTrace();
		}



        


    }//end of constructor

    



}
