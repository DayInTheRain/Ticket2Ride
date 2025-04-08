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
}

public City(String n, ArrayList<Railroad> r, double[] c) {
	this.name = n;
	this.routes = r;
	this.stationID = 0;
	this.coords[0] = c[0];
	this.coords[1] = c[1];
	// station id starts at 0 when no one has it;
}


public City(String n, double[] c) {
	this.name = n;
	this.routes = null;
	this.stationID = 0;
	this.coords[0] = c[0];
	this.coords[1] = c[1];
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

public String toString(){
	return "" + name + " - " + coords[0]  + ", " + coords[1] + " - player: " + stationID;
}

public void placeStation(Player p) {
	stationID = p.getPlayerNum();
}

}