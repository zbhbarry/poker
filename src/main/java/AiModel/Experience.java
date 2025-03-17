package AiModel;

import main.Player;

import java.util.Set;

public class Experience {

    private double[] nextStates;
    private double[] states;
    private int action;
    private double reward;
    private Set<Player.Action> validActions;

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

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public Set<Player.Action> getValidActions() {
        return validActions;
    }

    public void setValidActions(Set<Player.Action> validActions) {
        this.validActions = validActions;
    }

    public Experience(double[] states, Player.Action action, double[] nextStates, Set<Player.Action> validActions) {
        this.states = states;
        this.action = action.getValue();
        this.reward = 1.0;
        this.nextStates=nextStates;
        this.validActions=validActions;
    }
}
