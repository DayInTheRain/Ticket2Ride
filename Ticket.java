import java.awt.*;
public class Ticket {
    private boolean isLong;
    private int pointValue;
    private String firstCity, secondCity;
    private Image image;

    public Ticket(String input){
        String[] in = input.split(" ");
        if(in[0].equals("t")){
            isLong = true;
        } else {
            isLong = false;
        }
        pointValue = Integer.parseInt(in[1]);
        firstCity = in[2];
        secondCity = in[3];
        
        if(isLong()){
            image = ImageLoader.get("/Images/LongTickets/" + input + ".jpg");
        }else{
            image = ImageLoader.get("/Images/RegularTickets/" + input + ".jpg");
        }

        // try {
		// 	image = ImageIO.read(Ticket.class.getResource("/Tickets/" + input + ".jpg"));
            
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
    } 

    public int compareTo(Ticket t){
        if(t.getFirstCity().equals(getFirstCity()) && t.getSecondCity().equals(getSecondCity())){
            return 0;
        }
        return -1;
    }

    public boolean isLong(){ return isLong; }
    public int getPointValue(){ return pointValue; }
    public String getFirstCity(){ return firstCity; }
    public String getSecondCity(){ return secondCity; }
    public Image getImage(){ return image; }
}
