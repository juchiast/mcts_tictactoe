package tictactoe;

import java.util.LinkedList;
import java.util.Random;

public class MctsNode {

    private final MctsNode parent;
    private int numSimulations = 0;
    private Reward reward;
    private final LinkedList<MctsNode> children = new LinkedList<>();
    private final LinkedList<TicTacToeMove> unexploredMoves;
    private final int player;
    private final TicTacToeMove moveUsedToGetToNode;

    public MctsNode(MctsNode parent, TicTacToeMove move, TicTacToeGame game) {
        this.parent = parent;
        moveUsedToGetToNode = move;
        unexploredMoves = game.getAvailableMoves();
        reward = new Reward(0, 0);
        player = game.getPlayerToMove();
    }

    public MctsNode select() {
        MctsNode selectedNode = this;
        double max = Integer.MIN_VALUE;

        for (MctsNode child : getChildren()) {
            double uctValue = getUctValue(child);

            if (uctValue > max) {
                max = uctValue;
                selectedNode = child;
            }
        }

        return selectedNode;
    }

    private double getUctValue(MctsNode child) {
        double uctValue;

        if (child.getNumberOfSimulations() == 0) {
            uctValue = 1;
        } else {
            uctValue
                = (1.0 * child.getRewardForPlayer(getPlayer())) / (child.getNumberOfSimulations() * 1.0)
                + (Math.sqrt(2 * (Math.log(getNumberOfSimulations() * 1.0) / child.getNumberOfSimulations())));
        }

        Random r = new Random();
        uctValue += (r.nextDouble() / 10000000);
        return uctValue;
    }

    public MctsNode expand(TicTacToeGame game) {
        if (!canExpand()) {
            return this;
        }
        Random random = new Random();
        int moveIndex = random.nextInt(unexploredMoves.size());

        TicTacToeMove move = unexploredMoves.remove(moveIndex);
        game.makeMove(move);
        MctsNode child = new MctsNode(this, move, game);
        children.add(child);
        return child;
    }

    public void backPropagate(Reward reward) {
        this.reward.addReward(reward);
        this.numSimulations++;
        if (parent != null) {
            parent.backPropagate(reward);
        }
    }

    public LinkedList<MctsNode> getChildren() {
        return children;
    }

    public int getNumberOfSimulations() {
        return numSimulations;
    }

    public int getPlayer() {
        return player;
    }

    public double getRewardForPlayer(int player) {
        return reward.getRewardForPlayer(player);
    }

    public boolean canExpand() {
        return unexploredMoves.size() > 0;
    }

    public MctsNode getMostVisitedNode() {
        int mostVisitCount = 0;
        MctsNode bestChild = null;

        for (MctsNode child : getChildren()) {
            if (child.getNumberOfSimulations() > mostVisitCount) {
                bestChild = child;
                mostVisitCount = child.getNumberOfSimulations();
            }
        }

        return bestChild;
    }

    public TicTacToeMove getMoveUsedToGetToNode() {
        return moveUsedToGetToNode;
    }

    public void printTree(String spacing) {
        String playerRewards = "";

        for (int i = 0; i < reward.getReward().size(); i++) {
            playerRewards += i + ": " + reward.getReward().get(i) + ";";
        }

        String move;

        if (moveUsedToGetToNode == null) {
            move = "(null)";
        } else {
            move = "(" + moveUsedToGetToNode.position.x + "," + moveUsedToGetToNode.position.y + ")";
        }
        System.out.println(spacing + "Player: " + player + " " + move + " Simulations: " + numSimulations + ". Rewards: " + playerRewards);

        for (MctsNode child : getChildren()) {
            child.printTree(spacing + "  ");
        }
    }
}
