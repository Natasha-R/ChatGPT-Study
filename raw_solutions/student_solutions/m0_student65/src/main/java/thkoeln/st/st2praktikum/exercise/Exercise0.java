package thkoeln.st.st2praktikum.exercise;

import java.util.Vector;

public class Exercise0 implements GoAble {
    Handle handle = new Handle();
    public Exercise0() {}

    @Override
    public String goTo(String goCommandString) {
        return handle.goTo(goCommandString);
    }
}
