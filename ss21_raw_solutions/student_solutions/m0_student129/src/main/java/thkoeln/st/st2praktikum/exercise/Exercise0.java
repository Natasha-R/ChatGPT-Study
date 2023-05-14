package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {
    int[][] matrix = {
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
    int posX = 1;
    int posY = 1;
    int steps = 0;
    boolean no_change = true;

    @Override
    public String move(String moveCommandString) {

        if (moveCommandString.charAt(0) == 'n') {
            steps = moveCommandString.charAt(3) - '0';
            for (int i = 0; i < steps; i++) {
                if (posY <= 0) {
                    break;
                } else if (matrix[posY - 1][posX] == 1) {
                    break;
                } else {
                    no_change = false;
                    posY--;
                }
                if (posY <= 0) {
                    break;
                }
            }
        } else if (moveCommandString.charAt(0) == 'e') {
            steps = moveCommandString.charAt(3) - '0';
            for (int i = 0; i < steps; i++) {
                if (posX >= 10) {
                    break;
                } else {
                    posX++;
                }
                if (matrix[posY][posX] == 1) {
                    posX--;
                    break;
                }
                if (posX >= 10) {
                    break;
                }
            }

        } else if (moveCommandString.charAt(0) == 's') {
            steps = moveCommandString.charAt(3) - '0';
            for (int i = 0; i < steps; i++) {
                if (posY <= 0) {
                    break;
                } else if (matrix[posY + 1][posX] == 1) {
                    break;
                } else {
                    no_change = false;
                    posY++;
                }
                if (posY >= 8) {
                    break;
                }
            }

        } else if (moveCommandString.charAt(0) == 'w') {
            steps = moveCommandString.charAt(3) - '0';
            for (int i = 0; i < steps; i++) {
                if (posX <= 0) {
                    break;
                } else {
                    posX--;
                }
                if (matrix[posY][posX] == 1) {
                    posX++;
                    break;
                }
                if (posX <= 0) {
                    break;
                }
            }
        }
        if (no_change) {
            return "(" + posX + "," + (7 - posY) + ")";
        } else {
            return "(" + posX + "," + (8 - posY) + ")";
        }
        //throw new UnsupportedOperationException();
    }
}
