/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * @author JLH
 */
public class SingleGameRun {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new SingleGameRun();
    }

    public SingleGameRun(){
        TicTacToePlayer player1 = new BufferedReaderPlayer();
        TicTacToePlayer player2 = new MctsTicTacToePlayer(10000);

        TicTacToeBoard board = new TicTacToeBoard();
        LoggingTicTacToeGame game = new LoggingTicTacToeGame(board);

        game.play(player1, player2);
    }
    
}
