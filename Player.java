import java.awt.Color;
import java.util.*;
public class Player {
    private int playerNum, points, trainStations, trains, turnStatus, turnState;
    private HashMap<String, Integer> trainCards;
    private ArrayList<Ticket> tickets;
    private boolean hasEuropeanExpress;
    private ArrayList<TrainCard> cardsPicked;
    private ArrayList<Railroad> railroads;
    private ArrayList<City> stations;

    

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

    public void addTrainCard(String color){
        trainCards.put(color, trainCards.get(color) + 1);
    }
    public void addTrainCard(String color, int i){
        trainCards.put(color, trainCards.get(color) + i);
    }

    public void addTrainCards(ArrayList<TrainCard> tcs){
        for(TrainCard t: tcs){
            addTrainCard(t.getColor());
        }
    }
    
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
    public void clearCardsPicked(){ cardsPicked.clear(); }
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

    public Color getColor(){
        Color c;
        switch (playerNum) {
            case 1:
                c = new Color(50, 168, 82); //green
                return  c;
            case 2:
                c = new Color(176, 26, 26); //red
                return  c;
            case 3:
                c = new Color(232, 195, 9); //yellow
                return  c;
            case 4:
                c = new Color(31, 31, 171); // blue
                return  c;
            default:
                return Color.gray;
        }
    }

    public void decrementTrainStations()
    {
        trainStations = trainStations -1;
    }

    public void addStation(City c){
        stations.add(c);
    }
}//class Player
