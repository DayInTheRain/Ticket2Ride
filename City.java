import java.util.*;

public class City {
private String name;
private ArrayList<Railroad> routes;
private int stationID;


public City(String n, ArrayList<Railroad> r) {
	this.name = n;
	this.routes = r;
	this.stationID = 0;
	// station id starts at 0 when no one has it;
}
public String getName() {
	return name;
}
public ArrayList<Railroad> getRoutes(){
	return routes;
}
public int getStation() {
	return stationID;
}

//public void placeStation(Player p) {
//	stationID = p.getPlayerNum();
//}

}