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
        setCommunityCard(communityCard);
        AllCard.clear();
        AllCard.addAll(this.player.getHands());
        AllCard.addAll(CommunityCard);
        map.clear();
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
        } else if (isFlush()) {
            player.setShape(Shape.FLUSH);
        } else if (isStraight(AllCard, true)) {
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

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (value > 3 || value == 2) {
                return false;
            } else if (value == 3) {
                ThreeKind.add(key);
            }
        }

        if (ThreeKind.size() == 1) {
            Max.add(ThreeKind.get(0));
        } else {
            return false;
        }

        while (Max.size() < 3) {
            int rank = AllCard.get(Index).rank.getValue();
            if (rank != ThreeKind.get(0)) {
                Max.add(rank);
            }
            Index++;
        }
        return true;

    }


    private boolean isStraight(List<Card> AllCard, boolean store) {

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

            if (store) {
                for (int i = MinNum + 4; i >= MinNum; i--) {
                    Max.add(i);
                }
            }
            return true;
        }

    }

    private boolean isFlush ()
    {

         Max.clear();
         Map<Card.Suit,List<Integer>> SuitMap=new HashMap<>();

         //将同花色的卡牌写入对应的集合
         for (Card card : AllCard) {
             Card.Suit suit=card.suit;
             int rank=card.rank.getValue();
             if(!SuitMap.containsKey(suit)) {
                 List<Integer> ranks=new ArrayList<>();
                 ranks.add(rank);
                 SuitMap.put(suit,ranks);
             }else {
                 SuitMap.get(suit).add(rank);
             }
         }


         //判断是否有花色的数量超过4张
         for (Map.Entry<Card.Suit, List<Integer>> entry : SuitMap.entrySet()) {
            Card.Suit suit=entry.getKey();
            List<Integer> ranks=entry.getValue();
            if(ranks.size()>=5) {
                ranks.sort((o1, o2) -> o2-o1);
                Max.addAll(ranks.subList(0,5));
                return true;
            }
         }

         return false;

    }

    private boolean isFullHouse ()
    {

            return false;
    }

    private boolean isFourOfKind ()
    {
            return false;
    }

    private boolean isStraightFlush ()
    {
            return false;
    }



}