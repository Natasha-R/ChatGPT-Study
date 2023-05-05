package thkoeln.st.st2praktikum.exercise;

class Cell{
    int x;
    int y;
    boolean wNO;
    boolean wEA;
    boolean wSO;
    boolean wWE;

    public Cell(int x, int y, boolean wNO, boolean wEA, boolean wSO, boolean wWE){
        this.x = x;
        this.y = y;
        this.wNO = wNO;
        this.wEA = wEA;
        this.wSO = wSO;
        this.wWE = wWE;
    }
}

public class Exercise0 implements Moveable {

    Cell[][] map;
    int currentX = 5;
    int currentY = 3;

    @Override
    public String move(String moveCommandString) {
        if(map == null)
            this.initMap();

        String[] command = moveCommandString.substring(moveCommandString.indexOf("[")+1,moveCommandString.indexOf("]")).split((","));

        currentY = Math.abs(currentY-(map.length-1));

        int tempX = currentX;
        int tempY = currentY;
        switch (command[0]){
            case "no":
                for (int i = currentY; i < currentY + Integer.parseInt(command[1]); i++){
                    if(!map[tempY][tempX].wNO && tempY > 0)
                        tempY--;
                    else
                        break;
                }
                break;
            case "ea":
                for (int j = currentX; j < currentX + Integer.parseInt(command[1]); j++){
                    if(!map[tempY][tempX].wEA && tempX < map[tempY].length-1)
                        tempX++;
                    else
                        break;
                }
                break;
            case "so":
                for (int i = currentY; i > currentY - Integer.parseInt(command[1]); i--){
                    if(!map[tempY][tempX].wSO && tempY < map.length-1)
                        tempY++;
                    else
                        break;
                }
                break;
            case "we":
                for (int j = currentX; j > currentX - Integer.parseInt(command[1]); j--){
                    if(!map[tempY][tempX].wWE && tempX > 0)
                        tempX--;
                    else
                        break;
                }
                break;
        }
        currentX = tempX;
        currentY = Math.abs(tempY-(map.length-1));
        return "(" +  currentX + "," + currentY + ")";
    }

    private void initMap(){
        //Creating the map of Cells
        map = new Cell[8][12];
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                map[i][j] = new Cell(j,i,false,false,false,false);
            }
        }
        //Creating obstacles in map
        for (int i = 5; i < 7; i++){
            map[i][3].wEA = true;
            map[i][4].wWE = true;
        }
        for (int j = 3; j < 9; j++){
            map[4][j].wSO = true;
            map[5][j].wNO = true;
        }
        for (int i = 0; i < 6; i++){
            map[i][5].wEA = true;
            map[i][6].wWE = true;
        }
        for (int j = 1; j < 6; j++){
            map[1][j].wSO = true;
            map[2][j].wNO = true;
        }
    }
}
