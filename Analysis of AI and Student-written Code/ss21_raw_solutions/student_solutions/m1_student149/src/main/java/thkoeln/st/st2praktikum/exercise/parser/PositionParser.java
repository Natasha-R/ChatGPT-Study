package thkoeln.st.st2praktikum.exercise.parser;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;

import java.util.regex.Matcher;

public class PositionParser implements Parsable<Position> {

    private static final String PATTERN = "\\((\\d+)\\,(\\d+)\\)";

    @Override
    public Position create(Matcher matcher) {
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));

        return Position.of(x, y);
    }

    @Override
    public String getPattern() {
        return PATTERN;
    }
}
