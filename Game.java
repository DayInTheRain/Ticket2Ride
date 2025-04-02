import java.util.*;
import static java.lang.System.*;
import java.io.InputStream;
public class Game {
    private boolean lastRound;
    private ArrayList<Player> player;
    private Deque<TrainCard> trainCards, discard;
    private Deque<Ticket> longTickets, tickets;

    public Game(){
        lastRound = false;

        player = new ArrayList<Player>();
        //Initialize players
        for(int i = 0; i < 4; i++){
            player.add(new Player(i+1));
        }

        trainCards = new LinkedList<TrainCard>();
        discard = new LinkedList<TrainCard>();
        longTickets = new LinkedList<Ticket>();
        tickets = new LinkedList<Ticket>();
        //Initialize all cards
        try {
		    // Load card names from the jar resource
		    InputStream cardStream = getClass().getResourceAsStream("/insert name here");
		    Scanner scanner = new Scanner(cardStream);
		    while (scanner.hasNextLine()) {
		        String name = scanner.nextLine();
		        TrainCard nextCard = new TrainCard(name);
		        trainCards.add(nextCard);
		    }
		    scanner.close();

		    // Load patron names from the jar resource
		    InputStream ticketStream = getClass().getResourceAsStream("/insert name here");
		    scanner = new Scanner(ticketStream);
		    while (scanner.hasNextLine()) {
		        String name = scanner.nextLine();
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

    }

    



}
