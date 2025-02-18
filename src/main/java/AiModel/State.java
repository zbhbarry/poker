package AiModel;

import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.List;

public class State {


    public double[] getState(Player player,
                             int round,
                             int playerSize,
                             int liver,
                             int TotalPot,
                             int pot,
                             int TotalRaise,
                             List<Card> communityCards,
                             List<Player> players) {
        List<Double> states=new ArrayList<>();
        /*
        - 回合数
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
        //- 手牌和牌桌排名的组合
        - 手牌与牌桌实力的组合
        //- 手牌与牌桌潜力的组合
        */
        states.add((double) (round/4));
        states.add((double) playerSize);
        states.add((double) liver);
        states.add((double) player.getPosition());
        states.add((double)pot/TotalPot);//玩家总筹码量的百分比
        states.add((double)player.getTotalBet()/pot);//玩家下注量和底池的比较

        if(TotalRaise==0) {
            states.add((double) 0);//玩家的加注次数和所有玩家总加注次数比较
        }else {
            states.add((double) player.getRaiseNum()/TotalRaise);//玩家的加注次数和所有玩家总加注次数比较
        }


        for (Player other : players) {
            if (other != player) {
                states.add( (double)other.getTotalBet() / pot);
            }
        }

        if(TotalRaise==0) {
            for (Player other : players) {
                if(other!=player) {
                    states.add((double) 0);
                }
            }
        }else {
            for (Player other : players) {
                if(other!=player) {
                    states.add((double) other.getRaiseNum()/TotalRaise);
                }
            }//玩家的加注次数和所有玩家总加注次数比较
        }

        states.add(getCardVector(player.getHands().get(0)));
        states.add(getCardVector(player.getHands().get(1)));
        if(communityCards.isEmpty()){
            for (int i = 0; i < 5; i++) {
                states.add((double)0);
            }
        }else {
            for (int i = 0; i < 5; i++) {
                if(i+1<=communityCards.size()){
                    states.add(getCardVector(communityCards.get(i)));
                }else {
                    states.add((double)0);
                }
            }
        }
        //states.add((double) (player.getRank()/playerSize));
        if(player.getShape()!=null){
            states.add((double) player.getShape().getValue()/9);
        }else {
            states.add((double) 0);
        }


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
