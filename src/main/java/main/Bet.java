package main;

import java.util.ArrayList;
import java.util.List;

public class Bet {


    private int pot;
    private int GameRound;
    private int MinBet;
    private final int PlayerSize;
    private Player CurrentPlayer;

    private int Flag;//表示第一回合大小盲注是否下注完成

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getMinBet() {
        return MinBet;
    }

    public void setMinBet(int minBet) {
        MinBet = minBet;
    }

    public int getGameRound() {
        return GameRound;
    }

    public void setGameRound(int gameRound) {
        GameRound = gameRound;
    }

    public int getPlayerSize() {
        return PlayerSize;
    }

    public Player getCurrentPlayer() {
        return CurrentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        CurrentPlayer = currentPlayer;
    }

    public Bet(int playerSize)
    {
        this.GameRound=0;
        this.MinBet=0;
        this.pot=0;
        this.PlayerSize=playerSize;

    }

    public void initBet()
    {
        setCurrentPlayer(null);
        setMinBet(0);
        setPot(0);
        setGameRound(0);
        if(PlayerSize>2) {
            Flag=2;
        }else {
            Flag=1;
        }
    }

    /*
        FOLD(0),
        CHECK(1),
        CALL(2),
        BET(3),
        BET_10(4),
        BET_25(5),
        BET_50(6),
        RAISE_2(4),
        RAISE_3(5),
        POT(6),
        POT_2(7),
        ALLIN(8);
     */

    //获取合理动作
    public List<Player.Action> GetValidActions(Player player) {

        setCurrentPlayer(player);
        List<Player.Action> actions = new ArrayList<>();

        int playerChips = player.getChips(); // 获取玩家的筹码
        int potHalf = pot / 2;  // 底池的一半

        //(player.getPosition() == 0 || (PlayerSize > 2 && player.getPosition() == 1))
        if (GameRound == 1) {
            if (Flag > 0) {
                if (playerChips > 1) {
                    actions.add(Player.Action.BET);
                } else {
                    actions.add(Player.Action.ALLIN);
                }
                Flag--;
                return actions;
            }
        }

        // 1. 默认可选操作
        actions.add(Player.Action.FOLD);

        // 2. 如果没有下注（MinBet == 0）
        if (MinBet == 0) {
            actions.add(Player.Action.CHECK); // 可以选择过牌
            if(playerChips>=2) actions.add(Player.Action.BET);   // 默认最小下注

            if (playerChips * 0.1 > 2) actions.add(Player.Action.BET_10);
            if (playerChips * 0.25 > 2) actions.add(Player.Action.BET_25);
            if (playerChips * 0.5 > 2) actions.add(Player.Action.BET_50);
        }
        // 3. 如果有人下注（MinBet > 0）
        else {
            if (playerChips > MinBet) actions.add(Player.Action.CALL); // 可以跟注

            // 允许加注的情况
            if (playerChips > MinBet * 2) actions.add(Player.Action.RAISE_2);
            if (playerChips > MinBet * 3) actions.add(Player.Action.RAISE_3);

        }

        // 4. 如果底池大于 0，允许选择 POT_HALF、POT 和 POT_2
        if (pot > 0) {
            if (playerChips > potHalf && potHalf > MinBet*2) actions.add(Player.Action.POT_HALF);
            if (playerChips > pot && pot > MinBet*2) actions.add(Player.Action.POT);
            if (playerChips > pot*2 && pot*2 > MinBet*2) actions.add(Player.Action.POT_2);
        }

        // 5. 允许全押
        actions.add(Player.Action.ALLIN);

        return actions;
    }


    //执行动作
    public void DoAction(Player.Action action,Player player)
    {

        int chipsNum=player.getChips();
        int betNum=0;
        int currentBet=player.getCurrentBet();

        switch (action) {

            case FOLD -> player.setIsFold(1);
            case CHECK-> player.setCurrentBet(0);
            case CALL -> {
                betNum=MinBet-currentBet;
                player.setCurrentBet(MinBet);
                player.setChips(chipsNum-betNum);
            }
            case BET -> {
                betNum=2;
                player.setCurrentBet(betNum);
                player.setChips(chipsNum-betNum);
                MinBet=2;
            }
            case BET_10 -> {
                betNum=(int) (chipsNum * 0.1);
                player.setCurrentBet(betNum);
                player.setChips(chipsNum-betNum);
                MinBet=betNum;
                player.setRaiseNum(player.getRaiseNum()+1);
            }
            case BET_25 -> {
                betNum=(int) (chipsNum * 0.25);
                player.setCurrentBet(betNum);
                player.setChips(chipsNum-betNum);
                MinBet=betNum;
                player.setRaiseNum(player.getRaiseNum()+1);
            }
            case BET_50 -> {
                betNum=(int) (chipsNum * 0.5);
                player.setCurrentBet(betNum);
                player.setChips(chipsNum-betNum);
                MinBet=betNum;
                player.setRaiseNum(player.getRaiseNum()+1);
            }
            case RAISE_2 -> {
                betNum= MinBet*2-currentBet;
                player.setCurrentBet(MinBet*2);
                player.setChips(chipsNum-betNum);
                MinBet=MinBet*2;
                player.setRaiseNum(player.getRaiseNum()+1);
            }
            case RAISE_3 -> {
                betNum=MinBet*3-currentBet;
                player.setCurrentBet(MinBet*3);
                player.setChips(chipsNum-betNum);
                MinBet=MinBet*3;
                player.setRaiseNum(player.getRaiseNum()+1);
            }
            case POT -> {
                betNum=pot-currentBet;
                player.setCurrentBet(pot);
                player.setChips(chipsNum-betNum);
                MinBet=pot;
                player.setRaiseNum(player.getRaiseNum()+1);
            }
            case POT_2 -> {
                betNum=pot*2-currentBet;
                player.setCurrentBet(pot*2);
                player.setChips(chipsNum-betNum);
                MinBet=pot*2;
                player.setRaiseNum(player.getRaiseNum()+1);
            }
            case POT_HALF -> {
                betNum=pot/2-currentBet;
                player.setCurrentBet(pot/2);
                player.setChips(chipsNum-betNum);
                MinBet=pot/2;
                player.setRaiseNum(player.getRaiseNum()+1);
            }
            case ALLIN -> {
                betNum=chipsNum;
                player.setCurrentBet(chipsNum+currentBet);
                player.setChips(0);
                player.setIsAllIn(1);
                if(MinBet < chipsNum) {
                    MinBet = chipsNum + currentBet;
                    player.setRaiseNum(player.getRaiseNum()+1);
                }
            }
            default -> System.out.println("未知动作");
        }


        setPot(pot+betNum);




    }

    public void getBetInfo(Player player, Player.Action action,int betNum)
    {
        System.out.println((player.getName()
                + " 的动作是: " + action
                + " 下注筹码量: " + betNum
                + " 回合中下注总筹码量: " + player.getCurrentBet()
                + " 剩余筹码量是: " + player.getChips()
                + " 底池数量是: " + pot
        ));

    }








}
