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

        //测试判断是否准确;
        System.out.println("公共牌是"+shapeJude.getCommunityCard());
        System.out.println("---------------------------------");
        for (Player player : players) {

            String name=player.getName();

            shapeJude.SetInfo(player,null);
            shapeJude.CardJude();

            System.out.println(name+"的手牌是"+player.getHands());
            System.out.println(name+"的所有手牌加公共牌是"+shapeJude.getAllCard());
            System.out.println(name+"的最大组成牌是"+player.getMaxShapeCards());
            System.out.println(name+"的最大牌型是"+player.getShape());
            System.out.println("---------------------------------");

        }
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
