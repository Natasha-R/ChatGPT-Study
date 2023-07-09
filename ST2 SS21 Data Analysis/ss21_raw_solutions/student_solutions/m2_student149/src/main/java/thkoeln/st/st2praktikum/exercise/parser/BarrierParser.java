package thkoeln.st.st2praktikum.exercise.parser;

import thkoeln.st.st2praktikum.exercise.environment.barrier.MyBarrier;
import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.exception.ParseException;

import java.util.regex.Matcher;

public class BarrierParser implements Parsable<MyBarrier> {

    private static final String PATTERN = "(\\((?:\\d|\\,)+\\))\\-(\\((?:\\d|\\,)+\\))";

    @Override
    public MyBarrier create(Matcher matcher) throws ParseException {
        Position start = Position.of(matcher.group(1));
        Position end = Position.of(matcher.group(2));

        return MyBarrier.of(start, end);
    }

    @Override
    public String getPattern() {
        return PATTERN;
    }
}
