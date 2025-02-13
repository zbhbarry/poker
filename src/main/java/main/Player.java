package main;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {


    public enum Action
    {

        FOLD(0),
        CHECK(1),
        CALL(2),
        BET(3),
        BET_10(4),
        BET_25(5),
        BET_50(6),
        RAISE_2(7),
        RAISE_3(8),
        POT(9),
        POT_2(10),
        POT_HALF(11),
        ALLIN(12);

        private final int value;

        Action(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Action fromValue(int value) {
            for (Action action : Action.values()) {
                if (action.getValue() == value) {
                    return action;
                }
            }
            throw new IllegalArgumentException("No Action found for value: " + value);
        }
    }

    //变量
    private int chips;//剩余筹码数量

    private int OriginChips;//初始筹码数量

    private int PredictiveChips;//预期可以赢得的筹码数量

    private int CurrentBet;//当前下注量

    private int TotalBet;//这个回合的总投入量

    private int isFold;//是否弃牌

    private int isAllIn;//是否全压

    private int isWin;//是否赢了

    private int Position;//位置

    private String name;//名字

    private List<Card> Hands;//手牌

    private List<Integer> MaxShapeCards;//最大手牌集合

    private ShapeJude.Shape shape;//手牌强度


    //java bean
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

    public int getOriginChips() {
        return OriginChips;
    }

    public void setOriginChips(int originChips) {
        OriginChips = originChips;
    }

    public int getPredictiveChips() {
        return PredictiveChips;
    }

    public void setPredictiveChips(int predictiveChips) {
        PredictiveChips = predictiveChips;
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

    public int getIsAllIn() {
        return isAllIn;
    }

    public void setIsAllIn(int isAllIn) {
        this.isAllIn = isAllIn;
    }

    public int getIsWin() {
        return isWin;
    }

    public void setIsWin(int isWin) {
        this.isWin = isWin;
    }

    public int getTotalBet() {
        return TotalBet;
    }

    public void setTotalBet(int totalBet) {
        TotalBet = totalBet;
    }

    public Player(String name, int chips) {
       this.name=name;
       this.isFold=0;
       this.isAllIn=0;
       this.CurrentBet=0;
       this.chips=chips;
       this.isWin=0;
       this.PredictiveChips=0;
       this.OriginChips=0;
       this.Hands=new ArrayList<>(2);
       this.MaxShapeCards=new ArrayList<>();
       this.TotalBet=0;
    }

    public void InitPlayer()
    {
        getHands().clear();
        setShape(null);
        getMaxShapeCards().clear();
        setIsFold(0);
        setIsAllIn(0);
        setCurrentBet(0);
        setIsWin(0);
        setTotalBet(0);
        if(chips<=0) {
            setChips(3000);
        }
        setOriginChips(chips);
        setPredictiveChips(0);
    }

    public abstract Action SelectAction(double[] state,List<Action> validActions);

    @Override
    public String toString() {
        return name+"的手牌是"+getHands().get(0).toString()+","
        +getHands().get(1).toString()
        + ","+"有"+chips+"筹码,";
    }

}
