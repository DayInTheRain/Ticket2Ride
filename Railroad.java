
public class Railroad {
	private boolean isDouble;
	private Player player;
	private int numTrains;
	private int numWilds;
	private String color;
	private boolean isTunnel;

	private String firstCityString;
	private String secondCityString;
	private City firsCity;
	private City secondCity;

	private String railroadID;
	private double[] coords;

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
		return firsCity;
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
		firsCity = c1;
		secondCity = c2;
	}

	public String getCities(){
		return firstCityString + " " + secondCityString;
	}

	public boolean isDouble() {
		return isDouble;
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
			return firsCity;
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
}//end of the class
