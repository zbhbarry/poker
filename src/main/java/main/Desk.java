package main;

import java.util.ArrayList;
import java.util.List;

public class Desk {

    private Poker poker;

    private final List<Player> players;

    private final ShapeJude shapeJude;

    private int GameRound;
    

    public Poker getPoker() {
        return poker;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public ShapeJude getShapeJude() {
        return shapeJude;
    }

    public int getGameRound() {
        return GameRound;
    }

    public void setGameRound(int gameRound) {
        GameRound = gameRound;
    }

    public Desk(List<Player> players) {
        this.poker=new Poker();
        this.shapeJude =new ShapeJude();
        this.players=players;

    }

    //表示一轮游戏
    public void round()
    {

        //数据初始化\
        {
            poker = new Poker();
            shapeJude.getCommunityCard().clear();
            for (Player player : players) {
                player.InitPlayer();
            }
            
        }

        DealPosition();
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
        System.out.println("                              ");
    }

    
    public void DealPosition()
    {
        Player player=players.get(0);
        players.remove(0);
        players.add(player);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setPosition(i);
        }
    }


    public void DealHands() {
        int i=0;
        while (i<2) {
            for (Player player : players) {
                player.getHands().add(poker.DealCard());
            }
            i++;
        }
        GameRound=1;
    }

    public void DealFlop() {
        poker.DealCard();
        for (int i = 0; i < 3; i++) {
            shapeJude.getCommunityCard().add(poker.DealCard());
        }
        GameRound=2;
    }

    public void DealTurn() {
        poker.DealCard();
        shapeJude.getCommunityCard().add(poker.DealCard());
        GameRound=3;
    }

    public void DealRiver() {
        poker.DealCard();
        shapeJude.getCommunityCard().add(poker.DealCard());
        GameRound=4;
    }
}
