import java.nio.file.PathMatcher;
import java.util.ArrayList;

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

        System.out.println();
        System.out.println("Player 1 is claiming " + r);
        r.claim(game.getPlayers().get(0));
        System.out.println(r.getPlayer());

        System.out.println();
        System.out.println("Trying to get longest connected railroad");
        ArrayList<Railroad> railroadList  = new ArrayList<Railroad>();
        railroadList.add(mapT.getRailroad(mapT.getCity("ber"), mapT.getCity("fra")));
        railroadList.add(mapT.getRailroad(mapT.getCity("dan"), mapT.getCity("ber")));
        railroadList.add(mapT.getRailroad(mapT.getCity("dan"), mapT.getCity("rig")));
        railroadList.add(mapT.getRailroad(mapT.getCity("wil"), mapT.getCity("rig")));
        railroadList.add(mapT.getRailroad(mapT.getCity("pam"), mapT.getCity("par")));

        System.out.println(mapT.getRailroad(mapT.getCity("ber"), mapT.getCity("fra")).getNeighbors());

        //System.out.println(railroadList.get(1));

        // int europeanExpress = mapT.dfSearch(railroadList, r);
        // System.out.println(europeanExpress);

        // System.out.println();
        // System.out.println("Tryng to get Longest railroad from player");
        // game.getPlayers().get(0);

        // for (Railroad tempRailroad : railroadList){
        //     tempRailroad.claim(game.getPlayers().get(0));
        //     System.out.println(game.getPlayers().get(0).getRailroadList());
        // }

        // int maxOfPlayer0 = mapT.longestRailroadOfPlayer(game.getPlayers().get(0));
        // System.out.println(maxOfPlayer0);

        System.out.println("trying to see in a railroad is claimed");
        boolean isClaimed= mapT.searchIfClaimed(mapT.getCity("BeR"), mapT.getCity("dan"), game.getPlayers().get(0));
        System.out.println("Did player 1 claim this? " + isClaimed);
        isClaimed= mapT.searchIfClaimed(mapT.getCity("BeR"), mapT.getCity("dan"), game.getPlayers().get(1));
        System.out.println("Did player 1 claim this? " + isClaimed);


        System.out.println();
        System.out.println("trying to get other railroad");
        Railroad dr1 = mapT.getRailroad(mapT.getCity("PAM"), mapT.getCity("PAR"));
        Railroad dr2 = dr1.getSisterRailroad();
        System.out.println(dr1);
        System.out.println(dr2);

        System.out.println();
        System.out.println("Trying to shuffle deck");
        while(!game.getTrainCards().isEmpty()){
            game.drawTrainCard();
        }

        while(game.getPlayers().get(game.getPlayerTurn() - 1 ).getTrainCards().isEmpty() == false){
            //TrainCard t = game.getPlayers().get(game.getPlayerTurn() - 1 ).getTrainCards();
        }
        System.out.println(game.getDiscardCard());
        System.out.println(game.drawTrainCard());
        System.out.println(game.getTrainCards());

    }//main ends
}//class ends



