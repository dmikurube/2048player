package dmikurube;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Board;
import main.Defence;
import main.Direction;


public class DMDefence extends Defence {
    Random random = new Random();
    List<Direction> cands = new ArrayList<Direction>();

    @Override
    public Direction defend(int[] vec, int score) {
        cands.clear();
        for(Direction d : Direction.values()) {
            int[] v = vec.clone();
            if(Board.move(v, d) != null) {
                cands.add(d);
            }
        }
        int ncand = cands.size();
        if(ncand > 0) {
            int r = random.nextInt(ncand);
            return cands.get(r);
        } else {
            throw new RuntimeException("Cannot move to any direction");
        }
    }

}
