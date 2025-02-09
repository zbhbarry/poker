package main;

import java.util.*;

public class Comparator {


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
        private Shape(int value) {
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

    private List<Card> Max;

    private Player player;

    public List<Card> getMax() {
        return Max;
    }

    public void setMax(List<Card> max) {
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

    public Comparator()
    {
        CommunityCard=new ArrayList<>();
        AllCard=new ArrayList<>();
        Max=new ArrayList<>(5);
    }

    //信息初始化
    public void SetInfo(Player player,List<Card> communityCard)
    {
        setPlayer(player);
        setCommunityCard(communityCard);
        AllCard.clear();
        AllCard.addAll(this.player.getHands());
        AllCard.addAll(CommunityCard);
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
        Set<Card> cards=new HashSet<>();
        for (Card card : AllCard) {
            if(!cards.add(card))
            {
                return false;
            }
        }
        AllCard.sort(new java.util.Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o2.rank.getValue()-o1.rank.getValue();
            }
        });

        //添加最大牌值
        for (int i = 0; i < Max.size(); i++) {
            Max.add(i,AllCard.get(i));
        }
        return true;
    }

    private boolean isOnePair()
    {
        return false;

    }

    private  boolean isTwoPair()
    {
        return false;
    }

    private boolean isThreeOfKind()
    {
        return false;
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
