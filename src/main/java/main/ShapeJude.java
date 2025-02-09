package main;

import java.util.*;

public class ShapeJude {


    public enum Shape
    {
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

    private List<Integer> Max;

    private Player player;

    Map<Integer, Integer> map;

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

    public ShapeJude()
    {
        CommunityCard=new ArrayList<>();
        AllCard=new ArrayList<>();
        Max=new ArrayList<>();
        map=new HashMap<>();
    }

    //信息初始化
    public void SetInfo(Player player,List<Card> communityCard)
    {
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

    public void CardJude()
    {


        if(isStraightFlush()) {
            player.setShape(Shape.STRAIGHT_FLUSH);
        }else if (isFourOfKind()) {
            player.setShape(Shape.FOUR_OF_A_KIND);
        } else if (isFullHouse()) {
            player.setShape(Shape.FULL_HOUSE);
        } else if (isFlush()) {
            player.setShape(Shape.FLUSH);
        } else if (isStraight()) {
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



    private boolean isHighCard()
    {

        Max.clear();
        Set<Integer> cards=new HashSet<>();
        for (Card card : AllCard) {
            int rank=card.rank.getValue();
            if(!cards.add(rank))
            {
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
        int PairNum=0;
        int Index=0;

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

        if (PairCount > 1 || PairCount==0)
        {
            return false;
        }

        Max.add(PairNum);
        //添加最大牌值
        while (Max.size()<4)
        {
            int rank=AllCard.get(Index).rank.getValue();
            if(rank!=PairNum)
            {
                Max.add(rank);
            }
            Index++;
        }

        return true;
    }



    private  boolean isTwoPair()
    {

        Max.clear();

        List<Integer> PairNum=new ArrayList<>();

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
        if (PairNum.size() < 2)
        {
            return false;
        }else if(PairNum.size()>2){
            PairNum.remove(PairNum.size()-1);
        }

        Max.addAll(PairNum);

        for (Card card : AllCard) {
            int rank=card.rank.getValue();
            if(!PairNum.contains(rank))
            {
                Max.add(rank);
                return true;
            }
        }
        return true;
    }



    private boolean isThreeOfKind()
    {
        Max.clear();
        List<Integer> ThreeKind=new ArrayList<>();
        int Index=0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (value > 3 || value==2) {
                return false;
            } else if (value == 3) {
                ThreeKind.add(key);
            }
        }

        if(ThreeKind.size()==1) {
            Max.add(ThreeKind.get(0));
        }else {
            return false;
        }

        while (Max.size()<3)
        {
            int rank=AllCard.get(Index).rank.getValue();
            if(rank!=ThreeKind.get(0))
            {
                Max.add(rank);
            }
            Index++;
        }
        return true;

    }


    private boolean isStraight()
    {
        return false;
    }

    private boolean isFlush()
    {
        return false;
    }

    private boolean isFullHouse()
    {

        return false;
    }

    private boolean isFourOfKind()
    {
        return false;
    }

    private boolean isStraightFlush()
    {
        return false;
    }


}
