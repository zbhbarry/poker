package main;

import java.util.*;

public class ShapeJude {


    public enum Shape {
        HIGH_CARD(1),
        ONE_PAIR(2),
        TWO_PAIR(3),
        THREE_OF_A_KIND(4),
        STRAIGHT(5),
        FLUSH(6),
        FULL_HOUSE(7),
        FOUR_OF_A_KIND(8),
        STRAIGHT_FLUSH(9);

        private final int value;

        // Constructor
        Shape(int value) {
            this.value = value;
        }

        // Get the hand rank based on the numerical value
        public static Shape fromValue(int value) {
            for (Shape shape : Shape.values()) {
                if (shape.value == value) {
                    return shape;
                }
            }
            throw new IllegalArgumentException("Invalid hand rank value: " + value);
        }

        // Get the numerical value corresponding to the hand rank
        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.name();
        }

    }

    private List<Card> CommunityCard;

    private List<Card> AllCard;

    private List<Integer> Max;//存储最大手牌点数

    private Player player;

    Map<Integer, Integer> map;//记录每个点数的出现次数

    public List<Integer> getMax() {
        return Max;
    }

    public void setMax(List<Integer> max) {
        Max = max;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Card> getCommunityCard() {
        return CommunityCard;
    }

    public void setCommunityCard(List<Card> communityCard) {
        CommunityCard = communityCard;
    }

    public List<Card> getAllCard() {
        return AllCard;
    }

    public void setAllCard(List<Card> allCard) {
        AllCard = allCard;
    }

    public ShapeJude() {
        CommunityCard = new ArrayList<>();
        AllCard = new ArrayList<>();
        Max = new ArrayList<>();
        map = new HashMap<>();
    }

    //信息初始化
    public void SetInfo(Player player, List<Card> communityCard) {
        setPlayer(player);
        if(CommunityCard==null) {
            setCommunityCard(communityCard);//为了写测试类
        }

        //清空上一个的玩家的所有牌和map工具
        AllCard.clear();
        AllCard.addAll(this.player.getHands());
        AllCard.addAll(CommunityCard);
        map.clear();

        //重新载入map
        for (Card card : AllCard) {
            int rank = card.rank.getValue();
            map.put(rank, map.containsKey(rank) ? map.get(rank) + 1 : 1);
        }
        Collections.sort(AllCard);//对所有卡牌依据点数排序
    }

    public void CardJude() {


        if (isStraightFlush()) {
            player.setShape(Shape.STRAIGHT_FLUSH);
        } else if (isFourOfKind()) {
            player.setShape(Shape.FOUR_OF_A_KIND);
        } else if (isFullHouse()) {
            player.setShape(Shape.FULL_HOUSE);
        } else if (isFlush(true,null)) {
            player.setShape(Shape.FLUSH);
        } else if (isStraight(AllCard)) {
            player.setShape(Shape.STRAIGHT);
        } else if (isThreeOfKind()) {
            player.setShape(Shape.THREE_OF_A_KIND);
        } else if (isTwoPair()) {
            player.setShape(Shape.TWO_PAIR);
        } else if (isOnePair()) {
            player.setShape(Shape.ONE_PAIR);
        } else if (isHighCard()) {
            player.setShape(Shape.HIGH_CARD);
        } else {
            System.out.println("没有合适的牌型");
        }

        player.setMaxShapeCards(Max);

    }


    private boolean isHighCard() {

        Max.clear();
        Set<Integer> cards = new HashSet<>();
        for (Card card : AllCard) {
            int rank = card.rank.getValue();
            if (!cards.add(rank)) {
                return false;
            }
        }

        //添加最大牌值
        for (int i = 0; i < 5; i++) {
            Max.add(AllCard.get(i).rank.getValue());
        }

        return true;
    }


    private boolean isOnePair() {


        Max.clear();
        int PairCount = 0;
        int PairNum = 0;
        int Index = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (value >= 3) {
                return false;
            } else if (value == 2) {
                PairCount++;
                PairNum = key;
            }
        }

        if (PairCount > 1 || PairCount == 0) {
            return false;
        }

        Max.add(PairNum);
        //添加最大牌值
        while (Max.size() < 4) {
            int rank = AllCard.get(Index).rank.getValue();
            if (rank != PairNum) {
                Max.add(rank);
            }
            Index++;
        }

        return true;
    }


    private boolean isTwoPair() {

        Max.clear();

        List<Integer> PairNum = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (value >= 3) {
                return false;
            } else if (value == 2) {
                PairNum.add(key);
            }
        }

        //对对子的值降序排序
        PairNum.sort((o1, o2) -> o2 - o1);
        if (PairNum.size() < 2) {
            return false;
        } else if (PairNum.size() > 2) {
            PairNum.remove(PairNum.size() - 1);
        }

        Max.addAll(PairNum);

        for (Card card : AllCard) {
            int rank = card.rank.getValue();
            if (!PairNum.contains(rank)) {
                Max.add(rank);
                return true;
            }
        }
        return true;
    }


    private boolean isThreeOfKind() {
        Max.clear();
        List<Integer> ThreeKind = new ArrayList<>();
        int Index = 0;

        //判断是否有值超过三次
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (value > 3 || value == 2) {
                return false;
            } else if (value == 3) {
                ThreeKind.add(key);
            }
        }

        //如果没有的话返回false，并将三条的值放入
        if (ThreeKind.size() == 1) {
            Max.add(ThreeKind.get(0));
        } else {
            return false;
        }

        //将剩余三条外的值补齐
        while (Max.size() < 3) {
            int rank = AllCard.get(Index).rank.getValue();
            if (rank != ThreeKind.get(0)) {
                Max.add(rank);
            }
            Index++;
        }
        return true;

    }

    private boolean isStraight(List<Card> AllCard) {

        Max.clear();
        int MinNum = 0;
        Set<Integer> ranks = new HashSet<>();
        for (Card card : AllCard) {
            ranks.add(card.rank.getValue());
        }

        if (ranks.size() < 5) {
            return false;
        }

        //将set里面的数字放入集合中并且排序
        List<Integer> storage = new ArrayList<>(ranks);
        storage.sort(((o1, o2) -> o1 - o2));

        int MinFlag = 0;
        int count = 0;
        int LastNum = 0;

        //如果包含‘A’，初始值设置为1;
        if (AllCard.get(0).rank.getValue() == 14) {
            MinFlag = 1;
            LastNum = 1;
            count++;
        }

        for(int i = 0; i < storage.size(); i++) {
            int rank = storage.get(i);

            //设置初始值
            if (MinFlag == 0) {
                MinFlag = rank;
                count++;

            } else {
                if (rank == LastNum + 1) {
                    count++;
                } else if (rank > LastNum + 1) {
                    MinFlag = rank;
                    count = 1;
                }
            }

            //如果总数到达5，说明有顺子
            if (count == 5) {
                MinNum = MinFlag;
            } else if (count > 5) {
                MinFlag++;
                MinNum = MinFlag;
            }

            LastNum = rank;
        }

        if (MinNum == 0) {
            return false;
        } else {

            for (int i = MinNum + 4; i >= MinNum; i--) {
                Max.add(i);
            }
            return true;
        }

    }

    private boolean isFlush (boolean store,List<Card> StoreAll)
    {

         Max.clear();
         Map<Card.Suit,List<Integer>> SuitMap=new HashMap<>();
         Map<Card.Suit,List<Card>> SuitCard=new HashMap<>();

         //将同花色的卡牌的值写入对应的集合,并且将不同花色的卡牌写入集合
         for (Card card : AllCard) {
             Card.Suit suit=card.suit;
             int rank=card.rank.getValue();
             if(!SuitMap.containsKey(suit)) {
                 List<Integer> ranks=new ArrayList<>();
                 List<Card> cards=new ArrayList<>();
                 ranks.add(rank);
                 cards.add(card);
                 SuitMap.put(suit,ranks);
                 SuitCard.put(suit,cards);
             }else {
                 SuitMap.get(suit).add(rank);
                 SuitCard.get(suit).add(card);
             }
         }


         //判断是否存储，如果存储就返回，如果不存储就为同花顺的后续判断做准备
         if(store) {
             //判断是否有花色的数量超过4张
             for (Map.Entry<Card.Suit, List<Integer>> entry : SuitMap.entrySet()) {

                 List<Integer> ranks=entry.getValue();
                 if(ranks.size()>=5) {
                     ranks.sort((o1, o2) -> o2-o1);
                     Max.addAll(ranks.subList(0,5));
                     return true;
                 }
             }
         }else {
             for (Map.Entry<Card.Suit, List<Card>> entry : SuitCard.entrySet()) {

                 List<Card> cards=entry.getValue();
                 if(cards.size()>=5) {
                     Collections.sort(cards);
                     StoreAll.addAll(cards);
                     return true;
                 }
             }
         }


         return false;

    }

    private boolean isFullHouse ()
    {
        Max.clear();
        //设置两个区域，一个三条的集合，一个对子的集合
        List<Integer> ThreeKind=new ArrayList<>();
        List<Integer> Pair=new ArrayList<>();

        //遍历map填充两个集合
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

            int rank=entry.getKey();
            int value=entry.getValue();
            if(value>3) {
                return false;
            }else if(value==3) {
                ThreeKind.add(rank);
            } else if (value==2) {
                Pair.add(rank);
            }
        }

        //对两个集合排序
        ThreeKind.sort((o1, o2) -> o2-o1);
        Pair.sort((o1, o2) -> o2-o1);

        //填充Max
        if(ThreeKind.isEmpty())
        {
            return false;
        }else if(ThreeKind.size()==2) {
            Max.addAll(ThreeKind);
        } else if (ThreeKind.size()==1) {
            if(Pair.isEmpty()) {
                return false;
            }else {
                Max.addAll(ThreeKind);
                Max.add(Pair.get(0));
            }
        }
        return true;

    }

    private boolean isFourOfKind ()
    {
        Max.clear();
        int FourKind = 0;

        //判断是否有值是四次
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if(value==4)
            {
                FourKind=key;
                Max.add(key);
            }
        }

        if(FourKind==0)
        {
            return false;
        }else
        {
            for (Card card : AllCard) {
                int rank=card.rank.getValue();
                if(rank!=FourKind) {
                    Max.add(rank);
                    return true;
                }
            }
        }
       return true;
    }

    private boolean isStraightFlush ()
    {
        List<Card> AllFlush=new ArrayList<>();
        if(isFlush(false,AllFlush)) {
            return isStraight(AllFlush);
        }else {
            return false;
        }

    }



}