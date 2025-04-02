package GameEnv;

import java.util.List;
import java.util.Random;

public class HumanPlayer extends Player{
    public HumanPlayer(String name,int chips) {super(name,chips);}

    @Override
    public Action SelectAction(double[] state,List<Action> validActions) {

        // 创建一个随机数生成器
        Random random = new Random();

        // 生成一个随机的索引，范围是 0 到 validActions.size() - 1
        int randomIndex = random.nextInt(validActions.size());

        // 返回所选动作
        return  validActions.get(randomIndex);
    }
}
