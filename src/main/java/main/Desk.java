package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Desk {

    private Poker poker;

    private final List<Player> players;

    private final ShapeJude shapeJude;

    private final Bet bet;

    private int GameRound;

    private Set<Player> LivePlayer;
    

    public Poker getPoker() {
        return poker;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public ShapeJude getShapeJude() {
        return shapeJude;
    }

    public Bet getBet() {
        return bet;
    }

    public int getGameRound() {
        return GameRound;
    }

    public void setGameRound(int gameRound) {
        GameRound = gameRound;
    }

    public Set<Player> getLivePlayer() {
        return LivePlayer;
    }

    public void setLivePlayer(Set<Player> livePlayer) {
        LivePlayer = livePlayer;
    }

    public Desk(List<Player> players) {
        this.poker=new Poker();
        this.shapeJude =new ShapeJude();
        this.bet=new Bet(players.size());
        this.players=players;
        this.LivePlayer=new HashSet<>(players);
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
            bet.initBet();
            LivePlayer=new HashSet<>(players);
        }

        DealPosition();
        getPlayerInfo();
        System.out.println(" ");

        DealHands();
        BetRound();
        getPlayerInfo();

        DealFlop();
        BetRound();
        getPlayerInfo();

        DealTurn();
        BetRound();
        getPlayerInfo();

        DealRiver();
        BetRound();
        getPlayerInfo();


        //测试判断是否准确;
        System.out.print("存活的玩家: ");
        for (Player player : players) {
            if(player.getIsFold()!=1)
            {
                System.out.print(player.getName()+"  ");
            }
        }
        System.out.println(" ");
        System.out.println("---------------------------------");

    }

    public void getPlayerInfo()
    {
        System.out.println("公共牌是"+shapeJude.getCommunityCard());
        for (Player player : players) {

            String name=player.getName();

            if(!shapeJude.getCommunityCard().isEmpty()) {
                shapeJude.SetInfo(player,null);
                shapeJude.CardJude();
            }

            System.out.print(name+"的手牌是"+player.getHands()+",");
            System.out.print(name+"的所有手牌加公共牌是"+shapeJude.getAllCard()+",");
            System.out.print(name+"的最大组成牌是"+player.getMaxShapeCards()+",");
            System.out.print(name+"的最大牌型是"+player.getShape());
            System.out.println("剩余筹码数量是"+player.getChips());

        }
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
        bet.setGameRound(1);
    }

    public void DealFlop() {
        poker.DealCard();
        for (int i = 0; i < 3; i++) {
            shapeJude.getCommunityCard().add(poker.DealCard());
        }
        GameRound=2;
        bet.setGameRound(2);
    }

    public void DealTurn() {
        poker.DealCard();
        shapeJude.getCommunityCard().add(poker.DealCard());
        GameRound=3;
        bet.setGameRound(3);
    }

    public void DealRiver() {
        poker.DealCard();
        shapeJude.getCommunityCard().add(poker.DealCard());
        GameRound=4;
        bet.setGameRound(4);
    }

    //下注轮
    public void BetRound()
    {

        int count=0;

        for (Player player : players) {
            if(!(player.getIsAllIn()==1||player.getIsFold()==1)) count++;

        }

        if(count!=1) {
            do {
                for (Player player : players) {
                    if (LivePlayer.size() == 1) {
                         break;
                    }
                    if (!(player.getIsAllIn() == 1 || player.getIsFold() == 1)) {
                        Player.Action action = player.SelectAction(null, bet.GetValidActions(player));
                        bet.DoAction(action, player);
                    } else if (player.getIsFold() == 1) {
                        LivePlayer.remove(player);
                    }
                }
            } while (isEnd());
        }

        for (Player player : players) {
            player.setCurrentBet(0);
        }

        System.out.println(" ");
    }

    //判断下注轮是否结束
    public boolean isEnd()
    {
        for (Player player : players) {
            if(!(player.getIsAllIn()==1||player.getIsFold()==1))
            {
                if(bet.getMinBet()>player.getCurrentBet())
                {
                    return true;
                }
            }
        }
        return false;
    }






}
