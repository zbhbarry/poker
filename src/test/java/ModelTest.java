import AiModel.DQN;
import AiModel.DqnPlayer;
import AiModel.Experience;
import main.Desk;
import main.HumanPlayer;
import main.Player;
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


            List<Player> players=new ArrayList<>();

            //参数设置
            int totalCount = 20000;//总训练局数
            final int CHIPS = 300;//初始筹码数量


            // 创建 DQN 对象时传入超参数
            DQN dqn = new DQN();
            dqn.loadModel("dqn_model.zip");

            Player player1=new DqnPlayer("AI_1",CHIPS,dqn);
            Player player2=new HumanPlayer("AI_2",CHIPS);
            Player player3=new HumanPlayer("AI_3",CHIPS);
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

                i++;

            }

            System.out.println("玩家1的胜率是:"+(double) player1.getWinCount() /totalCount+",玩家1的弃牌率是:"+(double) player1.getFoldCount() /totalCount);
            System.out.println("玩家2的胜率是:"+(double) player2.getWinCount() /totalCount+",玩家2的弃牌率是:"+(double) player2.getFoldCount() /totalCount);
            System.out.println("玩家3的胜率是:"+(double) player3.getWinCount() /totalCount+",玩家3的弃牌率是:"+(double) player3.getFoldCount() /totalCount);

    }


}
