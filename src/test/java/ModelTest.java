import AiModel.DQN;
import AiModel.Experience;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

public class ModelTest {


    public static void main(String[] args) throws IOException {


        DQN dqn=new DQN(19,13);
        //Experience exp=new Experience()
        //INDArray state = Nd4j.create(exp.getStates());
        //INDArray testQValues = dqn.getqNetwork().output(stateBatch);
        //System.out.println("Test Q values: " + testQValues);

        dqn.loadModel("dqn_model.zip");
        System.out.println("Final Loss: " + dqn.getqNetwork().score());

    }


}
