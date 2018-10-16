package tictactoe;

public class MctsTicTacToePlayer implements TicTacToePlayer {

    public int maxIterations;

    public MctsTicTacToePlayer(int iterations) {
        this.maxIterations = iterations;
    }

    @Override
    public TicTacToeMove getMove(TicTacToeGame game) {
        MctsNode rootNode = new MctsNode(null, null, game);

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            TicTacToeGame gameCopy = game.getCopy();

            MctsNode node = select(rootNode, gameCopy);
            node = node.expand(gameCopy);
            Reward reward = rollout(gameCopy);
            node.backPropagate(reward);
        }

        MctsNode mostVisitedChild = rootNode.getMostVisitedNode();
        return mostVisitedChild.getMoveUsedToGetToNode();
    }

    private MctsNode select(MctsNode node, TicTacToeGame game) {
        while (!node.canExpand() && !game.gameIsOver()) {
            node = node.select();
            TicTacToeMove move = node.getMoveUsedToGetToNode();
            if (move != null) {
                game.makeMove(move);
            }
        }

        return node;
    }

    private Reward rollout(TicTacToeGame game) {
        TicTacToePlayer randomPlayer = new RandomTicTacToePlayer();

        game.play(randomPlayer, randomPlayer);

        return game.getReward();
    }
}
