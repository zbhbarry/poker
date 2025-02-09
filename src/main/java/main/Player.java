package main;

import java.util.ArrayList;
import java.util.List;

public class Player {



    private int chips;

    private int CurrentBet;

    private int Position;

    private String name;

    private List<Card> Hands;

    private List<Card> MaxShapeCards;

    private Comparator.Shape shape;

    public List<Card> getMaxShapeCards() {
        return MaxShapeCards;
    }

    public void setMaxShapeCards(List<Card> maxShapeCards) {
        MaxShapeCards = maxShapeCards;
    }

    public Comparator.Shape getShape() {
        return shape;
    }

    public void setShape(Comparator.Shape shape) {
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

    public Player(String name) {
       this.name=name;
       this.chips=0;
       this.Hands=new ArrayList<>();
    }

    @Override
    public String toString() {
        return name+"的手牌是"+getHands().get(0).toString()+","
        +getHands().get(1).toString()
        + ","+"有"+chips+"筹码,";
    }
}
