package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    public int[][] grid = new int[12][9];

    //public int[] startPosition = {8,3};
    public int[] currentPosition = {8,3};


    public int[][] fillGrid (int x, int y) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                grid[i][j] = 1;
            }
        }
        for (int i = 1; i <= 7; i++) {
            grid[4][i] = 0;
        }
        for (int i = 2; i <= 5; i++) {
            grid[6][i] = 0;
        }
        for (int i = 6; i <= 9; i++) {
            grid[i][2] = 0;
        }
        for (int i = 6; i <= 9; i++) {
            grid[i][5] = 0;
        }
        return grid;
    }

    public void showGrid (int[][] grid1) {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j< 9; j++) {
                System.out.println(grid1[i][j]);
            }
        }
    }

    public void tellPosition() {
        System.out.println("My x-position is " + currentPosition[0]);
        System.out.println("My y-position is " + currentPosition[1]);
    }

    @Override
    public String moveTo(String moveCommandString) {

        int[][] room = fillGrid(12, 9);
        //showGrid(room);
        int stepsToDo = Integer.parseInt(moveCommandString.substring(moveCommandString.indexOf(',')+1,moveCommandString.indexOf(']'))); //vielleicht noch mit substring testen
        String richtung = moveCommandString.substring(moveCommandString.indexOf('[')+1,moveCommandString.indexOf(','));
        //tellPosition();
        switch (richtung) {
            case "no":
                for(int i = 0; i < stepsToDo; i++) {
                    if (currentPosition[1] == 8) {
                        return "(" + Integer.toString(currentPosition[0]) + "," + Integer.toString(currentPosition[1]) + ")";
                    }
                    if (room[currentPosition[0]][currentPosition[1] + 1] == 1) {
                        currentPosition[1]++;
                    } else if (room[currentPosition[0]+1][currentPosition[1]+1] == 1) {
                        currentPosition[1]++;
                    } else return "(" + Integer.toString(currentPosition[0]) + "," + Integer.toString(currentPosition[1]) + ")";
                } break;
            case "ea":
                for (int i = 0; i < stepsToDo; i++) {
                    if (currentPosition[0] == 11) {
                        return "(" + Integer.toString(currentPosition[0]) + "," + Integer.toString(currentPosition[1]) + ")";
                    }
                    if (room[currentPosition[0]+1][currentPosition[1]] == 1) {
                        currentPosition[0]++;
                    } else if (room[currentPosition[0]+1][currentPosition[1]+1] == 1) {
                        currentPosition[0]++;
                    } else return "(" + Integer.toString(currentPosition[0]) + "," + Integer.toString(currentPosition[1]) + ")";
                } break;
            case "so":
                if (room[currentPosition[0]][currentPosition[1]] == 1 || room[currentPosition[0]+1][currentPosition[1]] == 1) {
                    for (int i = 0; i < stepsToDo; i++) {
                        if (currentPosition[1] == 0) {
                            return "(" + Integer.toString(currentPosition[0]) + "," + Integer.toString(currentPosition[1]) + ")";
                        }
                        if (room[currentPosition[0]][currentPosition[1] - 1] == 1) {
                            currentPosition[1]--;
                        } else if (room[currentPosition[0]][currentPosition[1] - 1] != 1) {
                            currentPosition[1]--;
                            if (room[currentPosition[0]+1][currentPosition[1]] != 1) {
                                return "(" + Integer.toString(currentPosition[0]) + "," + Integer.toString(currentPosition[1]) + ")";
                            }
                        }
                     }
                } else return "(" + Integer.toString(currentPosition[0]) + "," + Integer.toString(currentPosition[1]) + ")";
                break;
            case "we":
                if (room[currentPosition[0]][currentPosition[1]] == 1 || room[currentPosition[0]][currentPosition[1]+1] == 1) {
                    for (int i = 0; i < stepsToDo; i++) {
                        if (currentPosition[0] == 0) {
                            return "(" + Integer.toString(currentPosition[0]) + "," + Integer.toString(currentPosition[1]) + ")";
                        }
                        if(room[currentPosition[0]-1][currentPosition[1]] == 1) {
                            currentPosition[0]--;
                        } else if (room[currentPosition[0]-1][currentPosition[1]] != 1) {
                            currentPosition[0]--;
                            if(room[currentPosition[0]][currentPosition[1]+1] != 1) {
                                return "(" + Integer.toString(currentPosition[0]) + "," + Integer.toString(currentPosition[1]) + ")";
                            }
                        }
                    }
                } else return "(" + Integer.toString(currentPosition[0]) + "," + Integer.toString(currentPosition[1]) + ")";
                break;
            default: break;
        }
        //tellPosition();
        return "(" + Integer.toString(currentPosition[0]) + "," + Integer.toString(currentPosition[1]) + ")";
    }
}
