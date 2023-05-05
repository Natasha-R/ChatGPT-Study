package thkoeln.st.st2praktikum.parser;

import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

@Component
public class Serializer {

    public String serialize(Vector position) {
        return new StringBuilder()
                .append('(')
                .append((int) position.getX())
                .append(',')
                .append((int) position.getY())
                .append(')')
                .toString();
    }
}
