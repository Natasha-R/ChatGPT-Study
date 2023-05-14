package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Barrier {
    private int[] start;
    private int[] end;

    private Character getAxis() {
        if (start[1]==end[1]) {
            return 'h';
        } else if (start[0]==end[0]) {
            return 'v';
        } else {
            throw new IllegalStateException();
        }
    }

    private Boolean isCrossingThePlane(int plane, int current, int next) {
        return current < plane && next == plane;
    }

    private Boolean isNotInOrder(int[] currentPosition, int[] nextPosition) {
        return currentPosition[0]==nextPosition[0] && currentPosition[1] > nextPosition[1]
                || currentPosition[1]==nextPosition[1] && currentPosition[0] > nextPosition[0];
    }

    public Boolean isBlocking(int[] currentPosition, int[] nextPosition) {
        //sorting to half the possible cases
        if (isNotInOrder(currentPosition,nextPosition)) {
            int[] temp = currentPosition;
            currentPosition = nextPosition;
            nextPosition = temp;
        }

        if ( getAxis() == 'h' && isCrossingThePlane(start[1], currentPosition[1], nextPosition[1]) ) {
            return nextPosition[0] >= start[0] && nextPosition[0] < end[0];
        } else if ( getAxis() == 'v' && isCrossingThePlane(start[0], currentPosition[0], nextPosition[0]) ) {
            return nextPosition[1] >= start[1] && nextPosition[1] < end[1];
        }

        return false;
    }

    public Barrier(int[] start, int[] end) {
        this.start = start;
        this.end = end;
    }

    public static List<Barrier> getDefaultBarrierList() {
        List<Barrier> allBarriers = new ArrayList<>();

        //borders of the map
        allBarriers.add( new Barrier(new int[]{0,0}, new int[]{0,8}) );
        allBarriers.add( new Barrier(new int[]{0,0}, new int[]{12,0}) );
        allBarriers.add( new Barrier(new int[]{12,0}, new int[]{12,8}) );
        allBarriers.add( new Barrier(new int[]{0,8}, new int[]{12,8}) );

        //additional barriers
        allBarriers.add( new Barrier(new int[]{1,6}, new int[]{6,6}) );
        allBarriers.add( new Barrier(new int[]{3,3}, new int[]{9,3}) );
        allBarriers.add( new Barrier(new int[]{4,1}, new int[]{4,3}) );
        allBarriers.add( new Barrier(new int[]{6,2}, new int[]{6,8}) );

        return allBarriers;
    }
}
