package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x;
    private int y;
    private final int maxX = 12;
    private final int maxY = 8;

    public Exercise0() {
        x = 3;
        y = 0;
    }

    public String walkTo(String walkCommandString) {
        String direction = walkCommandString.substring(1,3);
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length()-1));
        if (direction.equals("no")) {
            y += steps;
            if (y >= maxY || (x == 3 && y > 3) || (x >= 4 && x <= 7 && y > 3) || (x == 7 && y > 2) || (x >= 1 && x <= 8 && y > 4)) {
                if (y >= maxY) {
                    y = maxY;
                }
                if (x == 3 && y > 3) {
                    y = 3;
                }
                if (x >= 4 && x <= 7 && y > 3) {
                    y = 3;
                }
                if (x == 7 && y > 2) {
                    y = 2;
                }
                if (x >=1 && x <=8 && y >4) {
                    y = 4;
                }
            }
        } else if (direction.equals("ea")) {
            x += steps;
            if (x >= maxX || (y <= 3 && x > 3) || (y == 3 && x > 7) || (y <=2 && x >7) || (y ==4 && x >8)) {
                if (x >= maxX) {
                    x = maxX;
                }
                if (y <=3 && x >3) {
                    x = 3;
                }
                if (y ==3 && x >7) {
                    x = 7;
                }
                if (y <=2 && x >7) {
                    x = 7;
                }
                if (y ==4 && x >8) {
                    x =8;
                }
            }
        } else if (direction.equals("so")) {
            y -= steps;
            if (y <0) {
                y =0;
            }
        } else if (direction.equals("we")) {
            x -= steps;
            if (x <0) {
                x =0;
            }
        }
        return "(" + x + "," + y + ")";
    }
}