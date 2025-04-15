import java.util.ArrayList;
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

	public void addCity(String key, City c){
		if(cities.get(key) == null){
			cities.put(key, c);
		}
	}

	public HashMap getCities() {
		return cities;
	}
	public HashMap getRailroads() {
		return railroads;
	}
	public int numTrains(Railroad x) {
		return x.getNumTrains();
	}

	public Player longestRailroad(ArrayList<Player> p) {
		Player longest = null;
		// write how to determine the longest
		return longest;
	}

	public boolean search(City fCity, City sCity, Player p) {
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

			if(rInfo[0].equals(fCity.getName()) && rInfo[1].equals(sCity.getName())){
				railroadID = r;
			}
		}

		scanner.close();

		return railroadID;
	}

	public City getCity(int x, int y) {
		City s = null;
		return s;
	}

	public Railroad getRailroad(City c1, City c2) {
		Railroad x = null;
		return x;
	}

}
