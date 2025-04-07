import java.util.*;
public class Player {
    private int playerNum, points, trainStations, trains, turnStatus, turnState;
    private HashMap<TrainCard, Integer> trainCards;
    private ArrayList<Ticket> tickets;
    private boolean hasEuropeanExpress;
    

    public Player(int num){
        playerNum = num;
        points = 0;
        trainStations = 3;
        trains = 45;
        turnStatus = 0;
        turnState = 0;

        //Initialize all cards (starting at 0)
        trainCards = new HashMap<>();
        trainCards.put(new TrainCard("red"), 0);
        trainCards.put(new TrainCard("orange"), 0);
        trainCards.put(new TrainCard("yellow"), 0);
        trainCards.put(new TrainCard("green"), 0);
        trainCards.put(new TrainCard("blue"), 0);
        trainCards.put(new TrainCard("pink"), 0);
        trainCards.put(new TrainCard("black"), 0);
        trainCards.put(new TrainCard("white"), 0);
        trainCards.put(new TrainCard("wild"), 0);
        

        tickets = null; //Change this to some sort of generate cards method
        hasEuropeanExpress = false;
    }

    public int getPlayerNum(){ return playerNum; }
    public int getPoints(){ return points; }
    public int getNumTrainStations(){ return trainStations; }
    public HashMap<TrainCard, Integer> getTrainCards(){ return trainCards; }
    public ArrayList<Ticket> getTickets(){ return tickets; }
    public boolean hasEuropeanExpress(){ return hasEuropeanExpress; }
    public int getNumTrains(){ return trains; }
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
    public void addtickets(ArrayList<Ticket> list) {
    	for(Ticket x: list) {
    		tickets.add(x);
    	}
    }
    public void addtrainCards(LinkedList<TrainCard> list) {
    	for(TrainCard s: list) {
    		//trainCards.put(s, 1);
    		trainCards.put(s, trainCards.get(s)+1);
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
}
