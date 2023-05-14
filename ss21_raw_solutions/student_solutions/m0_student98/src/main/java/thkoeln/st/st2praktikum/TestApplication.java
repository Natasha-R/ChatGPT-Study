package thkoeln.st.st2praktikum;

import thkoeln.st.st2praktikum.exercise.CommandParser;
import thkoeln.st.st2praktikum.exercise.Exercise0;
import thkoeln.st.st2praktikum.exercise.Matrix;
import thkoeln.st.st2praktikum.exercise.Position;

import static thkoeln.st.st2praktikum.exercise.Orientation.*;

public class TestApplication {

    public static void main(String[] args) {
        Matrix M = Matrix.createMatrix();
        Position CurrentPosition = new Position(4,2);

        System.out.println(M.validatePosition(CurrentPosition, NO));
    }
}
