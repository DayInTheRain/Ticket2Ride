
public class Railroad {
	private boolean isDouble;
	private Player player;
	private int numTrains;
	private int numWilds;
	private String color;
	private boolean isTunnel;
	private String firstCity;
	private String secondCity;
	private String railroadID;
	private double[] coords;

	public Railroad(String s){
		this.coords = new double[2];

		//System.out.println(s);
		this.railroadID = s;

		String[] info = s.split(" ");
	
		this.firstCity = info[0];
		this.secondCity = info[1];
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
		firstCity = fc;
		secondCity = sc;
		railroadID = rail;
		player = null;
	}
	
	public String getFirst(){
		return firstCity;
	}

	public String getSecond(){
		return secondCity;
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
	
}//end of the class
