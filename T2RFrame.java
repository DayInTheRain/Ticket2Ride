import java.awt.*;
import javax.swing.*;
public class T2RFrame extends JFrame{
    private int WIDTH = 1625;
    private int HEIGHT = 875;
    public T2RFrame(String s)
    {
        super(s);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(WIDTH,HEIGHT);
    add(new T2RPanel());
    setVisible(true);
    }
}
