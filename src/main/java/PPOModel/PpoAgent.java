package PPOModel;

import AiTraining.AiAgent;

import java.util.List;

public class PpoAgent extends AiAgent {

    PPO ppo;

    public PPO getPpo() {
        return ppo;
    }

    public void setPpo(PPO ppo) {
        this.ppo = ppo;
    }

    public PpoAgent(String name, int chips,PPO ppo,boolean isTrain) {
        super(name, chips, isTrain);
        this.ppo=ppo;
    }

    @Override
    public Action SelectAction(double[] state, List<Action> validActions) {
        return super.SelectAction(state, validActions);
    }
}
