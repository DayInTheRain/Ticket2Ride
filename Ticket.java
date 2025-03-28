import java.util.*;

import javax.imageio.ImageIO;
import javax.smartcardio.Card;

import java.awt.*;
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
        
        try {
			image = ImageIO.read(Ticket.class.getResource("/Tickets/" + input + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
