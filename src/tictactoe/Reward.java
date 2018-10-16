package tictactoe;

import java.util.HashMap;

public class Reward {
    HashMap<Integer, Integer> rewards = new HashMap<>();
    public Reward(int reward1, int reward2){
        rewards.put(0, reward1);
        rewards.put(1, reward2);
    }

    public HashMap<Integer, Integer> getReward(){
        return rewards;
    }

    public int getRewardForPlayer(int playerNumber){
        return rewards.get(playerNumber);
    }

    public void addReward(Reward reward) {
        rewards.put(0, rewards.get(0) + reward.getRewardForPlayer(0));
        rewards.put(1, rewards.get(1) + reward.getRewardForPlayer(1));
    }
}
