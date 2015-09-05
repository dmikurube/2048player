package dmikurube;

import main.Board;
import main.Direction;

public class Minimax {
    /**
     * @author terada
     * $B:GA0$NI>2ACM$H$=$N<j$NN>J}$rJV$9$?$a$K!"<j$r3JG<$9$k%*%V%8%'%/%H!#(B
     */
    public static class Result {
        int position;
        Direction direction;
    }

    /**
     * $B<iHwB&$,:GA1$N<j$rA*$V!#(B
     * @param vec $B8=:_$NHWLL(B
     * @param n $B;D$kFI$_$N?<$5(B ($B<iHwB&$r2?<jFI$`$+(B)
     * @param result $B<j$rJV$9I,MW$,$"$k$H$-$O3JG<MQ%*%V%8%'%/%H$rEO$9(B
     * @return $B:GA1<j$NI>2ACM(B
     */
    static int maxmin(int[] vec, int n, Result result) {
        int max = -9999999;
        // $B#4J}8~$9$Y$F$K$D$$$F8!F$$9$k(B
        for(Direction d : Direction.values()){
            // $B:n6HMQ$NHWLL$r:n@.(B
            int[] work = vec.clone();
            // $B$=$NJ}8~$KF0$+$7$F$_$k(B
            Integer i = Board.move(work, d);
            if(i != null){
                // $BF0$+$;$?$i(B
                int j = i; // $B$=$NF0$-$G$NF@E@(B
                if(n > 0){
                    // $BFI$_$NKvC<$G$J$$$J$i(B
                    // $B967bB&$N?H$K$J$C$F:GA1<j$r9M$($F(B
                    j += minmax(work, n-1, null);
                }
                if(j >= max){
                    // $B$h$jNI$$<j$,8+$D$+$C$?$i(B
                    max = j;
                    if(result != null)
                        // $B<j$b5-O?$9$kI,MW$,$"$k>l9g(B
                        result.direction = d;
                }
            }
        }
        return max;
    }

    /**
     * $B967bB&$,:GA1$N<j$rA*$V!#(B
     * @param vec $B8=:_$NHWLL(B
     * @param n $B;D$kFI$_$N?<$5(B ($B<iHwB&$r2?<jFI$`$+(B)
     * @param result $B<j$rJV$9I,MW$,$"$k$H$-$O3JG<MQ%*%V%8%'%/%H$rEO$9(B
     * @return $B:GA1<j$NI>2ACM(B
     */
    static int minmax(int[] vec, int n, Result result) {
        int min = Integer.MAX_VALUE;
        for(int i=0; i<Board.SIZE2; i++){
            if(vec[i] == 0){
                // $BHWLL>e$N$9$Y$F$N6u$-%^%9$K$D$$$F(B
                int[] work = vec.clone();
                // $B:n6HMQHWLL$N3:Ev2U=j$K(B2$B$rCV$$$F(B
                work[i] = 2;
                // $B<iHwB&$N?H$K$J$C$F:GA1<j$r5a$a$F(B
                int j = maxmin(work, n, null);
                if(j < min){
                    // $B$h$j<iHwB&$NE@$,2<$,$k<j$rA*$V(B
                    min = j;
                    if(result != null)
                        result.position = i;
                }
            }
        }
        return min;
    }
}
