package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Walkable {

    private final int WIDTH = 12;
    private final int HEIGHT = 8;

    private int x;
    private int y;

    private final Map<String, Wall> walls = new HashMap<>();


    public Exercise0() {
        this.x = 3;
        this.y = 0;

        walls.put("w1", new Wall(3, 0, 3, 3));
        walls.put("w2", new Wall(7, 0, 7, 2));

        walls.put("w3", new Wall(4, 3, 7, 3));
        walls.put("w4", new Wall(1, 4, 8, 4));
    }

    @Override
    public String walkTo(String walkCommandString) {

        Pattern pattern = Pattern.compile("\\[(.*),(.*)\\]");
        Matcher matcher = pattern.matcher(walkCommandString);

        if (matcher.find()) {

            String direction = matcher.group(1);
            int steps = Integer.parseInt(matcher.group(2));

            try {

                switch (direction) {
                    case "no":
                        for (int i = 0; i < steps; i++) {

                            if (!isWallPresent(x, y + 1)) {

                                if (y + 1 < HEIGHT) {
                                    y++;
                                }
                            } else {
                                break;
                            }
                        }
                        break;

                    case "ea":
                        for (int i = 0; i < steps; i++) {
                            if (!isWallPresent(x + 1, y)) {

                                if (x + 1 < WIDTH) {
                                    x++;
                                }
                            } else {
                                break;
                            }
                        }
                        break;

                    case "so":
                        for (int i = 0; i < steps; i++) {
                            if (!isWallPresent(x, y - 1)) {

                                if (y - 1 >= 0) {
                                    y--;
                                }
                            } else {
                                break;
                            }
                        }
                        break;

                    case "we":
                        for (int i = 0; i < steps; i++) {
                            if (!isWallPresent(x - 1, y)) {
                                if (x - 1 >= 0) {
                                    x--;
                                }
                            } else {
                                break;
                            }
                        }
                        break;

                    default:
                        throw new IllegalArgumentException("Invalid direction: " + direction);
                }

            } catch (IllegalArgumentException e) {

                System.err.println(e.getMessage());
            }

        } else {
            System.err.println("Invalid command format: " + walkCommandString);
        }


        return "(" + this.x + "," + this.y + ")";
    }

    private boolean isWallPresent(int x, int y) {
        for (Wall wall : walls.values()) {
            if (wall.isBlockage(x, y)) {
                return true;
            }
        }
        return false;
    }

    private class Wall {
        int x1, y1, x2, y2;

        public Wall(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public boolean isBlockage(int x, int y) {

            return (x == this.x1 && y >= this.y1 && y <= this.y2) ||
                    (x == this.x2 && y >= this.y1 && y <= this.y2);
        }
    }
}
