package AiTraining;


import DQNModel.DqnAgent;
import DQNModel.DQN;
import GameEnv.Desk;
import GameEnv.Player;

import java.util.ArrayList;
import java.util.List;

public class TrainMain {



    public static void main(String[] args) {




        List<Player> players=new ArrayList<>();

        //参数设置
        int totalCount = 500000;//总训练局数
        int trainCount = 50;//训练步数
        int flush = 500;//刷新经验池步数
        final int CHIPS = 300;//初始筹码数量
        int inputSize = 19;//状态空间维度
        int outputSize = 13;//动作空间维度
        double gamma = 0.99;                // 折扣因子
        double learningRate = 0.001;        // 学习率
        int targetUpdateFreq = 100;        // 目标网络同步间隔
        int batchSize = 64;                 // Mini-Batch 大小
        double epsilonMax = 0.8;         // 初始探索率
        double epsilonDecay = 0.001;   // 衰减率
        double epsilonMin = 0.01;      // 最小探索率


        // 创建 DQN 对象时传入超参数
        DQN dqn = new DQN(inputSize, outputSize, gamma, learningRate, targetUpdateFreq, batchSize, epsilonMax, epsilonDecay, epsilonMin);
        DQN dqn1= new DQN(inputSize, outputSize, gamma, learningRate, targetUpdateFreq, batchSize, epsilonMax, epsilonDecay, epsilonMin);
        DQN dqn2= new DQN(inputSize, outputSize, gamma, learningRate, targetUpdateFreq, batchSize, epsilonMax, epsilonDecay, epsilonMin);
        dqn1.loadModel("dqn_model1.zip");
        dqn2.loadModel("dqn_model2.zip");



        Player player1=new DqnAgent("AI_1",CHIPS,dqn,true);
        Player player2=new DqnAgent("AI_2",CHIPS,dqn1,false);
        Player player3=new DqnAgent("AI_3",CHIPS,dqn2,false);
       // Player player4=new DqnPlayer("AI_4",CHIPS,dqn);
       // Player player5=new AiPlayer("AI_5",CHIPS,dqn);
      //  Player player6=new AiPlayer("AI_6",CHIPS);



      //  players.add(player6);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        //players.add(player4);
       // players.add(player5);


        Desk desk=new Desk(players);

        int i=1;

        while (i<totalCount) {

            System.out.println("---------------第" + i + "局----------------");
            desk.round();

            //每50局训练一次，并且经验池的经验要大于batch的size

                for (Player player : players) {

                    if(player instanceof DqnAgent dqnAgent) {
                        DQN d= dqnAgent.getDqn();
                        for (Experience experience : dqnAgent.getExperiences()) {
                            d.getReplayBuffer().add(experience);
                        }
                        dqnAgent.getExperiences().clear();

                        if(i % trainCount == 0 && dqnAgent.isTrain()){
                            d.train();
                            System.out.println(dqnAgent.getName()+"的Final Loss: " + d.getqNetwork().score());
                        }
                        if (i % flush == 0) {
                            int bufferSize = d.getReplayBuffer().size();
                            if (bufferSize > 0) {  // 避免 buffer 为空时报错
                                int removeSize = (int) (0.2 * bufferSize);
                                d.getReplayBuffer().subList(0, removeSize).clear(); // 只删除最旧的 20%
                            }
                        }
                        // 在每局游戏开始时更新 epsilon
                       // d.setEpsilon(epsilonMax - ((double) i / totalCount) * (epsilonMax - epsilonMin));
                       // d.setEpsilon(Math.max(dqn.getEpsilon(), epsilonMin)); // 确保 epsilon 不小于 epsilonMin
                        System.out.println(dqnAgent.getName()+" 的探索率: "+d.getEpsilon());
                    }

                }




            i++;

            /*
            for (Player player : desk.getPlayers()) {
                AiPlayer aiPlayer=(AiPlayer) player;
                for (Experience experience : aiPlayer.getExperiences()) {
                    for (double state : experience.getStates()) {
                        System.out.print(state);
                        System.out.print("      ");
                    }
                    System.out.println(" ");
                }
                System.out.println(" ");
            }

             */


        }

        String filePath = "dqn_model.zip";
        dqn.saveModel(filePath);

        //String filePath1 ="dqn_model1.zip";
        //dqn1.saveModel(filePath1);

        //String filePath2 ="dqn_model2.zip";
        //dqn2.saveModel(filePath2);


        System.out.println("玩家1的胜率是:"+(double) player1.getWinCount() /totalCount+",玩家1的弃牌率是:"+(double) player1.getFoldCount() /totalCount);
        System.out.println("玩家2的胜率是:"+(double) player2.getWinCount() /totalCount+",玩家2的弃牌率是:"+(double) player2.getFoldCount() /totalCount);
        System.out.println("玩家3的胜率是:"+(double) player3.getWinCount() /totalCount+",玩家3的弃牌率是:"+(double) player3.getFoldCount() /totalCount);


    }
}