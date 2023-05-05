package thkoeln.st.st2praktikum.exercise;

public class Matrix {

    private final Boolean[][] matrix;
    private final int xLen;
    private final int yLen;

    private Matrix(Boolean[][] matrix, int xLen, int yLen) {
        this.matrix = matrix;
        this.xLen = xLen;
        this.yLen = yLen;
    }

    public static Matrix createMatrix() {
        int xLen = 13;
        int yLen = 10;
        Boolean[][] matrix = new Boolean[xLen][yLen];
        for (int i = 0; i < xLen; i++) {
            for (int j = 0; j < yLen; j++) {
                matrix[i][j] = false;
            }
        }
            matrix[4][1] = true;
            matrix[4][2] = true;
            matrix[4][3] = true;
            matrix[4][4] = true;
            matrix[4][5] = true;
            matrix[4][6] = true;
            matrix[4][7] = true;

            matrix[6][2] = true;
            matrix[7][2] = true;
            matrix[8][2] = true;
            matrix[9][2] = true;

            matrix[6][2] = true;
            matrix[6][3] = true;
            matrix[6][4] = true;
            matrix[6][5] = true;

            matrix[6][5] = true;
            matrix[7][5] = true;
            matrix[8][5] = true;
            matrix[9][5] = true;
        return new Matrix(matrix, xLen, yLen);
    }

    public Boolean validatePosition(Position newPosition, Orientation orientation) {
        if (!validateOutOfBounds(newPosition))
            return false;
        if (!validateMovement(newPosition, orientation))
            return false;
        return true;
    }

    private Boolean validateOutOfBounds(Position newPosition) {
        if (newPosition.getXPos() < 0)
            return false;
        if (newPosition.getXPos() >= xLen - 1)     // minus 1 because of tile definition
            return false;
        if (newPosition.getYPos() < 0)
            return false;
        if (newPosition.getYPos() >= yLen - 1)
            return false;
        return true;
    }

    private Boolean validateMovement(Position newPosition, Orientation orientation) {
        switch (orientation) {
            case NO: return validateNorthMovement(newPosition);
            case EA: return validateEastMovement(newPosition);
            case SO: return validateSouthMovement(newPosition);
            case WE: return validateWestMovement(newPosition);
        }
        return false;
    }

    private Boolean validateNorthMovement(Position newPosition) {
//        System.out.println("x: " + newPosition.getXPos() + ", y: " + newPosition.getYPos() + ", " + matrix[newPosition.getXPos()][newPosition.getYPos()]);
//        System.out.println("x: " + (newPosition.getXPos() + 1) + ", y: " + newPosition.getYPos() + ", " + matrix[newPosition.getXPos() + 1][newPosition.getYPos()]);

        if (matrix[newPosition.getXPos()][newPosition.getYPos()]) {
            if (matrix[newPosition.getXPos() + 1][newPosition.getYPos()]) {
                return false;
            }
        }
        return true;
    }

    private Boolean validateEastMovement(Position newPosition) {
        if (matrix[newPosition.getXPos()][newPosition.getYPos()]) {
            if (matrix[newPosition.getXPos()][newPosition.getYPos() + 1]) {
                return false;
            }
        }
        return true;
    }

    private Boolean validateSouthMovement(Position newPosition) {
        if (matrix[newPosition.getXPos()][newPosition.getYPos() + 1]) {
            if (matrix[newPosition.getXPos() + 1][newPosition.getYPos() + 1]) {
                return false;
            }
        }
        return true;
    }

    private Boolean validateWestMovement(Position newPosition) {
        if (matrix[newPosition.getXPos() + 1][newPosition.getYPos()]) {
            if (matrix[newPosition.getXPos() + 1][newPosition.getYPos() + 1]) {
                return false;
            }
        }
        return true;
    }

    public String MatrixToString() {
        String output = "";
        for (int j = yLen - 1; j >= 0; j--) {
            for (int i = 0; i < xLen; i++) {
                output += (matrix[i][j] ? "1" : "0");
                if (i + 1 >= xLen) {
                    output += "\n";
                }
            }
        }
        return output;
    }
}
