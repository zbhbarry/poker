package DQNModel;

import main.Player;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DqnPlayer extends Player {

    private List<Experience> experiences;

    private DQN dqn;

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

    public void setSteps(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public DQN getDqn() {
        return dqn;
    }

    public void setDqn(DQN dqn) {
        this.dqn = dqn;
    }

    public DqnPlayer(String name, int chips, DQN dqn,boolean isTrain) {
        super(name,chips);
        this.experiences =new ArrayList<>();
        this.dqn=dqn;
        this.isTrain=isTrain;
    }

    @Override
    public Action SelectAction(double[] state, List<Action> validActions) {

        if (validActions.isEmpty()) {
            System.out.println("Error: No valid actions available!");
            return null; // 或者 return 一个默认动作
        }


        Random random = new Random();

        if (random.nextDouble() < dqn.epsilon) {
            // 随机选择一个合法动作（探索）
            return validActions.get(random.nextInt(validActions.size()));
        } else {
            // 计算 Q 值
            INDArray State = Nd4j.create(state).reshape(1, state.length);
            INDArray qValues = dqn.getqNetwork().output(State);

            // 在 validActions 里找到 Q 值最大的动作
            Action bestAction = null;
            double maxQ = Double.NEGATIVE_INFINITY;

            for (Action action : validActions) {
                int actionIndex = action.getValue(); // 获取枚举对应的索引
                double qValue = qValues.getDouble(actionIndex);

                if (qValue > maxQ) {
                    maxQ = qValue;
                    bestAction = action;
                }
            }
            return bestAction;
        }
    }


}
