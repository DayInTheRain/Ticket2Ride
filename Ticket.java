import java.util.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    public boolean isLong(){ return isLong; }
    public int getPointValue(){ return pointValue; }
    public String getFirstCity(){ return firstCity; }
    public String getSecondCity(){ return secondCity; }
    public Image getImage(){ return image; }
}
