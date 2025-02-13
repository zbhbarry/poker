package main;


import java.util.ArrayList;
import java.util.List;

public class PokerGame {



    public static void main(String[] args) {


        final int CHIPS=3000;

        List<Player> players=new ArrayList<>();


        Player player1=new AiPlayer("AI_1",CHIPS);
        Player player2=new AiPlayer("AI_2",CHIPS);
        Player player3=new AiPlayer("AI_3",CHIPS);
        Player player4=new AiPlayer("AI_4",CHIPS);
        Player player5=new AiPlayer("AI_5",CHIPS);
        Player player6=new AiPlayer("AI_6",CHIPS);



        players.add(player6);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);




        Desk desk=new Desk(players);

        int i=1;

        while (true) {
            System.out.println("---------------第" + i + "局----------------");
            desk.round();
            i++;
        }




    }
}