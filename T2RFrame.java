import java.awt.*;
import javax.swing.*;
public class T2RFrame extends JFrame{
    private int WIDTH = 1625;
    private int HEIGHT = 875;

    private EndPanel endJPanel;
    private T2RPanel gamePanel;
    private JPanel mainP;
    private CardLayout cl;

    public T2RFrame(String s)
    {
        super(s);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);

        cl = new CardLayout();
        mainP = new JPanel();
        mainP.setLayout(cl);
        
        gamePanel = new T2RPanel();
        gamePanel.setFrame(this);
        mainP.add("GAME_PANEL", gamePanel);
        gameScreen();
        add(mainP);
        setVisible(true); 
    }//constructor

    public void endScreen(Game g){
        System.out.println("Ending the game");
        endJPanel = new EndPanel(g, gamePanel);
        endJPanel.setFrame(this);
        mainP.add("END_PANEL", endJPanel);
        cl.show(mainP, "END_PANEL");
    }

    public void gameScreen(){
        
        System.out.println("gameScreen");
        gamePanel.setFrame(this);
        cl.show(mainP, "GAME_PANEL");
        //gamePanel.setIsLookingAtMap(true);
    }

}//class
