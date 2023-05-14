package thkoeln.st.st2praktikum.exercise.parser;

import thkoeln.st.st2praktikum.exercise.Vector2D;
import thkoeln.st.st2praktikum.exercise.environment.barrier.MyBarrier;
import thkoeln.st.st2praktikum.exercise.exception.ParseException;

import java.util.regex.Matcher;

public class BarrierParser implements Parsable<MyBarrier> {

    private static final String PATTERN = "(\\((?:\\d|\\,)+\\))\\-(\\((?:\\d|\\,)+\\))";

    @Override
    public MyBarrier create(Matcher matcher) throws ParseException {
        Vector2D start = new Vector2D(matcher.group(1));
        Vector2D end = new Vector2D(matcher.group(2));

        return MyBarrier.of(start, end);
    }

    @Override
    public String getPattern() {
        return PATTERN;
    }
}
