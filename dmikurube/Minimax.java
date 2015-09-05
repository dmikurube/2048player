package dmikurube;

import main.Board;
import main.Direction;

public class Minimax {
    /**
     * @author terada
     * 最前の評価値とその手の両方を返すために、手を格納するオブジェクト。
     */
    public static class Result {
        int position;
        Direction direction;
    }

    /**
     * 守備側が最善の手を選ぶ。
     * @param vec 現在の盤面
     * @param n 残る読みの深さ (守備側を何手読むか)
     * @param result 手を返す必要があるときは格納用オブジェクトを渡す
     * @return 最善手の評価値
     */
    static int maxmin(int[] vec, int n, Result result) {
        int max = -9999999;
        // ４方向すべてについて検討する
        for(Direction d : Direction.values()){
            // 作業用の盤面を作成
            int[] work = vec.clone();
            // その方向に動かしてみる
            Integer i = Board.move(work, d);
            if(i != null){
                // 動かせたら
                int j = i; // その動きでの得点
                if(n > 0){
                    // 読みの末端でないなら
                    // 攻撃側の身になって最善手を考えて
                    j += minmax(work, n-1, null);
                }
                if(j >= max){
                    // より良い手が見つかったら
                    max = j;
                    if(result != null)
                        // 手も記録する必要がある場合
                        result.direction = d;
                }
            }
        }
        return max;
    }

    /**
     * 攻撃側が最善の手を選ぶ。
     * @param vec 現在の盤面
     * @param n 残る読みの深さ (守備側を何手読むか)
     * @param result 手を返す必要があるときは格納用オブジェクトを渡す
     * @return 最善手の評価値
     */
    static int minmax(int[] vec, int n, Result result) {
        int min = Integer.MAX_VALUE;
        for(int i=0; i<Board.SIZE2; i++){
            if(vec[i] == 0){
                // 盤面上のすべての空きマスについて
                int[] work = vec.clone();
                // 作業用盤面の該当箇所に2を置いて
                work[i] = 2;
                // 守備側の身になって最善手を求めて
                int j = maxmin(work, n, null);
                if(j < min){
                    // より守備側の点が下がる手を選ぶ
                    min = j;
                    if(result != null)
                        result.position = i;
                }
            }
        }
        return min;
    }
}
