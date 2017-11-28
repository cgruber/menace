package menace

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import menace.Player.O
import menace.Player.X
import org.junit.Test

class GameStateTest() {
    companion object {
        val E = null;
    }

    @Test fun x_winner() {
        val board =  board(
                X, X, X,
                O, O, X,
                O, O, E
        )
        print(render(board))
        assertThat(winner(board)).isEqualTo(X)
    }

    @Test fun o_winner() {
        val board =  board(
                X, X, O,
                O, O, X,
                O, X, E
        )
        print(render(board))
        assertThat(winner(board)).isEqualTo(O)
    }

    @Test fun no_winner() {
        val board =  board(
                X, X, O,
                O, X, X,
                X, O, O
        )
        print(render(board))
        assertThat(winner(board)).isEqualTo(null)
    }


}