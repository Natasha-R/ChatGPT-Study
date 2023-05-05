package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Miningmachinelist implements AddobjectInterface {
    List<Miningmachine> miningmachinelist = new ArrayList<Miningmachine>();

    @Override
    public void add(Object miningmachine) {
        miningmachinelist.add((Miningmachine) miningmachine);

    }
}
