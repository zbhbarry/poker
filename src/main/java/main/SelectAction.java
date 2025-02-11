package main;

public interface SelectAction {


    public enum Action
    {

        CALL(0),
        RAISE(1), // Double the maximum bet
        ALLIN(2),
        FOLD(3),
        CHECK(4),
        RAISE_3X(5), // Triple the maximum bet
        RAISE_FullPot(6), // Full pot raise
        RAISE_DoublePot(7); // Double pot raise

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


    public abstract void SelectAction();


}
