package PPOModel;

import AiTraining.AiAgent;

import java.util.List;

public class PpoAgent extends AiAgent {
    public PpoAgent(String name, int chips, boolean isTrain) {
        super(name, chips, isTrain);
    }

    @Override
    public Action SelectAction(double[] state, List<Action> validActions) {
        return super.SelectAction(state, validActions);
    }
}
