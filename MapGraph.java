import java.util.*;

public class MapGraph {
	private HashMap<String, City> cities;
	private HashMap<String, Railroad> railroads;

	public MapGraph() {
		cities = new HashMap<String, City>();
		railroads = new HashMap<String, Railroad>();
	}

	public MapGraph(ArrayList<City> c, ArrayList<Railroad> r){
		cities = new HashMap<String, City>();
		railroads = new HashMap<String, Railroad>();

		for(City ci : c){
			
			cities.put(ci.getName(), ci);
			System.out.println(ci + " was added to mapGraph");
		}

		for(Railroad ra : r){
			railroads.put(ra.getID(), ra);
			System.out.println(ra + " was added to mapGraph");
		}

		connectGraph();
		setUpNeighbors();
	}

	private void connectGraph(){
		Iterator<Railroad> iter = railroads.values().iterator();

		while(iter.hasNext()){
			Railroad r = iter.next();

			System.out.println(r);

			City foundCity = cities.get(r.getFirst());
			foundCity.addRoute(r);
			System.out.println("found first city: " + foundCity);
			foundCity = cities.get(r.getSecond());
			foundCity.addRoute(r);
			System.out.println("found second city: " + foundCity);
		}
	}//connectGraph

	public void setUpNeighbors(){
		Iterator<Railroad> iter = railroads.values().iterator();

		ArrayList<Railroad> list = new ArrayList<>(railroads.values());

		while(iter.hasNext()){
			Railroad currentRailroad = iter.next();

			for(Railroad r : list){
				if(r == currentRailroad){
					break;
				}

				if(currentRailroad.getFirst().equals(r.getFirst()) || currentRailroad.getFirst().equals(r.getSecond()) ||
				currentRailroad.getSecond().equals(r.getFirst()) || currentRailroad.getSecond().equals(r.getSecond())){

					System.out.println("" + r + " is a neighbor of " + currentRailroad);
					currentRailroad.addNeighbor(r);
				}
			}

		}
	}//setUpNeighbors


	//Method addCity should never be called vv
	/* 
	public void addCity(String key, City c){
		if(cities.get(key) == null){
			cities.put(key, c);
		}
	}
	*/
	

	public HashMap<String, City> getCities() {
		return cities;
	}
	public HashMap<String,Railroad> getRailroads() {
		return railroads;
	}
	
	public int numTrains(Railroad x) {
		return x.getNumTrains();
	}

	public Player longestRailroad(ArrayList<Player> p) {
		Player longest = null;
		// write how to determine the longest
		//ArrayList<Railroad> roadList = p.getRailroadList();
		

		return longest;
	}

	// NOT WORKING YET
	// public int longestRailroadOfPlayer(Player p){
	// 	int trainNum = 0;
	// 	int maxTrainNum = trainNum;
	// 	ArrayList<Railroad> roadList = p.getRailroadList();


	// 	Queue<Railroad> queue = new LinkedList<>();
	// 	boolean[] visited = new boolean[roadList.size()];
	// 	visited[0] = true;

	// 	while(!roadList.isEmpty()){
	// 		Railroad r = roadList.get(0);
	// 		trainNum = r.getNumTrains();
	// 		City city1 = r.getFirstCity();
	// 		City city2 = r.getFirstCity();
	// 		roadList.removeFirst();

	// 		boolean endOfSection = false;

	// 		while(endOfSection == false){
	// 			int current = 0;

	// 			for(Railroad currentRail : roadList ){
	// 				if(currentRail.getFirstCity().equals(city1) || currentRail.getSecondCity().equals(city2)){
	// 					trainNum += currentRail.getNumTrains();
	// 					roadList.remove(currentRail);
	// 				}
	// 			}


	// 		}

	// 		if(trainNum > maxTrainNum){
	// 			maxTrainNum = trainNum;
	// 		}
	// 	}

	// 	return maxTrainNum;
	// }//longestRailroadOFPlayer

	public int dfSearch(ArrayList<Railroad> list, Railroad start){


        System.out.println(list);


        int trainNum = 0;
        int maxTrainNum = trainNum;
        // Queue<Railroad> queue = new LinkedList<>();
        // queue.add(start);


        Railroad lastVisited = start;
        boolean[] visited = new boolean[list.size()];
        visited[list.indexOf(start)] = true;


        for(Railroad currentRailroad : list){
            if(!visited[list.indexOf(currentRailroad)] ){
                trainNum += currentRailroad.getNumTrains();
                visited[list.indexOf(currentRailroad)] = true;
                System.out.println(currentRailroad);
            }


            if(maxTrainNum < trainNum){
                maxTrainNum = trainNum;
            }
        }


        //System.out.println(visited);
        return maxTrainNum;
    }//dfSearch

		

	public boolean searchIfClaimed(City fCity, City sCity, Player p) {
		boolean claimed = false;
		//write on if railraod connects c1 anc c2 and see if claimed by player.
		String exists = railroadExists(fCity, sCity);

		if(!exists.equals("false")){
			if(railroads.get(exists).getPlayer().getPlayerNum() == p.getPlayerNum()){
				System.out.println(railroads.get(exists).getPlayer());
				claimed = true;
			}
		}
		return claimed;
	}

	

	public String railroadExists(City fCity, City sCity){
		String railroadID = "false";
		Scanner scanner = new Scanner(getClass().getResourceAsStream("/TextFiles/T2R_railroads.txt"));

		while (scanner.hasNextLine() || railroadID == ""){
			String r = scanner.nextLine();
			String[] rInfo = r.split(" ");

			if(rInfo[0].equals(fCity.getName()) && rInfo[1].equals(sCity.getName()) || rInfo[1].equals(fCity.getName()) && rInfo[0].equals(sCity.getName()) ){
				railroadID = r;
			}
		}

		scanner.close();

		return railroadID;
	}

	public City getCity(String c) {
		c = c.toUpperCase();
		return cities.get(c);
	}

	public Railroad getRailroad(City c1, City c2) {
		String id;
		if(c1.getName().compareToIgnoreCase(c2.getName()) < 0 ){
			id = railroadExists(c1, c2);
		} else{
			id = railroadExists(c2, c1);
		}

		if (id.equals("false")){
			return null;
		}else{
			return railroads.get(id);
		}
	}//getRailroad


}//class MapGraph
