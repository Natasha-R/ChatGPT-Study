package thkoeln.st.st2praktikum.parser;

public class MovementSerializer {

    public String serialize(double[] position) {
        return new StringBuilder()
                .append('(')
                .append((int) position[0])
                .append(',')
                .append((int) position[1])
                .append(')')
                .toString();
    }
}
