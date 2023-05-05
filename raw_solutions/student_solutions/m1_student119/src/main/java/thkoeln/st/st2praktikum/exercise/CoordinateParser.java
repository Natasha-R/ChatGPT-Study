package thkoeln.st.st2praktikum.exercise;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoordinateParser
{
    public static Point parse(String coordinate) {
        final Pattern pattern = Pattern.compile("^\\((.*),(.*)\\)$");
        final Matcher matcher = pattern.matcher(coordinate);

        if(matcher.find()) {
            final int x = Integer.parseInt(matcher.group(1));
            final int y = Integer.parseInt(matcher.group(2));

            return new Point(x, y);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
