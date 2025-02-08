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
    }

    public Poker getPoker() {
        return poker;
    }

    public List<player> getPlayers() {
        return players;
    }
}
