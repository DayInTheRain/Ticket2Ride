import java.util.*;
public class Player {
    private int playerNum, points, trainStations;
    private ArrayList<TrainCard> trainCards;
    private ArrayList<Ticket> tickets;
    private boolean hasEuropeanExpress;

    public Player(int num){
        playerNum = num;
        points = 0;
        trainStations = 3;
        trainCards = null; //Change this to some sort of generate cards method
        tickets = null;
        hasEuropeanExpress = false;
    }
}
