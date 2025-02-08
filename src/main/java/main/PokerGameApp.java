package main;


public class PokerGameApp  {

    public static void main(String[] args) {

        Desk desk=new Desk();
        for (player player : desk.getPlayers()) {
            System.out.println(player.toString());
        }

    }
}