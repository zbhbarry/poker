import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DQNAgent {
    private static final int STATE_SIZE = 4;  // 状态维度
    private static final int ACTION_SIZE = 2; // 可选动作数
    private static final double GAMMA = 0.99; // 折扣因子
    private static final double ALPHA = 0.01; // 学习率
    private static double EPSILON = 1.0; // ε贪心策略（初始探索概率）
    private static final double EPSILON_DECAY = 0.995; // ε衰减率
    private static final double EPSILON_MIN = 0.01; // 最小 ε

    private MultiLayerNetwork onlineModel;
    private MultiLayerNetwork targetModel;
    private List<Experience> replayBuffer = new ArrayList<>();
    private static final int BUFFER_SIZE = 10000;
    private static final int BATCH_SIZE = 32;
    private static final int TARGET_UPDATE_FREQUENCY = 10;

    private Random random = new Random();
    // 用于静态方法中的随机数生成
    private static final Random staticRandom = new Random();

    public DQNAgent() {
        // 初始化神经网络
        onlineModel = buildModel();
        targetModel = buildModel();
        targetModel.setParams(onlineModel.params()); // 初始同步参数
    }

    private MultiLayerNetwork buildModel() {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(123)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(ALPHA))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(STATE_SIZE).nOut(24)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new DenseLayer.Builder().nIn(24).nOut(24)
                        .activation(Activation.RELU)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .nIn(24).nOut(ACTION_SIZE)
                        .activation(Activation.IDENTITY)
                        .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(10));
        return model;
    }

    public int chooseAction(INDArray state) {
        if (random.nextDouble() < EPSILON) {
            return random.nextInt(ACTION_SIZE);
        }
        INDArray qValues = onlineModel.output(state);
        return Nd4j.argMax(qValues).getInt(0); // 选择 Q 值最大的动作
    }

    public void storeExperience(INDArray state, int action, double reward, INDArray nextState, boolean done) {
        if (replayBuffer.size() > BUFFER_SIZE) {
            replayBuffer.remove(0);
        }
        replayBuffer.add(new Experience(state, action, reward, nextState, done));
    }

    public void train() {
        if (replayBuffer.size() < BATCH_SIZE) {
            return;
        }

        List<Experience> batch = getRandomBatch();

        for (Experience exp : batch) {
            INDArray state = exp.state;
            int action = exp.action;
            double reward = exp.reward;
            INDArray nextState = exp.nextState;
            boolean done = exp.done;

            // 获取当前状态的 Q 值，并初始化所有动作的 Q 值为 -∞
            INDArray qValues = Nd4j.valueArrayOf(new int[]{1, ACTION_SIZE}, Double.NEGATIVE_INFINITY);
            INDArray currentQValues = onlineModel.output(state);
            qValues.assign(currentQValues);

            // 计算目标 Q 值
            double targetQ;
            if (done) {
                targetQ = reward; // 终止状态
            } else {
                INDArray nextQValues = targetModel.output(nextState);
                double maxNextQ = nextQValues.maxNumber().doubleValue();
                targetQ = reward + GAMMA * maxNextQ;
            }

            // 仅更新当前执行的动作 Q 值，未探索的动作仍然保持 -∞
            qValues.putScalar(action, targetQ);

            // 训练网络
            onlineModel.fit(state, qValues);
        }

        // ε贪心策略衰减
        if (EPSILON > EPSILON_MIN) {
            EPSILON *= EPSILON_DECAY;
        }
    }

    public void updateTargetModel() {
        targetModel.setParams(onlineModel.params());
    }

    private List<Experience> getRandomBatch() {
        List<Experience> batch = new ArrayList<>();
        for (int i = 0; i < BATCH_SIZE; i++) {
            batch.add(replayBuffer.get(random.nextInt(replayBuffer.size())));
        }
        return batch;
    }

    public static void main(String[] args) {
        DQNAgent agent = new DQNAgent();

        for (int episode = 1; episode <= 1000; episode++) {
            INDArray state = getInitialState();
            boolean done = false;
            int step = 0;

            while (!done && step < 200) {
                int action = agent.chooseAction(state);
                INDArray nextState = getNextState(state, action);
                double reward = getReward(state, action);
                done = isDone(state);

                agent.storeExperience(state, action, reward, nextState, done);
                agent.train();

                state = nextState;
                step++;
            }

            if (episode % TARGET_UPDATE_FREQUENCY == 0) {
                agent.updateTargetModel();
            }

            System.out.println("Episode " + episode + " completed.");
        }
    }

    // 生成初始状态
    private static INDArray getInitialState() {
        return Nd4j.rand(new int[]{1, STATE_SIZE});
    }

    // 模拟状态转移：当前状态加上随机扰动
    private static INDArray getNextState(INDArray state, int action) {
        return state.add(Nd4j.randn(state.shape()));
    }

    // 模拟奖励：返回一个随机奖励
    private static double getReward(INDArray state, int action) {
        return staticRandom.nextDouble();
    }

    // 模拟终止条件：随机决定是否终止
    private static boolean isDone(INDArray state) {
        return staticRandom.nextDouble() > 0.95;
    }

    // 内部经验类，用于存储经验数据
    static class Experience {
        INDArray state;
        int action;
        double reward;
        INDArray nextState;
        boolean done;

        public Experience(INDArray state, int action, double reward, INDArray nextState, boolean done) {
            this.state = state;
            this.action = action;
            this.reward = reward;
            this.nextState = nextState;
            this.done = done;
        }
    }
}
