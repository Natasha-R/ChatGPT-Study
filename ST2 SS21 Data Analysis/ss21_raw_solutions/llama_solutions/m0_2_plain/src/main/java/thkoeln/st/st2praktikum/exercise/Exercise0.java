package thkoeln.st.st2praktikum.exercise;
public class Exercise0 implements Walkable {
    private int[][] field; // The field, initialized below

    static final String WALL = "Wall";
    private int startX; // Start X-Coordinate
    private int startY; // Start Y-Coordinate
    private int width; // Field Width
    private int height; // Field Height

    public void initField() {
        // Initialize field with 0s and walls
        int numRows = height + 1;
        int numCols = width + 1;

        field = new int[numRows][numCols];
        field[startY][startX] = 0;

        for (int i = 0; i < numRows; i++) {
            field[i][startX] = Wall; // Top Row Wall
            if (i == height && i > startY) {
                field[i][startX] = Wall; // Bottom Row Wall
            }

            for (int j = 0; j < numCols; j++) {
                switch ((char)(j + 'A')) { // Map characters to columns (ABCDEF...)
                    case 'E': // East Wall
                        field[startY][j] = Wall;
                        break;

                    default:
                        break;
                }
            }

            if (i < height && j != startX) {
                field[i][startX+1] = Wall; // Right Column Wall
            }

            if (i >= height && j != startX) {
                field[i][width] = Wall; // Left Column Wall
            }
        }

        for (int i = 0; i <= startY; i++) {
            for (int j = startX+1; j <= width; j++) {
                field[i][j] = 0;
            }
        }
    }

    public Exercise0() {
        startX = 2;
        startY = 2;
        height = 9;
        width = 12;

        initField();
    }

    @Override
    public String walk(String direction) {
        String[] cmd =direction.substring(1).split(",");
        String[] dirArr=cmd[0].toLowerCase().replace("n", "no")
                .replace("w", "we").replace("s", "so").replace("e", "ea").split("r");
        boolean horizontalMove = true;
        if(dirArr[0].equals("no")){
            horizontalMove = false;
        }
        int rows = Math.abs(Integer.parseInt(dirArr[1]))+height;
        int cols = width;

        int newRow = startY;
        int newCol = startX;

        while(rows-- > 0){
            if(!field[newRow][newCol].equals(0)){
                return "";
            }
            else{
                if(horizontalMove){
                    if(cols > 0 && !field[newRow][cols-1].equals(Wall)) fields[newRow][cols++]=0;
                    else if (!fields[newRow][cols].equals(Wall)){
                        newCol += horizontalMove ? 1 : -1;
                        return "" + getNewLocation();
                    }
                }
                else{
                    if(colls < width && !field[newRow][colls+1].equals(Wall)){
                        fields[newRow][cols++]=0;
                    }
                    else if (!fields[newRow][cols].equals(Wall)){
                        newCol += horizontalMove ? colls + 1 : -1;
                        return "" + getNewLocation();
                    }
                }
            }
            newRow++;
            cols = width;
        }

        return null;
    }

    public String getNewLocation(){
        return startX + "," + startY;
    }
}