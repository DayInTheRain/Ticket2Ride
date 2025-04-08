
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

	public Railroad(String s){
		this.railroadID = s;
		
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
	
	

}
