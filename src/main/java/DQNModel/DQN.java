package DQNModel;
import AiTraining.Experience;
import GameEnv.Player;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.deeplearning4j.util.ModelSerializer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DQN {
    private MultiLayerNetwork qNetwork;         // 主 Q 网络
    private MultiLayerNetwork targetNetwork;    // 目标 Q 网络
    private double gamma;                // 折扣因子
    private double learningRate;        // 学习率
    private int inputSize;                  // 状态空间维度
    private int outputSize;                 // 动作空间大小
    private int targetUpdateFreq;        // 目标网络同步间隔
    private int batchSize;                 // Mini-Batch 大小
    // private int replayMemorySize = 10000;       // 经验回放池大小
    private List<Experience> replayBuffer = new ArrayList<>();
    private Random random = new Random();
    private int stepCounter = 0;// 训练步数
    double epsilonMax;         // 初始探索率
    double epsilonDecay;   // 衰减率
    public double epsilonMin;      // 最小探索率
    double epsilon;

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public double getEpsilonMax() {
        return epsilonMax;
    }

    public void setEpsilonMax(double epsilon) {
        this.epsilonMax = epsilon;
    }

    public MultiLayerNetwork getqNetwork() {
        return qNetwork;
    }

    public void setqNetwork(MultiLayerNetwork qNetwork) {
        this.qNetwork = qNetwork;
    }

    public MultiLayerNetwork getTargetNetwork() {
        return targetNetwork;
    }

    public void setTargetNetwork(MultiLayerNetwork targetNetwork) {
        this.targetNetwork = targetNetwork;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(int outputSize) {
        this.outputSize = outputSize;
    }

    public int getTargetUpdateFreq() {
        return targetUpdateFreq;
    }

    public void setTargetUpdateFreq(int targetUpdateFreq) {
        this.targetUpdateFreq = targetUpdateFreq;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public List<Experience> getReplayBuffer() {
        return replayBuffer;
    }

    public void setReplayBuffer(List<Experience> replayBuffer) {
        this.replayBuffer = replayBuffer;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int getStepCounter() {
        return stepCounter;
    }

    public void setStepCounter(int stepCounter) {
        this.stepCounter = stepCounter;
    }

    public DQN(){

    }

    public DQN(int inputSize, int outputSize, double gamma, double learningRate, int targetUpdateFreq,
               int batchSize, double epsilonMax, double epsilonDecay, double epsilonMin) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.gamma = gamma;
        this.learningRate = learningRate;
        this.targetUpdateFreq = targetUpdateFreq;
        this.batchSize = batchSize;
        this.epsilonMax = epsilonMax;
        this.epsilonDecay = epsilonDecay;
        this.epsilonMin = epsilonMin;
        this.epsilon = epsilonMax; // 初始探索率

        // 初始化 Q 网络和目标网络
        qNetwork = buildNetwork(inputSize, outputSize);
        targetNetwork = buildNetwork(inputSize, outputSize);
        targetNetwork.setParams(qNetwork.params());  // 目标网络参数初始化
    }

    // 构建网络的方法
    private MultiLayerNetwork buildNetwork(int stateSize, int actionSize) {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(123)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(learningRate))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(stateSize).nOut(64)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new DenseLayer.Builder().nIn(64).nOut(64)
                        .activation(Activation.RELU)
                        .build())
                .layer(2, new OutputLayer.Builder(org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction.MSE)
                        .nIn(64).nOut(actionSize)
                        .activation(Activation.IDENTITY)
                        .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        return model;
    }

    public void saveModel(String filePath) {
        try {
            ModelSerializer.writeModel(qNetwork, new File(filePath), true);
            System.out.println("Model saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadModel(String filePath) {
        try {
            qNetwork = ModelSerializer.restoreMultiLayerNetwork(new File(filePath));
            targetNetwork = new MultiLayerNetwork(qNetwork.getLayerWiseConfigurations());
            targetNetwork.init();
            // 同步 Q 网络到目标网络
            targetNetwork.setParams(qNetwork.params());
            System.out.println("Model loaded from: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 训练 DQN
    public void train() {
        if (replayBuffer.size() < batchSize) return; // 经验池数据不足时不训练
        stepCounter++;

        // 随机抽取 batchSize 个样本
        Collections.shuffle(replayBuffer);
        List<Experience> batch = replayBuffer.subList(0, batchSize);

        // 处理 Mini-Batch 数据
        INDArray stateBatch = Nd4j.zeros(batchSize, inputSize);
        INDArray nextStateBatch = Nd4j.zeros(batchSize, inputSize);
        int[] actions = new int[batchSize];
        double[] rewards = new double[batchSize];

        for (int i = 0; i < batchSize; i++) {
            Experience exp = batch.get(i);
            INDArray state = Nd4j.create(exp.getStates());
            stateBatch.putRow(i, state);
            INDArray nextState = Nd4j.create(exp.getNextStates());
            nextStateBatch.putRow(i, nextState);

            actions[i] = exp.getAction();
            rewards[i] = exp.getReward();
        }

        // 计算目标 Q 值（Bellman 方程）
        INDArray targetQValues = targetNetwork.output(nextStateBatch);
        INDArray qValues = qNetwork.output(stateBatch);

        //System.out.println("Predicted Q values before training: " + qValues);

        for (int i = 0; i < batchSize; i++) {
            double targetValue;
            if (batch.get(i).isDone()) {
                targetValue = rewards[i]; // 终止状态，Q(s,a) = reward
            } else {
                double maxQ = targetQValues.getRow(i).maxNumber().doubleValue();
                targetValue = rewards[i] + gamma * maxQ;
            }
            INDArray qRow = qValues.getRow(i);
            // 遍历 Q 值，将不合法的动作设为 -10
            for (int j = 0; j < qRow.columns(); j++) {
                Player.Action action = Player.Action.fromValue(j); // 获取枚举动作
                if (!batch.get(i).getValidActions().contains(action)) {
                    qRow.putScalar(j, -10); // 设为 -10，避免选择
                }
            }
            qValues.putScalar(i, actions[i], targetValue); // 更新 Q 值

           // System.out.println("State batch: " + stateBatch);
           // System.out.println("Target Q Values: " + targetQValues);
           // System.out.println("Q values: " + qValues);
        }
        //System.out.println("Predicted Q values after training: " + qValues);
        System.out.println("Final Loss: " + this.getqNetwork().score());

        // 训练 Q 网络
        qNetwork.fit(stateBatch, qValues);

        // 训练过程中衰减 epsilon
       // epsilon = Math.max(epsilon * epsilonDecay, epsilonMin);

        // 每 targetUpdateFreq 步同步目标网络
        if (stepCounter % targetUpdateFreq == 0) {
            targetNetwork.setParams(qNetwork.params());
            System.out.println("Target network updated.");
        }
    }

}

