
import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TrainCard {
    private String color;
    private Image image;

    public TrainCard(String c){
        color = c;
        try {
			image = ImageIO.read(Ticket.class.getResource("/TrainCard/" + c + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public String getColor(){
        return color;
    }

    public Image getImage(){
        return image;
    }
} //end of class
