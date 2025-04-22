import java.util.*;
public class Player {
    private int playerNum, points, trainStations, trains, turnStatus, turnState;
    private HashMap<String, Integer> trainCards;
    private ArrayList<Ticket> tickets;
    private boolean hasEuropeanExpress;
<<<<<<< HEAD
    private ArrayList<TrainCard> cardsPicked;
=======
    private ArrayList<Railroad> railroads;
>>>>>>> 910aa5c820d7896c5b699a5ad2c39c1f682b31cc
    

    public Player(int num){
        playerNum = num;
        points = 0;
        trainStations = 3;
        trains = 45;
        turnStatus = 0;
        turnState = 0;

        //Initialize all cards (starting at 0)
        trainCards = new HashMap<>();
        trainCards.put(("red"), 0);
        trainCards.put(("orange"), 0);
        trainCards.put(("yellow"), 0);
        trainCards.put(("green"), 0);
        trainCards.put(("blue"), 0);
        trainCards.put(("pink"), 0);
        trainCards.put(("black"), 0);
        trainCards.put(("white"), 0);
        trainCards.put(("wild"), 0);
        

        tickets = new ArrayList<>();
        cardsPicked = new ArrayList<>();
        hasEuropeanExpress = false;
        railroads = new ArrayList<>();
    }

    public int getPlayerNum(){ return playerNum; }
    public int getPoints(){ return points; }
    public int getNumTrainStations(){ return trainStations; }
    public HashMap<String, Integer> getTrainCards(){ return trainCards; }
    public ArrayList<Ticket> getTickets(){ return tickets; }
    public boolean hasEuropeanExpress(){ return hasEuropeanExpress; }
    public int getNumTrains(){ return trains; }
    public ArrayList<TrainCard> getCardsPicked() { return cardsPicked; }

    public void addRailroad(Railroad r){
        railroads.add(r);
    }

    public ArrayList<Railroad> getRailroadList(){
        return railroads;
    }

    public void turnState(int num) {
    	turnStatus = num;
    	// if 0 = not player turn
    	//if 1 = player turn
    	// if 2 = player finished turn
    }
    public int getTurnState() {
    	return turnStatus;
    }

    public void addPoints(int num) { points += num; }
    public void placeStation() {
    	trainStations--;
    }
    public void placetrains(int num) {
    	trains -= num;
    }
    public void addToCardsPicked(TrainCard t){
        cardsPicked.add(t);
    }
    public boolean checkTickets(Ticket t){
        for(int i = 0; i < tickets.size(); i++){
            if(tickets.get(i).compareTo(t) == 0){
                return false;
            }
        }
        return true;
    }
    public void addtickets(Ticket x) {
    	tickets.add(x);
    }
    public void removeTicket(Ticket x){
        tickets.remove(x);
    }
    public void rotateTickets(boolean forwards){
        if(forwards){
            tickets.add(tickets.remove(0));
        } else {
            tickets.add(0, tickets.remove(tickets.size()-1));
        }
    }
    public void addtrainCards(LinkedList<TrainCard> list) {
    	for(TrainCard s: list) {
    		//trainCards.put(s, 1);
    		trainCards.put(s.getColor(), trainCards.get(s.getColor())+1);
    	}	
    	}

    public void choice(int num) {
        turnState = num;
        // 1 = claim route
        // 2 = pick train card
        // 3 = pick ticket card
        // 4 = build train Station
    }
    public int getchoice() {
        return turnState;
    }

    public String toString(){
        return "Player " + getPlayerNum();
    }
}//class Player
