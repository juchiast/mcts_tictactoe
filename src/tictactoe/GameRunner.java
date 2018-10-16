package tictactoe;

public class GameRunner {

    public static void main(String[] args) {
        new GameRunner();
    }

    public GameRunner() {
        int wins = 0;
        int draws = 0;
        int losses = 0;
        for (int i =0; i < 100; i++){
            TicTacToePlayer player1 = new BufferedReaderPlayer();
            TicTacToePlayer player2 = new MctsTicTacToePlayer(10000);

            TicTacToeBoard board = new TicTacToeBoard();
            LoggingTicTacToeGame game = new LoggingTicTacToeGame(board);

            game.play(player1, player2);

            if (game.getReward().getRewardForPlayer(0) > 0) {
                wins++;
                System.out.println("Player 1 Wins!");
            } else if (game.getReward().getRewardForPlayer(0) < 0) {
                losses++;
                System.out.println("Player 2 Wins!");
            } else {
                draws++;
                System.out.println("Game was a draw!");
            }
        }
        System.out.println("Won: " + wins);
        System.out.println("Drew: " + draws);
        System.out.println("Lost: " + losses);
    }
}
