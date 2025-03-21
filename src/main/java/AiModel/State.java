package AiModel;

import main.Card;
import main.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class State {


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
    public double[] getState(Player player,
                             int round,
                             int playerSize,
                             int liver,
                             long TotalPot,
                             long pot,
                             int TotalRaise,
                             List<Card> communityCards,
                             List<Player> players) {
        List<Double> states = new ArrayList<>();

        states.add(roundToThreeDecimalPlaces((double) round / 4));
        states.add(roundToThreeDecimalPlaces((double) playerSize));
        states.add(roundToThreeDecimalPlaces((double) liver));
        states.add(roundToThreeDecimalPlaces((double) player.getPosition()));
        states.add(roundToThreeDecimalPlaces((double) pot / TotalPot));
        if(pot!=0){
            states.add(roundToThreeDecimalPlaces((double) player.getTotalBet() / pot));
        }else {
            states.add(0.000);
        }

        if (TotalRaise == 0) {
            states.add(0.000);
        } else {
            states.add(roundToThreeDecimalPlaces((double) player.getRaiseNum() / TotalRaise));
        }

        for (Player other : players) {
            if (other != player) {
                if(pot!=0) {
                    states.add(roundToThreeDecimalPlaces((double) other.getTotalBet() / pot));
                }else {
                    states.add(0.000);
                }
            }
        }

        if (TotalRaise == 0) {
            for (Player other : players) {
                if (other != player) {
                    states.add(0.000);
                }
            }
        } else {
            for (Player other : players) {
                if (other != player) {
                    states.add(roundToThreeDecimalPlaces((double) other.getRaiseNum() / TotalRaise));
                }
            }
        }

        states.add(roundToThreeDecimalPlaces(getCardVector(player.getHands().get(0))));
        states.add(roundToThreeDecimalPlaces(getCardVector(player.getHands().get(1))));

        if (communityCards.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                states.add(0.000);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                if (i + 1 <= communityCards.size()) {
                    states.add(roundToThreeDecimalPlaces(getCardVector(communityCards.get(i))));
                } else {
                    states.add(0.000);
                }
            }
        }

        if (player.getShape() != null) {
            states.add(roundToThreeDecimalPlaces((double) player.getShape().getValue() / 9));
        } else {
            states.add(0.000);
        }

        return states.stream().mapToDouble(Double::doubleValue).toArray();
    }


    //设置精度
    public static double roundToThreeDecimalPlaces(double value) {
        return new BigDecimal(value).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }


    //设置每个牌的编号，归一化
    public double getCardVector(Card card)
    {
        //CardValue=(Suit−1)×13+Rank
        int Suit=card.getSuit().getValue();
        int Rank=card.getRank().getValue();
        return  (double) ((Suit - 1) * 13 + Rank - 2) /52;
    }



}
