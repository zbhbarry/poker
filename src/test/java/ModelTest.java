import DQNModel.DQN;
import DQNModel.DqnAgent;
import DQNModel.State;
import main.Desk;
import main.HumanPlayer;
import main.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelTest {


    public static void main(String[] args) throws IOException {


        List<Player> players = new ArrayList<>();

        //参数设置
        int totalCount = 100000;//总训练局数
        final int CHIPS = 300;//初始筹码数量


        // 创建 DQN 对象时传入超参数
        DQN dqn = new DQN();
        dqn.loadModel("dqn_model.zip");
        DQN dqn1 = new DQN();
        dqn1.loadModel("dqn_model1.zip");
        DQN dqn2 = new DQN();
        dqn2.loadModel("dqn_model2.zip");

        Player player1 = new DqnAgent("AI_1", CHIPS, dqn, false);
       // Player player2 = new DqnPlayer("AI_2", CHIPS,dqn1,false);
        //Player player3 = new DqnPlayer("AI_3", CHIPS,dqn2,false);
        Player player4=new HumanPlayer("AI_4",CHIPS);
        Player player5=new HumanPlayer("AI_5",CHIPS);
        //Player player6=new AiPlayer("AI_6",CHIPS);

        //  players.add(player6);
        players.add(player1);
       // players.add(player2);
       // players.add(player3);
        players.add(player4);
         players.add(player5);


        Desk desk = new Desk(players);

        int i = 1;

        while (i < totalCount) {

            System.out.println("---------------第" + i + "局----------------");
            desk.round();

            i++;

        }

        System.out.println("玩家1的胜率是:" + (double) player1.getWinCount() / totalCount + ",玩家1的弃牌率是:" + State.roundToThreeDecimalPlaces((double) player1.getFoldCount() / totalCount));
        System.out.println("玩家2的胜率是:" + (double) player4.getWinCount() / totalCount + ",玩家2的弃牌率是:" + (double) player4.getFoldCount() / totalCount);
        System.out.println("玩家3的胜率是:" + (double) player5.getWinCount() / totalCount + ",玩家3的弃牌率是:" + (double) player5.getFoldCount() / totalCount);

    }


}
