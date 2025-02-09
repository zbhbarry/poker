package main;

import java.util.ArrayList;
import java.util.List;

public class Desk {

    private final Poker poker;

    private final List<Player> players;

    private final ShapeJude shapeJude;

    public Desk() {
        this.poker=new Poker();
        this.shapeJude =new ShapeJude();
        this.players=new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            players.add(new Player("player"+i));
        }
        DealHands();


        DealFlop();



        DealTurn();



        DealRiver();

    }

    public Poker getPoker() {
        return poker;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void DealHands() {
        int i=0;
        while (i<2) {
            for (Player player : players) {
                player.getHands().add(poker.DealCard());
            }
            i++;
        }
    }

    public void DealFlop() {
        poker.DealCard();
        for (int i = 0; i < 3; i++) {
            shapeJude.getCommunityCard().add(poker.DealCard());
        }
    }

    public void DealTurn() {
        poker.DealCard();
        shapeJude.getCommunityCard().add(poker.DealCard());
    }

    public void DealRiver() {
        poker.DealCard();
        shapeJude.getCommunityCard().add(poker.DealCard());
    }
}
