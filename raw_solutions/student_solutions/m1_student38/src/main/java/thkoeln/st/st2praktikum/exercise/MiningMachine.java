package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

class MiningMachine implements IMachine{
    private String name;
    int posX;
    int posY;
    UUID fieldID;

    public MiningMachine(String name){
        this.name = name;
        this.posX = startX;
        this.posY = startY;
        this.fieldID = FieldID;
    }

    private int tempX;
    private int tempY;

    @Override
    public void move(String[] command, Field currentField) {
        tempX = posX;
        tempY = determineRealY(posY,currentField);
        switch (command[0]){
            case "no":
                moveUp(Integer.parseInt(command[1]),tempY,currentField);
                break;
            case "so":
                moveDown(Integer.parseInt(command[1]),tempY,currentField);
                break;
            case "ea":
                moveRight(Integer.parseInt(command[1]),currentField);
                break;
            case "we":
                moveLeft(Integer.parseInt(command[1]),currentField);
                break;
        }
        currentField.map[posX][determineRealY(posY,currentField)].busy = false;

        posX = tempX;
        posY = determineRealY(tempY,currentField);
    }

    private int determineRealY(int coordY,Field field){
        return Math.abs(coordY-(field.map.length-1));
    }

    void moveUp(int steps, int realY, Field currentField){
        for (int i = realY; i < realY + steps; i++){
            if(!currentField.map[tempY][tempX].wNO && tempY > 0 && !currentField.map[tempY][tempX].busy)
                tempY--;
            else
                break;
        }
    }

    void moveDown(int steps, int realY, Field currentField) {
        for (int i = realY; i > realY - steps; i--){
            if(!currentField.map[tempY][tempX].wSO && tempY < currentField.map.length-1 && !currentField.map[tempY][tempX].busy)
                tempY++;
            else
                break;
        }
    }

    void moveRight(int steps, Field currentField) {
        for (int j = posX; j < posX + steps; j++){
            if(!currentField.map[tempY][tempX].wEA && tempX < currentField.map[tempY].length-1 && !currentField.map[tempY][tempX].busy)
                tempX++;
            else
                break;
        }
    }

    void moveLeft(int steps,Field currentField) {
        for (int j = posX; j > posX - steps; j--){
            if(!currentField.map[tempY][tempX].wWE && tempX > 0 && !currentField.map[tempY][tempX].busy)
                tempX--;
            else
                break;
        }
    }

    @Override
    public String getCoordinates() {
        return "(" + this.posX + "," + this.posY + ")";
    }
}