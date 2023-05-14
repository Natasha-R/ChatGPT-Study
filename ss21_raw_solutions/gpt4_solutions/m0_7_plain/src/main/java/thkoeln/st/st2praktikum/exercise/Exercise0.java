package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Walkable {
    private final int WIDTH = 12;
    private final int HEIGHT = 9;
    private Point currentPosition = new Point(0, 2);

    @Override
    public String walk(String walkCommandString) {
        Pattern pattern = Pattern.compile("\\[(\\w+),(\\d+)\\]");
        Matcher matcher = pattern.matcher(walkCommandString);

        if (matcher.find()) {
            String direction = matcher.group(1);
            int steps = Integer.parseInt(matcher.group(2));

            switch (direction) {
                case "no":
                    currentPosition.y = Math.min(currentPosition.y + steps, HEIGHT - 1);
                    if (currentPosition.x == 3 && currentPosition.y >= 3) {
                        currentPosition.y = Math.min(2, currentPosition.y - 1);
                    } else if (currentPosition.x == 7 && currentPosition.y >= 5) {
                        currentPosition.y = Math.min(4, currentPosition.y - 1);
                    }
                    break;
                case "ea":
                    currentPosition.x = Math.min(currentPosition.x + steps, WIDTH - 1);
                    if (currentPosition.y <= 3 && currentPosition.x >= 3) {
                        currentPosition.x = Math.min(2, currentPosition.x - 1);
                    } else if (currentPosition.y >= 5 && currentPosition.x >= 5) {
                        currentPosition.x = Math.min(4, currentPosition.x - 1);
                    }
                    break;
                case "so":
                    currentPosition.y = Math.max(currentPosition.y - steps, 0);
                    if (currentPosition.x == 3 && currentPosition.y <= 3) {
                        currentPosition.y = Math.max(4, currentPosition.y + 1);
                    } else if (currentPosition.x == 7 && currentPosition.y <= 5) {
                        currentPosition.y = Math.max(6, currentPosition.y + 1);
                    }
                    break;
                case "we":
                    currentPosition.x = Math.max(currentPosition.x - steps, 0);
                    if (currentPosition.y <= 3 && currentPosition.x <= 3) {
                        currentPosition.x = Math.max(4, currentPosition.x + 1);
                    } else if (currentPosition.y >= 5 && currentPosition.x <= 7) {
                        currentPosition.x = Math.max(8, currentPosition.x + 1);
                    }
                    break;
            }
        }

        return String.format("(%d,%d)", currentPosition.x, currentPosition.y);
    }
}