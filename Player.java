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
        trains = 15;
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
        stations = new ArrayList<City>();
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


    

        public boolean areConnected(Railroad start, Railroad target) {
            if (start == null || target == null) return false;
            if (start == target) return true;
    
            Set<Railroad> visited = new HashSet<>();
            Queue<Railroad> queue = new LinkedList<>();
            queue.add(start);
            visited.add(start);
    
            while (!queue.isEmpty()) {
                Railroad current = queue.poll();
    
                for (Railroad neighbor : current.getNeighbors()) {
                        if (this.railroads.contains(neighbor))
                           { 
                            if (neighbor == target) {
                                return true;
                            }
                            if (!visited.contains(neighbor)) {
                                visited.add(neighbor);
                                queue.add(neighbor);
                            }
                        }
                }
            }
    
            return false;
        }
    

        public boolean TicketCompleted(Ticket ticket)
        {
            ticket.getFirstCity();

            ArrayList<Railroad> startingRoads = new ArrayList<Railroad>();
            for (Railroad r : railroads)
            {
                System.out.println("The railroad I'm considering adding is " + r);
                System.out.println(ticket.getFirstCity());
                System.out.println(r.getFirst());
                System.out.println("the first size is " + startingRoads.size());
                if (ticket.getFirstCity().equals(r.getFirst()) || ticket.getFirstCity().equals(r.getSecond()))
                {
                    startingRoads.add(r);
                    System.out.println("if statement changes to size" + startingRoads.size());
                    
                }

               
            }


            
            System.out.println("Starting road " + startingRoads);
            ArrayList<Railroad> toBeAdded = new ArrayList<Railroad>();
            for (Railroad p: railroads)
            {

                for (Railroad q : startingRoads)
                if (p.getFirst().equals(q.getFirst()) || p.getFirst().equals(q.getSecond()))
                {
                    if ( startingRoads.contains(p))
                    {
                        System.out.println("already added");
                    }
                    else
                    {
                    toBeAdded.add(p);
                    }
                    
                }

            }
            startingRoads.addAll(toBeAdded);
            System.out.println("starting roads is of size" + startingRoads.size());
            System.out.println(startingRoads.toString());
            /* 
        getRailroadList().get(0);
        HashSet<City> cityNetwork = new HashSet<City>();
                */
                HashSet<String> citiesVisited = new HashSet<String>();
                for (Railroad r : startingRoads)
                {
                    citiesVisited.add(r.getFirst());
                    citiesVisited.add(r.getSecond());
                }
                if (citiesVisited.contains(ticket.getFirstCity()) && citiesVisited.contains(ticket.getSecondCity()))
                {
                    return true;
                }
                return false;
        }
}//class Player
