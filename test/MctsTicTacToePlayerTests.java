
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import tictactoe.GameSymbols;
import tictactoe.MctsTicTacToePlayer;
import tictactoe.TicTacToeBoard;
import tictactoe.TicTacToeGame;
import tictactoe.TicTacToeMove;

public class MctsTicTacToePlayerTests {

    private int _maxSimulations = 5000;

    public MctsTicTacToePlayerTests() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void mctsPlayerStopsImmediateLoss() {
        for (int i = 0; i < 100; i++) {
            TicTacToeBoard b = new TicTacToeBoard();
            b.play(1, 1, GameSymbols.Player1Symbol);
            b.play(0, 0, GameSymbols.Player2Symbol);
            b.play(2, 1, GameSymbols.Player1Symbol);
            b.play(0, 1, GameSymbols.Player2Symbol);

            TicTacToeGame game = new TicTacToeGame(b);
            MctsTicTacToePlayer player = new MctsTicTacToePlayer(_maxSimulations);
            TicTacToeMove move = player.getMove(game);
            game.makeMove(move);

            if (b.getSymbolAtPoint(new Point(0, 2)) != GameSymbols.Player1Symbol) {
                System.out.println("Didn't Stop Immediate Loss");
                b.printBoard();
            }

            assertEquals(GameSymbols.Player1Symbol, b.getSymbolAtPoint(new Point(0, 2)));
        }
    }

    @Test
    public void mctsPlayerStopsImmediateLoss2() {
        for (int i = 0; i < 100; i++) {
            TicTacToeBoard b = new TicTacToeBoard();
            b.play(0, 0, GameSymbols.Player1Symbol);
            b.play(2, 0, GameSymbols.Player2Symbol);
            b.play(1, 0, GameSymbols.Player1Symbol);
            b.play(1, 1, GameSymbols.Player2Symbol);
            b.play(0, 2, GameSymbols.Player1Symbol);
            b.play(0, 1, GameSymbols.Player2Symbol);

            TicTacToeGame game = new TicTacToeGame(b);

            MctsTicTacToePlayer player = new MctsTicTacToePlayer(_maxSimulations);
            game.makeMove(player.getMove(game));

            if (!(b.getSymbolAtPoint(new Point(2, 1)) == GameSymbols.Player1Symbol)) {
                System.out.println("Died");
                b.printBoard();
            }

            assertEquals(GameSymbols.Player1Symbol, b.getSymbolAtPoint(new Point(2, 1)));
        }
    }

    @Test
    public void mctsPlayerTakesTrickWinWhenPresented() {
        for (int i = 0; i < 100; i++) {
            TicTacToeBoard b = new TicTacToeBoard();
            b.play(0, 1, GameSymbols.Player2Symbol);
            b.play(2, 2, GameSymbols.Player2Symbol);
            b.play(0, 0, GameSymbols.Player1Symbol);
            b.play(1, 1, GameSymbols.Player1Symbol);

            TicTacToeGame game = new TicTacToeGame(b);
            MctsTicTacToePlayer player = new MctsTicTacToePlayer(_maxSimulations);

            TicTacToeMove move = player.getMove(game);
            game.makeMove(move);

            if (!(b.getSymbolAtPoint(new Point(2, 0)) == GameSymbols.Player1Symbol
                || b.getSymbolAtPoint(new Point(1, 0)) == GameSymbols.Player1Symbol)) {
                System.out.println("Missed Trick");
                b.printBoard();
            }

            assertTrue(GameSymbols.Player1Symbol == b.getSymbolAtPoint(new Point(2, 0))
                || GameSymbols.Player1Symbol == b.getSymbolAtPoint(new Point(1, 0))
            );
        }
    }

    @Test
    public void mctsPlayerTakesWinFromFarWhenPresented() {
        int testRuns = 100;
        int correct = 0;

        for (int i = 0; i < testRuns; i++) {
            TicTacToeBoard b = new TicTacToeBoard();
            b.play(1, 1, GameSymbols.Player1Symbol);
            b.play(1, 0, GameSymbols.Player2Symbol);

            TicTacToeGame game = new TicTacToeGame(b);
            MctsTicTacToePlayer player = new MctsTicTacToePlayer(_maxSimulations);

            TicTacToeMove move = player.getMove(game);
            game.makeMove(move);

            if (!(b.getSymbolAtPoint(new Point(0, 0)) == GameSymbols.Player1Symbol
                || b.getSymbolAtPoint(new Point(0, 2)) == GameSymbols.Player1Symbol
                || b.getSymbolAtPoint(new Point(2, 0)) == GameSymbols.Player1Symbol
                || b.getSymbolAtPoint(new Point(2, 2)) == GameSymbols.Player1Symbol
                || b.getSymbolAtPoint(new Point(0, 1)) == GameSymbols.Player1Symbol
                || b.getSymbolAtPoint(new Point(2, 1)) == GameSymbols.Player1Symbol)) {
                System.out.println("Missed Far Away Win");
                b.printBoard();
            } else {
                correct++;
            }
        }
        assertTrue(correct == testRuns);
    }


    @Test
    public void mctsDoesntWalkIntoTrap() {
        int countCorrect = 0;
        int trials = 100000;
        for (int i = 0; i < trials; i++) {
            TicTacToeBoard b = new TicTacToeBoard();
//            b.play(1, 1, GameSymbols.Player1Symbol);
            TicTacToeGame game = new TicTacToeGame(b);
            game.makeMove(new TicTacToeMove(new Point(1, 1), GameSymbols.Player1Symbol));

            MctsTicTacToePlayer player = new MctsTicTacToePlayer(_maxSimulations);
            TicTacToeMove move = player.getMove(game);
            Point position = move.getPosition();
            game.makeMove(move);

            if ((position.x == 0 && position.y == 0)
                || (position.x == 0 && position.y == 2)
                || (position.x == 2 && position.y == 0)
                || (position.x == 2 && position.y == 2)) {
                countCorrect++;
            } else {
                b.printBoard();
            }
        }

        assertEquals(trials, countCorrect);
    }

    @Test
    public void mctsNeverLoses()  {
        int testRuns = 10000;
        int correct = 0;

        for (int i = 0; i < testRuns; i++) {
            TicTacToeBoard b = new TicTacToeBoard();
            TicTacToeGame game = new TicTacToeGame(b);

            MctsTicTacToePlayer player1 = new MctsTicTacToePlayer(_maxSimulations);
            game.play(player1, player1);

            if (!game.boardContainsWinForSymbol(GameSymbols.Player1Symbol) && 
                !game.boardContainsWinForSymbol(GameSymbols.Player2Symbol)) {
                correct++;
            } else {
                System.out.println("Board state was not draw: ");
                b.printBoard();
            }
        }

        assertEquals(testRuns, correct);
    }
}
