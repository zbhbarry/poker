package main;

import java.util.*;

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
            shapeJude.getAllCard().clear();
            for (Player player : players) {
                player.InitPlayer();
            }
            bet.initBet();
            LivePlayer=new HashSet<>(players);
        }

        //分位置
        DealPosition();
        //getPlayerInfo();
        System.out.println(" ");

        //手牌轮
        DealHands();
        //setPlayerShape();
        BetRound();
        //getPlayerInfo();

        //翻牌轮
        DealFlop();
        //setPlayerShape();
        BetRound();
        //getPlayerInfo();

        //转牌轮
        DealTurn();
        //setPlayerShape();
        BetRound();
        //getPlayerInfo();

        //河牌轮
        DealRiver();
        //setPlayerShape();
        BetRound();
        //getPlayerInfo();


        setPlayerShape();
        getPlayerInfo();


        JudeWinner();


        getLiverPlayerInfo();


    }

    public void setPlayerShape() {

        for (Player player : players) {
            if(!shapeJude.getCommunityCard().isEmpty()) {

                shapeJude.SetInfo(player, null);
                shapeJude.CardJude();

            }
        }

    }

    public void getLiverPlayerInfo()
    {
        //测试判断是否准确;
        System.out.println("存活的玩家: ");
        for (Player player : LivePlayer) {
            //System.out.print(player.getName()+",");
            System.out.print(player.getName()+"的起始筹码量是"+player.getOriginChips()+",");
            System.out.println(player.getName()+"的当前筹码数量是"+player.getChips());
        }
        System.out.println(" ");
        System.out.println("---------------------------------");
    }

    public void getPlayerInfo()
    {
        // System.out.println("第" + GameRound + "回合");
        System.out.println("公共牌是"+shapeJude.getCommunityCard());
        setPlayerShape();
        for (Player player : players) {

            String name=player.getName();

            System.out.print(name+"的手牌是"+player.getHands()+",");
            System.out.print(name+"的最大组成牌是"+player.getMaxShapeCards()+",");
            System.out.print(name+"的最大牌型是"+player.getShape()+",");
            System.out.print(name+"的总下注量"+player.getTotalBet()+",");
            System.out.print(name+"的弃牌状态"+player.getIsFold()+",");
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
                        if (action == Player.Action.FOLD) {
                            LivePlayer.remove(player);
                        }
                    }
                }
            } while (isEnd());
        }

        for (Player player : players) {
            player.setTotalBet(player.getTotalBet()+player.getCurrentBet());
            player.setCurrentBet(0);
        }

        //System.out.println(" ");
    }

    //判断下注轮是否结束
    public boolean isEnd()
    {
        for (Player player : players) {
            if(!(player.getIsAllIn()==1||player.getIsFold()==1))
            {
                if(bet.getMinBet()>player.getCurrentBet() && LivePlayer.size()>1)
                {
                    return true;
                }
            }
        }
        return false;
    }


    //判断赢家并分配底池
    public void JudeWinner()
    {

        List<Player> Winners = getPlayerList();

        setIfWin_fold(Winners);

        Winners.removeIf(player -> player.getIsFold() == 1);


        System.out.println("底池数量是 :"+bet.getPot());
        //查看存活玩家信息
        for (Player player : Winners) {
            System.out.println(player.getName() +"    "
                    + player.getShape() +"    "
                    + player.getMaxShapeCards()+"   "
                    + player.getTotalBet()
            );
        }

        distributePot(Winners);

        setIfWin_noFold(Winners);

    }

    //对玩家集合通过大小排序
    private List<Player> getPlayerList() {
        List<Player> Winners = new ArrayList<>(players);
        Winners.sort(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                int shape1=o1.getShape().getValue();
                int shape2=o2.getShape().getValue();
                if(shape1!=shape2) {
                    return o2.getShape().getValue() - o1.getShape().getValue();
                }else {
                    // 牌型相同，按最大牌比较
                    return compareMaxCards(o1.getMaxShapeCards(), o2.getMaxShapeCards());
                }
            }

        });
        System.out.println("排序后");
        for (Player player : Winners) {
            System.out.println(player.getName()+"   "+player.getShape()+"  "+player.getMaxShapeCards());
        }
        System.out.println("  ");
        return Winners;
    }

    // 牌型相同时，比较最大牌
    private int compareMaxCards(List<Integer> cards1, List<Integer> cards2) {
        int size = Math.min(cards1.size(), cards2.size());

        for (int i = 0; i < size; i++) {
            int comparison = Integer.compare(cards2.get(i), cards1.get(i)); // 降序排列
            if (comparison != 0) {
                return comparison;  // 找到不同的牌，直接返回比较结果
            }
        }

        // 如果所有元素都相同，返回 0，表示平局
        return 0;
    }


    //用于判断弃牌选手是否可以赢得比赛
    private void setIfWin_fold(List<Player> winners)
    {
        List<Player> FirstClass=new ArrayList<>();
        FirstClass.add(winners.get(0));
        ShapeJude.Shape MaxShape=winners.get(0).getShape();
        Player MaxPlayer= winners.get(0);

        for (int i = 1; i < winners.size(); i++) {
            Player curPlayer= winners.get(i);
            if(curPlayer.getShape()==MaxShape && compareMaxCards(MaxPlayer.getMaxShapeCards(),curPlayer.getMaxShapeCards())==0) {
                FirstClass.add(curPlayer);
            }
        }

        for (Player player : FirstClass) {
            if(player.getIsFold()==1)
            {
                player.setIsWin(1);
            }
        }
    }

    //设置没弃牌玩家是否赢了
    private void setIfWin_noFold(List<Player> winners)
    {
        for (Player player : winners) {
            int history=player.getOriginChips();
            int now=player.getChips();
            if(history<=now) {
                player.setIsWin(1);
            }else {
                player.setIsWin(0);
            }
        }
    }

    //分配底池
    private void distributePot(List<Player> winners) {

        do {

            if (winners.size() == 1) {
                winners.get(0).setChips(bet.getPot() + winners.get(0).getChips());
                winners.clear();
                bet.setPot(0);
            } else {

                List<Player> firstClass = new ArrayList<>();
                firstClass.add(winners.get(0));
                ShapeJude.Shape MaxShape = winners.get(0).getShape();
                Player MaxPlayer = winners.get(0);

                //填充第一梯队
                for (int i = 1; i < winners.size(); i++) {
                    Player curPlayer = winners.get(i);
                    if (curPlayer.getShape() == MaxShape && compareMaxCards(MaxPlayer.getMaxShapeCards(), curPlayer.getMaxShapeCards()) == 0) {
                        firstClass.add(curPlayer);
                    }
                }



                winners.removeAll(firstClass);

                List<Player> otherClass = new ArrayList<>(players);
                otherClass.removeAll(firstClass);
                int sumWinner = 0;

                for (Player player : firstClass) {
                    sumWinner = player.getTotalBet() + sumWinner;
                }

                for (Player player : firstClass) {
                    int shouldWin = player.getTotalBet();
                    for (Player other : otherClass) {
                        if (sumWinner <= other.getTotalBet()) {
                            shouldWin += player.getTotalBet();
                        } else {
                            shouldWin += (int) (((double) player.getTotalBet() / sumWinner) * other.getTotalBet());

                        }
                    }
                    player.setChips(player.getChips()+shouldWin);
                    bet.setPot(bet.getPot()-shouldWin);
                }
                if(winners.isEmpty()) {
                    bet.setPot(0);
                }

            }



        }while (bet.getPot()!=0);

    }




}
