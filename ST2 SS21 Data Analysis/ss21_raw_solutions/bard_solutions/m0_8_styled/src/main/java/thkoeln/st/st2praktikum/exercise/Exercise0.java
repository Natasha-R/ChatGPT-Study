package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

        private int x = 7;
        private int y = 7;
        private int width = 11;
        private int height = 8;
        private String[] barriers = {"(2,1)-(10,1)", "(2,1)-(2,6)", "(2,6)-(7,6)", "(10,1)-(10,8)"};

        @Override
        public String move(String moveCommandString) {
                // Parse the move command string
                String[] tokens = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
                String direction = tokens[0];
                int steps = Integer.parseInt(tokens[1]);

                // Check if the move is valid
                if (!isValidMove(direction, steps)) {
                        return null;
                }

                // Move the cleaning device
                switch (direction) {
                        case "no":
                                for (int i = 0; i < steps; i++) {
                                        y--;
                                }
                                break;
                        case "ea":
                                for (int i = 0; i < steps; i++) {
                                        x++;
                                }
                                break;
                        case "so":
                                for (int i = 0; i < steps; i++) {
                                        y++;
                                }
                                break;
                        case "we":
                                for (int i = 0; i < steps; i++) {
                                        x--;
                                }
                                break;
                }

                // Return the new location of the cleaning device
                return "(" + x + "," + y + ")";
        }

        private boolean isValidMove(String direction, int steps) {
                // Check if the direction is valid
                if (!direction.equals("no") && !direction.equals("ea") && !direction.equals("so") && !direction.equals("we")) {
                        return false;
                }

                // Check if the number of steps is valid
                if (steps < 1) {
                        return false;
                }

                // Check if the move will hit a barrier
                for (String barrier : barriers) {
                        String[] coords = barrier.split("-");
                        int x1 = Integer.parseInt(coords[0].substring(1, coords[0].length()));
                        int y1 = Integer.parseInt(coords[0].substring(coords[0].length() - 1));
                        int x2 = Integer.parseInt(coords[1].substring(1, coords[1].length()));
                        int y2 = Integer.parseInt(coords[1].substring(coords[1].length() - 1));
                        if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                                return false;
                        }
                }

                // The move is valid
                return true;
        }
}