package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Exercise0 implements Moveable {

    private Position currentPosition = new Position("(0,2)");
    private Map map = new Map(12, 9,
            Arrays.asList("(3,0)-(3,2)", "(5,0)-(5,3)", "(4,5)-(6,5)", "(7,5)-(7,8)").stream().map(barrier -> new Barrier(barrier)).collect(Collectors.toList()));

    @Override
    public String moveTo(String moveCommandString) {
        move(new Movement(moveCommandString));

        return currentPosition.toString();
    }

    private void move(Movement movement) {
        if (movement.getMoves() > 0) {
            Position previewedStep = movement.previewStep(currentPosition);
            if (map.isMoveValid(currentPosition, previewedStep)) {
                currentPosition = movement.makeStep(currentPosition);
                move(movement);
            }
        }
    }
}
