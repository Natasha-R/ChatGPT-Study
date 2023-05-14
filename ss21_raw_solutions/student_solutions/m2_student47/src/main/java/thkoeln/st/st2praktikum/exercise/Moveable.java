package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public interface Moveable {
    void moveWest(ArrayList<Field> fieldlist,ArrayList<MiningMachine> machines) ;
    void moveSouth(ArrayList<Field> fieldList,ArrayList<MiningMachine> machines);
    void moveEast(ArrayList<Field> fieldList,ArrayList<MiningMachine> machines);
    void moveNorth(ArrayList<Field> fieldList,ArrayList<MiningMachine> machines);
}
