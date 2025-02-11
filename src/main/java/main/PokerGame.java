package main;


import java.util.ArrayList;
import java.util.List;

public class PokerGame {



    public static void main(String[] args) {


        List<Player> players=new ArrayList<>();


        Player player1=new AiPlayer("AI_1");
        Player player2=new AiPlayer("AI_2");
        Player player3=new AiPlayer("AI_3");
        Player player4=new AiPlayer("AI_4");
        Player player5=new AiPlayer("AI_5");
        Player player6=new HumanPlayer("HUMAN");



        players.add(player6);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);


        Desk desk=new Desk(players);

        for (Player player : players) {
            System.out.println(player.getName());
        }


        for (int i = 1; i < 6; i++) {

            System.out.println("---------------第"+i+"局----------------");
            desk.round();
        }



    }
}