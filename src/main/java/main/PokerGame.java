package main;


import AiModel.AiPlayer;
import AiModel.DQN;
import AiModel.Experience;

import java.util.ArrayList;
import java.util.List;

public class PokerGame {



    public static void main(String[] args) {


        final int CHIPS=300;

        List<Player> players=new ArrayList<>();

        int inputSize=19;
        int outputSize=13;
        int trainCount=500;

        DQN dqn=new DQN(inputSize,outputSize);


        Player player1=new AiPlayer("AI_1",CHIPS,dqn);
        Player player2=new AiPlayer("AI_2",CHIPS,dqn);
        Player player3=new AiPlayer("AI_3",CHIPS,dqn);
        //Player player4=new AiPlayer("AI_4",CHIPS,dqn);
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

        while (i<10) {

            System.out.println("---------------第" + i + "局----------------");
            desk.round();

            //每搁500局训练一次，并且经验池的经验要大于batch的size
            if(i % trainCount == 0){
                for (Player player : players) {
                    AiPlayer aiPlayer=(AiPlayer) player;
                    for (Experience experience : aiPlayer.getExperiences()) {
                        dqn.getReplayBuffer().add(experience);
                    }
                    aiPlayer.getExperiences().clear();
                }
                if(dqn.getReplayBuffer().size() > dqn.getBatchSize()){
                    dqn.train();
                    dqn.getReplayBuffer().clear();
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





    }
}