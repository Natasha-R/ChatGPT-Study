package thkoeln.st.st2praktikum.exercise;

public class Handle {
    private Field field = new Field();
    private MiningMachine miningMachine = new MiningMachine();

    public String goTo(String goCommandString) {
        int index = goCommandString.lastIndexOf(',');
        int numberOfSteps = Integer.valueOf(String.valueOf(goCommandString.charAt(index + 1)));
        if (numberOfSteps > 0) {
            if (goCommandString.contains("no")) {
                miningMachine.moveNorth(field, numberOfSteps);
            }
            if (goCommandString.contains("ea")) {
                miningMachine.moveEast(field, numberOfSteps);
            }
            if (goCommandString.contains("so")) {
                miningMachine.moveSouth(field, numberOfSteps);
            }
            if (goCommandString.contains("we")) {
                miningMachine.moveWest(field, numberOfSteps);
            }
        }
        return "("+miningMachine.getxPos()+","+miningMachine.getyPos()+")";
    }
}
