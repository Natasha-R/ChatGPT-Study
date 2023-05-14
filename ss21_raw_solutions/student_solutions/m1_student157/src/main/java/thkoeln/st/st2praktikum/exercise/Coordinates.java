package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(String coordinates) {
        final String regex = "\\(([0-9]*),([0-9]*)\\)";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(coordinates);

        while (matcher.find()) {
            this.x = Integer.parseInt(matcher.group(1));
            this.y = Integer.parseInt(matcher.group(2));
        }
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates coordinates = (Coordinates) o;
        return this.x == coordinates.getX() && this.y == coordinates.getY();
    }

}
