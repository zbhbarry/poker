package AiModel;

import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.List;

public class State {


    Player player;
    private int PlayerSize;

    private int Liver;

    private int TotalPot;

    private int pot;

    private int TotalRaise;

    private List<Integer> betPlayers;

    private List<Integer> RaisePlayers;

    private List<Card> communityCards;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPlayerSize() {
        return PlayerSize;
    }

    public void setPlayerSize(int playerSize) {
        PlayerSize = playerSize;
    }

    public int getLiver() {
        return Liver;
    }

    public void setLiver(int liver) {
        Liver = liver;
    }

    public int getTotalPot() {
        return TotalPot;
    }

    public void setTotalPot(int totalPot) {
        TotalPot = totalPot;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getTotalRaise() {
        return TotalRaise;
    }

    public void setTotalRaise(int totalRaise) {
        TotalRaise = totalRaise;
    }

    public List<Integer> getBetPlayers() {
        return betPlayers;
    }

    public void setBetPlayers(List<Integer> betPlayers) {
        this.betPlayers = betPlayers;
    }

    public List<Integer> getRaisePlayers() {
        return RaisePlayers;
    }

    public void setRaisePlayers(List<Integer> raisePlayers) {
        RaisePlayers = raisePlayers;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public void setCommunityCards(List<Card> communityCards) {
        this.communityCards = communityCards;
    }

    public State(Player player, int playerSize, int liver, int TotalPot, int pot, int TotalRaise, List<Card> communityCards) {
        this.PlayerSize = playerSize;
        this.Liver=liver;
        this.TotalPot=TotalPot;
        this.pot=pot;
        this.TotalRaise=TotalRaise;
        this.betPlayers=new ArrayList<>();
        this.RaisePlayers=new ArrayList<>();
        this.communityCards=communityCards;
    }



    public double[] getState()
    {
        List<Double> states=new ArrayList<>();
        /*
        - 玩家人数
        - 本轮活跃玩家（未弃牌玩家）人数
        - 玩家位置
        - 彩池金额
        - 自己投入彩池的金额
        - 自己的加注次数
        - 每位玩家下注的金额
        - 每位玩家的加注次数
        - 手牌
        - 桌面牌
        - 手牌和牌桌排名的组合
        - 手牌与牌桌实力的组合
        - 手牌与牌桌潜力的组合
        */
        states.add((double) PlayerSize);
        states.add((double) Liver);
        states.add((double) player.getPosition());
        states.add((double) pot/TotalPot);
        states.add((double) (player.getCurrentBet()/pot));
        states.add((double) (player.getRaiseNum()/TotalRaise));
        for (Integer integer : betPlayers) {
            states.add((double) (integer/pot));
        }
        for (Integer integer : RaisePlayers) {
            states.add((double) (integer/TotalRaise));
        }
        states.add(getCardVector(player.getHands().get(0)));
        states.add(getCardVector(player.getHands().get(1)));
        for (Card card : communityCards) {
            states.add(getCardVector(card));
        }
        states.add((double) (player.getRank()/PlayerSize));
        states.add((double) (player.getShape().getValue()/9));

        return states.stream().mapToDouble(Double::doubleValue).toArray();
    }


    public double getCardVector(Card card)
    {
        //CardValue=(Suit−1)×13+Rank
        int Suit=card.getSuit().getValue();
        int Rank=card.getRank().getValue();
        return  (double) ((Suit - 1) * 13 + Rank - 2) /52;
    }



}
