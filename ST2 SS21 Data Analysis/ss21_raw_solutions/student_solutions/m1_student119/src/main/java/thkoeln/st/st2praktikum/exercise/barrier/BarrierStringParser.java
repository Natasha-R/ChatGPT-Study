package thkoeln.st.st2praktikum.exercise.barrier;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BarrierStringParser {
    //"(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
    public static Barrier parse(String barrierString) {
        final Pattern pattern = Pattern.compile("^\\((.*),(.*)\\)-\\((.*),(.*)\\)$");
        final Matcher matcher = pattern.matcher(barrierString);

        if(matcher.find()) {
            final int startX = Integer.parseInt(matcher.group(1));
            final int startY = Integer.parseInt(matcher.group(2));

            final int endX = Integer.parseInt(matcher.group(3));
            final int endY = Integer.parseInt(matcher.group(4));
            final BarrierType barrierType = startX == endX ? BarrierType.vertical : BarrierType.horizontal;
            return new Barrier(new Point(startX, startY), new Point(endX, endY), barrierType);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
