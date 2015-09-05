package dmikurube;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Board;
import main.Offence;


public class DMOffence extends Offence {
    Random random = new Random();
    List<Integer> cands = new ArrayList<Integer>();

    @Override
    public int offend(int[] vec, int score) {
        cands.clear();
        for(int i = 0; i < Board.SIZE2; i++) {
            if(vec[i] == 0) {
                cands.add(i);
            }
        }
        int size = cands.size();
        if(size > 0) {
            int r = random.nextInt(size);
            return (cands.get(r));
        } else {
            throw new RuntimeException("No vacant cell found");
        }
    }
}
