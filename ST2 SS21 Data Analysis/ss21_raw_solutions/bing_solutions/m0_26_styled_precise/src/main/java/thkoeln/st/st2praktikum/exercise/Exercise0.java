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

    @Override
    public String walkTo(String walkCommandString) {
        String direction = walkCommandString.substring(1,3);
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length()-1));
        if (direction.equals("no")) {
            y += steps;
            if (y >= maxY || (x == 3 && y > 3) || (x >= 4 && x <= 7 && y > 3) || (x == 7 && y > 2) || (x >= 1 && x <= 8 && y > 4)) {
                y = Math.min(maxY, Math.min(y, Math.min((x == 3 ? 3 : y), (x >= 4 && x <= 7 ? 3 : y))));
                y = Math.min(y, Math.min((x == 7 ? 2 : y), (x >= 1 && x <= 8 ? 4 : y)));
            }
        } else if (direction.equals("ea")) {
            x += steps;
            if (x >= maxX || (y <= 3 && x > 3) || (y == 3 && x > 7) || (y <= 2 && x > 7) || (y == 4 && x > 8)) {
                x = Math.min(maxX, Math.min(x, Math.min((y <= 3 ? 3 : x), (y == 3 ? 7 : x))));
                x = Math.min(x, Math.min((y <=2 ? 7 : x), (y ==4 ? 8 : x)));
            }
        } else if (direction.equals("so")) {
            y -= steps;
            if (y <0 || (x ==3 && y <0) || (x >=4 && x <=7 && y <0) || (x ==7 && y <0) || (x >=1 && x <=8 && y <0)) {
                y = Math.max(0, Math.max(y, Math.max((x ==3 ?0 :y), (x >=4 && x <=7 ?0 :y))));
                y = Math.max(y, Math.max((x ==7 ?0 :y), (x >=1 && x <=8 ?0 :y)));
            }
        } else if (direction.equals("we")) {
            x -= steps;
            if (x <0 || (y <=3 && x <0) || (y ==3 && x <4) || (y <=2 && x <0) || (y ==4 && x <1)) {
                x = Math.max(0, Math.max(x, Math.max((y <=3 ?0 :x), (y ==3 ?4 :x))));
                x = Math.max(x, Math.max((y <=2 ?0 :x), (y ==4 ?1 :x)));
            }
        }
        return "(" + x + "," + y + ")";
    }
}