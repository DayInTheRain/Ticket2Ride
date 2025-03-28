import java.awt.*;
import javax.swing.*;
public class T2RFrame extends JFrame{
    private int WIDTH = 1200;
    private int HEIGHT = 480;
    public T2RFrame(String s)
    {
        super(s);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(WIDTH,HEIGHT);
    setVisible(true);
    }
}
