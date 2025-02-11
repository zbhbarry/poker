package main;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements SelectAction{


    private int chips;//剩余筹码数量

    private int CurrentBet;//当前下注量

    private int  isFold;//是否弃牌

    private int Position;//位置

    private String name;//名字

    private List<Card> Hands;//手牌

    private List<Integer> MaxShapeCards;//最大手牌集合

    private ShapeJude.Shape shape;//手牌强度




    public List<Integer> getMaxShapeCards() {
        return MaxShapeCards;
    }

    public void setMaxShapeCards(List<Integer> maxShapeCards) {
        MaxShapeCards = maxShapeCards;
    }

    public ShapeJude.Shape getShape() {
        return shape;
    }

    public void setShape(ShapeJude.Shape shape) {
        this.shape = shape;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public int getCurrentBet() {
        return CurrentBet;
    }

    public void setCurrentBet(int currentBet) {
        CurrentBet = currentBet;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getHands() {
        return Hands;
    }

    public void setHands(List<Card> hands) {
        Hands = hands;
    }

    public int getIsFold() {
        return isFold;
    }

    public void setIsFold(int isFold) {
        this.isFold = isFold;
    }

    public Player(String name) {
       this.name=name;
       this.chips=0;
       this.isFold=0;
       this.Hands=new ArrayList<>(2);
       this.MaxShapeCards=new ArrayList<>();
    }

    public void InitPlayer()
    {
        getHands().clear();
        setShape(null);
        getMaxShapeCards().clear();
        setIsFold(0);
    }


    @Override
    public String toString() {
        return name+"的手牌是"+getHands().get(0).toString()+","
        +getHands().get(1).toString()
        + ","+"有"+chips+"筹码,";
    }

}
