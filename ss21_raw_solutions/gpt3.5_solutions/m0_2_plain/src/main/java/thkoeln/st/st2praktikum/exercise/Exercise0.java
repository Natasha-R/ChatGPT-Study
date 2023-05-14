package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    private int x; // current x-coordinate
    private int y; // current y-coordinate

    public Exercise0() {
        // Initialize the starting position of the maintenance droid
        x = 11;
        y = 7;
    }

    @Override
    public String goTo(String goCommandString) {
        String[] command = goCommandString.replace("[", "").replace("]", "").split(",");
        String direction = command[0];
        int steps = Integer.parseInt(command[1]);

        // Update the coordinates based on the direction and number of steps
        for (int i = 0; i < steps; i++) {
            switch (direction) {
                case "no": // north
                    if (y == 5 && x >= 5 && x <= 6) {
                        y = 4;
                        break;
                    } else if (y > 0) {
                        y--;
                    }
                    break;
                case "ea": // east
                    if (x == 5 && y >= 5 && y <= 6) {
                        x = 6;
                        break;
                    } else if (x < 11) {
                        x++;
                    }
                    break;
                case "so": // south
                    if (y == 0 && x >= 5 && x <= 6) {
                        y = 1;
                        break;
                    } else if (y < 7) {
                        y++;
                    }
                    break;
                case "we": // west
                    if (x == 6 && y >= 1 && y <= 5) {
                        x = 5;
                        break;
                    } else if (x > 0) {
                        x--;
                    }
                    break;
            }
        }

        return "(" + x + "," + y + ")";
    }
}