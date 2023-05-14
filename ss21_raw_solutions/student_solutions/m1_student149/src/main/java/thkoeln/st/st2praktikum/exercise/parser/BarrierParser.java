package thkoeln.st.st2praktikum.exercise.parser;

import thkoeln.st.st2praktikum.exercise.environment.Barrier;
import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.exception.ParseException;

import java.util.regex.Matcher;

public class BarrierParser implements Parsable<Barrier> {

    private static final String PATTERN = "(\\((?:\\d|\\,)+\\))\\-(\\((?:\\d|\\,)+\\))";

    @Override
    public Barrier create(Matcher matcher) throws ParseException {
        Position start = Position.of(matcher.group(1));
        Position end = Position.of(matcher.group(2));

        return Barrier.of(start, end);
    }

    @Override
    public String getPattern() {
        return PATTERN;
    }
}
