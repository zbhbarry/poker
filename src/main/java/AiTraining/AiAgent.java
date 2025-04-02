package AiTraining;

import GameEnv.Player;

import java.util.ArrayList;
import java.util.List;

public class AiAgent extends Player {

    private List<Experience> experiences;

    boolean isTrain;

    public boolean isTrain() {
        return isTrain;
    }

    public void setTrain(boolean train) {
        isTrain = train;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public AiAgent(String name, int chips, boolean isTrain) {
        super(name, chips);
        this.isTrain=isTrain;
        this.experiences=new ArrayList<>();
    }

    @Override
    public Action SelectAction(double[] state, List<Action> validActions) {
        return null;
    }
}
