
import java.awt.*;

public class TrainCard {
    private String color;
    private Image image;

    public TrainCard(String c, Image i){
        color = c;
        image = i;
    }

    public String getColor(){
        return color;
    }

    public Image getImage(){
        return image;
    }
} //end of class
