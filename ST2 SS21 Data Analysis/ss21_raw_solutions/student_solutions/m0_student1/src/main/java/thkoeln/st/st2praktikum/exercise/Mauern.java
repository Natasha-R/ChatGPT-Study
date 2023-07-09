package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Mauern {

    public List<String> mauernX() {

        List<String> listx = new ArrayList<>();
        listx.add("y1-5,x2");
        listx.add("y1-10,x10");
        return listx;
    }

    public List<String> mauernY() {

        List<String> listy = new ArrayList<>();
        listy.add("x2-9,y1");
        listy.add("x2-6,y6");
        return listy;
    }

}
