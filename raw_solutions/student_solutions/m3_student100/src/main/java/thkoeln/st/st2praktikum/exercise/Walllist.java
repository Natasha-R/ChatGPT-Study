package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Walllist implements AddobjectInterface {
    private List<Wall> walllist = new ArrayList<Wall>();


    @Override
    public void add(Object toadd) {
        walllist.add((Wall) toadd);
    }
}


