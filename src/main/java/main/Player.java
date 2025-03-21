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
    private long chips;//剩余筹码数量

    private long OriginChips;//初始筹码数量

    private long PredictiveChips;//预期可以赢得的筹码数量

    private long CurrentBet;//当前下注量

    private long TotalBet;//这个回合的总投入量

    private long SpaceBet;//剩余投入量

    private int isFold;//是否弃牌

    private int isAllIn;//是否全压

    private int isWin;//是否赢了

    private int Position;//位置

    private String name;//名字

    private List<Card> Hands;//手牌

    private List<Integer> MaxShapeCards;//最大手牌集合

    private ShapeJude.Shape shape;//手牌强度

    private int RaiseNum;//加注次数

    private int rank;//玩家排名


    //java bean
    public List<Integer> getMaxShapeCards() {
        return new ArrayList<>(MaxShapeCards); //  返回副本，防止外部修改
    }

    public void setMaxShapeCards(List<Integer> maxShapeCards) {
        this.MaxShapeCards = new ArrayList<>(maxShapeCards); //  存储一个新的 List，避免数据共享
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ShapeJude.Shape getShape() {
        return shape;
    }

    public void setShape(ShapeJude.Shape shape) {
        this.shape = shape;
    }

    public long getChips() {
        return chips;
    }

    public void setChips(long chips) {
        this.chips = chips;
    }

    public long getOriginChips() {
        return OriginChips;
    }

    public void setOriginChips(long originChips) {
        OriginChips = originChips;
    }

    public long getSpaceBet() {
        return SpaceBet;
    }

    public void setSpaceBet(long spaceBet) {
        SpaceBet = spaceBet;
    }

    public long getPredictiveChips() {
        return PredictiveChips;
    }

    public void setPredictiveChips(int predictiveChips) {
        PredictiveChips = predictiveChips;
    }

    public long getCurrentBet() {
        return CurrentBet;
    }

    public void setCurrentBet(long currentBet) {
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

    public long getTotalBet() {
        return TotalBet;
    }

    public void setTotalBet(long totalBet) {
        TotalBet = totalBet;
    }

    public int getRaiseNum() {
        return RaiseNum;
    }

    public void setRaiseNum(int raiseNum) {
        RaiseNum = raiseNum;
    }

    public Player(String name, int chips) {
       this.name=name;
       this.isFold=0;
       this.isAllIn=0;
       this.CurrentBet=0;
       this.chips=chips;
       this.isWin=-1;
       this.PredictiveChips=0;
       this.OriginChips=0;
       this.Hands=new ArrayList<>(2);
       this.MaxShapeCards=new ArrayList<>();
       this.TotalBet=0;
       this.SpaceBet=0;
       this.RaiseNum=0;
       this.rank=-1;
    }

    public void InitPlayer()
    {
        getHands().clear();
        setShape(null);
        getMaxShapeCards().clear();
        setIsFold(0);
        setIsAllIn(0);
        setCurrentBet(0);
        setIsWin(-1);
        setTotalBet(0);
        setSpaceBet(0);
        setOriginChips(chips);
        setPredictiveChips(0);
        setRaiseNum(0);
        setRank(-1);
    }

    public abstract Action SelectAction(double[] state,List<Action> validActions);

    @Override
    public String toString() {
        return name+"的手牌是"+getHands().get(0).toString()+","
        +getHands().get(1).toString()
        + ","+"有"+chips+"筹码,";
    }

}
