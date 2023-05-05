package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Miningmachinelist implements AddobjectInterface {
    private List<Miningmachine> miningmachinelist = new ArrayList<Miningmachine>();

    public List<Miningmachine> getMiningmachinelist() {
        return miningmachinelist;
    }

    @Override
    public void add(Object miningmachine) {
        miningmachinelist.add((Miningmachine) miningmachine);

    }
}
