package main;

import java.util.ArrayList;
import java.util.List;

public class Desk {


    private final Poker poker;

    private List<player> players;

    public Desk()
    {
        this.poker=new Poker();
        this.players=new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            players.add(new player("player"+i));
        }
        DealHands();
    }

    public Poker getPoker() {
        return poker;
    }

    public List<player> getPlayers() {
        return players;
    }

    public void DealHands()
    {
        int i=0;
        while (i<2) {
            for (player player : players) {
                player.getHands().add(poker.DealCard());
            }
            i++;
        }
    }
}
