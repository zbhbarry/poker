import AiModel.DQN;
import AiModel.Experience;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModelTest {


    public static void main(String[] args) throws IOException {
        double gamma = 0.99;                // 折扣因子
        double learningRate = 0.01;        // 学习率
        int inputSize=19;                  // 状态空间维度
        int outputSize=13;                 // 动作空间大小
        int targetUpdateFreq = 100;        // 目标网络同步间隔
        int batchSize = 64;                 // Mini-Batch 大小
        // private int replayMemorySize = 10000;       // 经验回放池大小
        List<Experience> replayBuffer = new ArrayList<>();
        Random random = new Random();
        int stepCounter = 0;// 训练步数
        double epsilonMax = 1.0;         // 初始探索率
        double epsilonDecay = 0.001;   // 衰减率
        double epsilonMin = 0.01;      // 最小探索率
        double epsilon = 0;


        // 创建 DQN 对象时传入超参数
        DQN dqn = new DQN(inputSize, outputSize, gamma, learningRate, targetUpdateFreq, batchSize, epsilonMax, epsilonDecay, epsilonMin);

        //Experience exp=new Experience()
        //INDArray state = Nd4j.create(exp.getStates());
        //INDArray testQValues = dqn.getqNetwork().output(stateBatch);
        //System.out.println("Test Q values: " + testQValues);

        dqn.loadModel("dqn_model.zip");
        System.out.println("Final Loss: " + dqn.getqNetwork().score());

    }


}
