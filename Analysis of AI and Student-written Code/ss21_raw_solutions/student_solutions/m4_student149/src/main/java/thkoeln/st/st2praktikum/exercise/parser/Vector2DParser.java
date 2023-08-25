package thkoeln.st.st2praktikum.exercise.parser;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.exception.Vector2DOutOfSpaceException;

import java.util.regex.Matcher;

public class Vector2DParser implements Parsable<Vector2D> {

    private static final String PATTERN = "\\((\\d+)\\,(\\d+)\\)";

    @Override
    public Vector2D create(Matcher matcher) throws Vector2DOutOfSpaceException {
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));

        return new Vector2D(x, y);
    }

    @Override
    public String getPattern() {
        return PATTERN;
    }
}
