import java.util.*;
public class Player {
    private int playerNum, points, trainStations, trains;
    private HashMap<TrainCard, Integer> trainCards;
    private ArrayList<Ticket> tickets;
    private boolean hasEuropeanExpress;

    public Player(int num){
        playerNum = num;
        points = 0;
        trainStations = 3;
        trains = 45;

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

    public void addPoints(int num) { points += num; }
}
