package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class BarrierList implements addStuff {
    public List<Barrier> getBarrierList() {
        return barrierList;
    }

    private List<Barrier> barrierList = new ArrayList<Barrier>();


    @Override
    public void add(Object toAdd) {
        barrierList.add((Barrier) toAdd);
    }
}

