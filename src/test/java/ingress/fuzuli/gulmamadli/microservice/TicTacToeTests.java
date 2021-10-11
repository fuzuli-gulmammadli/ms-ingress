package ingress.fuzuli.gulmamadli.microservice;

import ingress.fuzuli.gulmamadli.microservice.lesson4.TicTacToe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TicTacToeTests {

    private TicTacToe game;

    @BeforeEach
    public void init(){
        game = new TicTacToe();
    }

    @Test
    public void caseWhenXIsOutOfTheBoardThenRuntimeException(){
        int x = 4;
        int y = 2;

        assertThatThrownBy(() -> game.play(x, y))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("X is out of the Board!");
    }

    @Test
    public void caseWhenYIsOutOfTheBoardThenRuntimeException(){
        int x = 2;
        int y = 5;

        assertThatThrownBy(() -> game.play(x, y))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Y is out of the Board!");
    }

    @Test
    public void caseWhenFirstMoveThenPlayerShouldBeX(){
        game.setCurrentPlayer();
        assertThat(game.getCurrentPlayer()).isEqualTo('X');
    }

    @Test
    public void caseWhenSecondMoveThenPlayerShouldBeO(){
        game.setCurrentPlayer();
        game.setCurrentPlayer();
        assertThat(game.getCurrentPlayer()).isEqualTo('O');
    }

    @Test
    public void whenPlayOccupiedBowThenRuntimeException(){
        game.play(1, 1);
        assertThatThrownBy(() -> game.play(1, 1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Box already taken!");
    }

    @Test
    public void whenAllHorizontalSymbolsAreSameThenWinner(){
        game.play(2, 1);
        game.play(3, 1);
        game.play(2, 2);
        game.play(3, 2);
        game.play(2, 3);

        assertThat(game.gameResult).isEqualTo("X has won!");
    }

    @Test
    public void whenAllVerticalSymbolsAreSameThenWinner(){
        game.play(1, 2);
        game.play(1, 1);
        game.play(2, 2);
        game.play(1, 3);
        game.play(3, 2);

        assertThat(game.gameResult).isEqualTo("X has won!");
    }

    @Test
    public void whenAnyDiagonalSymbolsAreSameThenWinner(){
        game.play(1, 1);
        game.play(2, 1);
        game.play(2, 2);
        game.play(2, 3);
        game.play(3, 3);

        assertThat(game.gameResult).isEqualTo("X has won!");
    }

    @Test
    public void whenAllMovesMadeAndNoWinnerThenDraw(){
        game.play(1, 2); //X
        game.play(1, 1); //O
        game.play(2, 2); //X
        game.play(1, 3); //O
        game.play(2, 3); //X
        game.play(2, 1); //O
        game.play(3, 1); //X
        game.play(3, 2); //O
        game.play(3, 3); //x

        assertThat(game.gameResult).isEqualTo("Draw!");
    }
}

