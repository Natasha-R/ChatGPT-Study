package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;

public class Exercise0 implements Walkable {
    /*
    Maschine konstruieren
    Startposition angeben
    Feld angeben
    Wände bestimmen
     */

    private MiningMachine miningMachine = new MiningMachine(4,0, new MiningField(11,8, Arrays.asList(
            new Walls(1,6,4,6),
            new Walls(5,6,7,6),
            new Walls(1,0,1,6),
            new Walls(7,2,7,6)
    )));
    @Override
    public String walkTo(String walkCommandString) {
        return miningMachine.moveMiningMachine(walkCommandString);
    }


}
