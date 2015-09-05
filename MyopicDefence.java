package dmikurube;

import main.Defence;
import main.Direction;


public class MyopicDefence extends Defence {
    static final int N = 2;

    public Direction defend(int[] vec, int score) {
        Minimax.Result result = new Minimax.Result();
        Minimax.maxmin(vec, N, result);
        return result.direction;
    }
}
