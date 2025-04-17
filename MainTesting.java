import java.text.BreakIterator;

public class MainTesting {
    public static void main(String[] args) {
        Game game = new Game();

        MapGraph mapT = game.getMap();

        //BER DAN grey 0.310130 0.148325 F 4 0 F
        mapT.railroadExists(new City("BER 0.36544 0.25837"), new City("DAN 0.4481 0.16387"));

        System.out.println();
        System.out.println("Finding from BER to DAN");
        System.out.println(mapT.railroadExists(new City("BER 0.36544 0.25837"), new City("DAN 0.4481 0.16387")));

        System.out.println();
        System.out.println("Finding from BRE to LON");
        System.out.println(mapT.railroadExists(new City("BRE 0.08576 0.36244"), new City("LON 0.16034 0.22846")));


        System.out.println();
        System.out.println("Trying for getRailroad:");
        Railroad r = mapT.getRailroad(mapT.getCity("BeR"), mapT.getCity("dan"));
        System.out.println(r);

        System.out.println("Player 1 is claiming " + r);
        r.claim(game.getPlayers().get(0));
        System.out.println(r.getPlayer());

        
    }//main ends
}//class ends
