package AiModel;

import main.Player;

public class Step {

    private double[] nextStates;
    private double[] states;
    private int action;
    private int reward;

    public double[] getStates() {
        return states;
    }

    public void setStates(double[] states) {
        this.states = states;
    }


    public double[] getNextStates() {
        return nextStates;
    }

    public void setNextStates(double[] nextStates) {
        this.nextStates = nextStates;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public Step(double[] states, Player.Action action,double[] nextStates) {
        this.states = states;
        this.action = action.getValue();
        this.reward = 0;
        this.nextStates=nextStates;
    }
}
