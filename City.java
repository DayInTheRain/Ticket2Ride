import java.util.*;

public class City {
private String name;
private ArrayList<Railroad> routes;
private int stationID;
private double[] coords = new double[2];

public City(String s){
	String[] str = s.split(" ");
	this.name = str[0];
	this.stationID = 0;
	this.coords[0] = Double.parseDouble(str[1]);
	this.coords[1] = Double.parseDouble(str[2]);
	routes = new ArrayList<>();
}

public City(String n, ArrayList<Railroad> r, double[] c) {
	this.name = n;
	this.routes = r;
	this.stationID = 0;
	this.coords[0] = c[0];
	this.coords[1] = c[1];
	routes = new ArrayList<>();
	// station id starts at 0 when no one has it;
}


public City(String n, double[] c) {
	this.name = n;
	this.routes = null;
	this.stationID = 0;
	this.coords[0] = c[0];
	this.coords[1] = c[1];
	routes = new ArrayList<>();
	// station id starts at 0 when no one has it;
}

public void addRoute(Railroad r){

	for(Railroad added : routes){
		if(added.getID().equals(r.getID())){
			return;
		}
	}
	
	routes.add(r);
}

// public void setStationID(int i)
// {
// 	stationID = i;
// }

public String getName() {
	return name;
}

public double[] getCoords() {
	return coords;
}
public ArrayList<Railroad> getRoutes(){
	return routes;
}
public int getStation() {
	return stationID;
}

public String toString(){
	return "" + name + " - " + coords[0]  + ", " + coords[1] + " - player: " + stationID;
}

public void placeStation(Player p) {
	stationID = p.getPlayerNum();
	p.addStation(this);
}

public void removeStation(Player p){
	stationID = 0;
	p.removeStation(this);
}

}