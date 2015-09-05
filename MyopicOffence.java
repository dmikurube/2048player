package dmikurube;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import main.Board;
import main.Offence;


public class MyopicOffence extends Offence {
    private class Selection {
        public Selection(int position, double rank) {
            this.position = position;
            this.rank = rank;
        }
        public int getPosition() { return this.position; }
        public double getRank() { return this.rank; }
        private final int position;
        private final double rank;
    }

    private boolean findSame(int[] field, int placement, int x, int y, int dirX, int dirY) {
        for (int i = 1; i < 4; ++i) {
            if (x + dirX * i < 0 || x + dirX * i > 3 ||
                y + dirY * i < 0 || y + dirY * i > 3) {
                break;
            }
            int found = field[(x + dirX * i) + (y + dirY * i) * 4];
            if (found == placement) {
                return true;
            } else if (found == 0) {
                continue;
            }
        }
        return false;
    }

    private double near(int[] field, int x, int y) {
        double rank = 1.0;

        int[] dx1 = {0, 1, 0, -1};
        int[] dy1 = {1, 0, -1, 0};
        for (int i = 0; i < 4; ++i) {
            int nx = x + dx1[i];
            int ny = y + dy1[i];
            if (nx < 0 || nx > 3 || ny < 0 || ny > 3) { continue; }
            if (field[nx + ny * 4] == 2) {
                rank *= 0.4;
            } else if (field[nx + ny * 4] == 4) {
                rank *= 0.6;
            } else if (field[nx + ny * 4] > 4) {
                rank *= (double)(31 - Integer.numberOfLeadingZeros(field[nx + ny * 4]));
            } else {
                rank *= 0.7;
            }
        }

        int[] dx2 = {1, 1, -1, -1};
        int[] dy2 = {1, -1, -1, 1};
        for (int i = 0; i < 4; ++i) {
            int nx = x + dx2[i];
            int ny = y + dy2[i];
            if (nx < 0 || nx > 3 || ny < 0 || ny > 3) { continue; }
            if (field[nx + ny * 4] == 2) {
                rank *= 0.6;
            } else if (field[nx + ny * 4] == 4) {
                rank *= 0.7;
            } else if (field[nx + ny * 4] > 4) {
                rank *= (double)(31 - Integer.numberOfLeadingZeros(field[nx + ny * 4])) * 0.25;
            } else {
                rank *= 0.8;
            }
        }

        return rank;
    }

    private Selection rank(int[] field, int placement, int target1, int target2) {
        int west = Math.min(target1 % 4, target2 % 4);
        int east = Math.max(target1 % 4, target2 % 4);
        int north = Math.min(target1 / 4, target2 / 4);
        int south = Math.max(target1 / 4, target2 / 4);

        double baseRank = (double)(field[target1] + field[target2]) *
                          (double)(8 - ((south - north) + (east - west)));
        Selection finalSelection = null;
        for (int y = north; y <= south; ++y) {
            for (int x = west; x <= east; ++x) {
                double rateRank = 1.0;
                if (field[x + y*4] == 0) {
                    rateRank *= near(field, x, y);
                    if (finalSelection == null || finalSelection.getRank() < baseRank * rateRank) {
                        finalSelection = new Selection(x + y*4, baseRank * rateRank);
                    }
                }
            }
        }
        return finalSelection;
    }

    public static <T extends Comparable<? super T>> List<T> sorted(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    }

    @Override
    public int offend(int[] field, int score) {
        Random random = new Random();
        HashMap<Integer, ArrayList<Integer>> dict = new HashMap<Integer, ArrayList<Integer>>();

        int bestPosition = -1;
        int bestRank = -1;

        for (int i = 0; i < Board.SIZE2; i++) {
            if (!dict.containsKey(field[i])) {
                dict.put(field[i], new ArrayList<Integer>());
            }
            dict.get(field[i]).add(i);
        }

        Selection finalSelection = null;
        for (Map.Entry<Integer, ArrayList<Integer>> entry: dict.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); ++i) {
                for (int j = i + 1; j < entry.getValue().size(); ++j) {
                    Selection selection =
                        rank(field, 2, entry.getValue().get(i), entry.getValue().get(j));
                    if (selection != null &&
                        (finalSelection == null ||
                         finalSelection.getRank() < selection.getRank())) {
                        finalSelection = selection;
                    }
                }
            }
        }
        for (int number: sorted(dict.keySet())) {
            for (int i: dict.get(number)) {
                if (dict.containsKey(number / 2)) {
                    for (int j: dict.get(number / 2)) {
                        Selection selection = rank(field, 2, i, j);
                        if (selection != null &&
                            (finalSelection == null ||
                             finalSelection.getRank() < selection.getRank())) {
                            finalSelection = selection;
                        }
                    }
                }
            }
        }

        if (finalSelection == null) {
            int size = dict.get(0).size();
            if(size > 0) {
                int r = random.nextInt(size);
                return (dict.get(0).get(r));
            } else {
                throw new RuntimeException("No vacant cell found");
            }
        } else {
            return finalSelection.getPosition();
        }
    }
}
