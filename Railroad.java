import java.util.ArrayList;

public class Railroad {
	private boolean isDouble;
	private Player player;
	private int numTrains;
	private int numWilds;
	private String color;
	private boolean isTunnel;

	private String firstCityString;
	private String secondCityString;
	private City firstCity;
	private City secondCity;

	private String railroadID;
	private double[] coords;
	private ArrayList<Railroad> neighbors;

	public Railroad(String s){
		this.coords = new double[2];

		//System.out.println(s);
		this.railroadID = s;

		String[] info = s.split(" ");
	
		this.firstCityString = info[0];
		this.secondCityString = info[1];
		this.color = info[2];
		this.coords[0] = Double.parseDouble(info[3]);
		this.coords[1] = Double.parseDouble(info[4]);
		this.isTunnel = info[5].equals("F") ? false : true;
		this.numTrains = Integer.parseInt(info[6]);
		this.numWilds = Integer.parseInt(info[7]);
		this.isDouble = info[8].equals("F") ? false : true;
		this.neighbors = new ArrayList<>();
	}
	
	public Railroad(boolean d, int numt, int numw, String c, boolean t, String fc, String sc, String rail) {
		isDouble = d;
		numTrains = numt;
		numWilds = numw;
		color = c;
		isTunnel = t;
		firstCityString = fc;
		secondCityString = sc;
		railroadID = rail;
		player = null;
	}
	
	public City getFirstCity(){
		return firstCity;
	}

	public City getSecondCity(){
		return secondCity;
	}

	public String getFirst(){
		return firstCityString;
	}

	public String getSecond(){
		return secondCityString;
	}

	public void setCities(City c1, City c2){
		firstCity = c1;
		secondCity = c2;
	}

	public String getCities(){
		return firstCityString + " " + secondCityString;
	}

	public boolean isDouble() {
		return isDouble;
	}

	public Railroad getSisterRailroad(){
		if(isDouble()){
			ArrayList<Railroad> cityRails = this.firstCity.getRoutes();
			for(Railroad r : cityRails){
				if(((r.getFirst().equals(this.getFirst()) && r.getSecond().equals(this.getSecond())) || 
				(r.getFirst().equals(this.getSecond()) && r.getSecond().equals(this.getFirst()))) && r.getID().equals(this.getID()) == false ) {
					return r;
				}
			}
		} 
		return null;
	}


	public Railroad getSisterRailroad(String color){
		if(isDouble()){
			if(this.color.equals(color)){
				return this;
			}
			else if (!this.color.equals(color)){
				ArrayList<Railroad> cityRails = this.firstCity.getRoutes();
				for(Railroad r : cityRails){
					if(((r.getFirst().equals(this.getFirst()) && r.getSecond().equals(this.getSecond())) || 
					(r.getFirst().equals(this.getSecond()) && r.getSecond().equals(this.getFirst()))) && r.getID().equals(this.getID()) == false ) {
						return r;
					}
				}
			}
		} 
		return null;
	}

	public Player getPlayer() {
		return player;
	}
	
	public int getNumTrains() {
		return numTrains;
	}
	
	public int getNumWild() {
		return numWilds;
	}
	
	public String getColor() {
		return color;
	}
	
	public boolean isTunnel() {
		return isTunnel;
	}
	
	public void claim(Player p) {
		player = p;
		p.addRailroad(this);
	}

	public double[] getCoords(){
		return coords;
	}

	public String toString(){
		return railroadID;
	}

	public String getID(){
		return railroadID;
	}
	
	public City getOtherCity(City c){
		if(c.getName().equals(firstCityString))
			return secondCity;
		else
			return firstCity;
	}
	
	public int getPoints() {
		if(this.getNumTrains()==1)
			return 1;
		else if(this.getNumTrains()==2)
			return 2;
		else if(this.getNumTrains()==3)
			return 4;
		else if(this.getNumTrains()==4)
			return 7;
		else if(this.getNumTrains()==6)
			return 15;
		else if(this.getNumTrains()==8)
			return 15;
		return 0;
	}

	public ArrayList<Railroad> getNeighbors(){
		return neighbors;
	}
	
	public void addNeighbor(Railroad r){
		neighbors.add(r);
	}
}//end of the class
