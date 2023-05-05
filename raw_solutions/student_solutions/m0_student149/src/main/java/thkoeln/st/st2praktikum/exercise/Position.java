package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Position {

    private static final String PATTERN = "\\((\\d+)\\,(\\d+)\\)";

    private int x;
    private int y;

    public Position(String position) {
        parsePosition(position);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" +
                x +
                "," +
                y +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    private void parsePosition(String position) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(position);

        if (matcher.find()) {
            x = Integer.parseInt(matcher.group(1));
            y = Integer.parseInt(matcher.group(2));
        }
    }
}
