package dmikurube;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Board;
import main.Defence;
import main.Direction;


public class MyopicDefence extends Defence {
    private double[] rates = {5.0, 2.0, 5.0, 2.0, 1.0, 2.0, 5.0, 2.0, 8.0};
    private int[] dxs = {0, 1, 2, 0, 1, 2, 0, 1, 2};
    private int[] dys = {0, 0, 0, 1, 1, 1, 2, 2, 2};

    public Direction defend(int[] field, int score) {
        double finalRank = -1.0;
        Direction finalDirection = null;

        for (Direction d: Direction.values()) {
            int[] movedLogField = field.clone();
            Integer moveScore = Board.move(movedLogField, d);
            for (int i = 0; i < Board.SIZE2; ++i) {
                movedLogField[i] = 31 - Integer.numberOfLeadingZeros(movedLogField[i]);
            }

            if (moveScore == null) { continue; }
            double rank = 0.0;
            for (int i = 0; i < 9; ++i) {
                double subRank = rates[i];
                for (int dx = 0; dx < 2; ++dx) {
                    for (int dy = 0; dy < 2; ++dy) {
                        int x = dxs[i] + dx;
                        int y = dys[i] + dy;
                        if (movedLogField[x + y * 4] > 0) {
                            subRank *= movedLogField[x + y * 4];
                        }
                    }
                }
                rank += subRank;
            }
            if (finalRank < rank) {
                finalDirection = d;
                finalRank = rank;
            }
        }

        if (finalDirection != null) {
            return finalDirection;
        }

        Random random = new Random();
        List<Direction> candidates = new ArrayList<Direction>();
        for (Direction d: Direction.values()) {
            int[] clonedField = field.clone();
            if (Board.move(clonedField, d) != null) {
                candidates.add(d);
            }
        }
        if (candidates.isEmpty()) {
            throw new RuntimeException("Cannot move to any direction");
        } else {
            int r = random.nextInt(candidates.size());
            return candidates.get(r);
        }
    }
}
