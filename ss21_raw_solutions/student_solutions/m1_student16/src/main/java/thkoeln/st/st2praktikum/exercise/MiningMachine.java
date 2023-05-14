package thkoeln.st.st2praktikum.exercise;

import java.util.List;
import java.util.UUID;

public class MiningMachine implements Movable{
    private final String name;
    private final UUID miningMachineId;
    private UUID fieldId;
    private Integer positionX;
    private Integer positionY;
    private Boolean isPlaced = false;

    public MiningMachine(String name){
        this.name = name;
        this.miningMachineId = UUID.randomUUID();
    }

    public boolean executeCommand(String commandString, List<Field> fields, List<Movable> miningMachines){
        switch (commandString.substring(1,3)){
            case "tr":
                return traverse(miningMachines,fields);
            case "en":
                return enter(miningMachines, UUID.fromString(commandString.substring(4,commandString.length()-1)));
            case "we":
            case "no":
            case "ea":
            case "so":
                return move(commandString.substring(1,3),Integer.parseInt(commandString.substring(4,5)),fields,miningMachines);
            default: throw new IllegalArgumentException();
        }
    }

    public boolean enter(List<Movable> miningMachines, UUID fieldId){
        if(isPlaced) return false;
        for(Movable miningMachine : miningMachines){
            if(miningMachine.isPlaced() && miningMachine.getFieldId().equals(fieldId) && miningMachine.getX().equals(0) && miningMachine.getY().equals(0)) return false;
        }
        this.positionX = 0;
        this.positionY = 0;
        this.fieldId = fieldId;
        this.isPlaced = true;
        return true;
    }

    public boolean move(String direction, Integer steps, List<Field> fields, List<Movable> miningMachines){
        Field targetField = null;
        for(Field field : fields){
            if(field.getFieldId().equals(this.fieldId)){
                targetField = field;
            }
        }
        if(targetField == null) throw new NullPointerException();
        for(int i = 0; i < steps; i++){
            switch (direction) {
                case "no":
                    moveNorth(targetField,miningMachines);
                    break;
                case "so":
                    moveSouth(targetField,miningMachines);
                    break;
                case "we":
                    moveWest(targetField,miningMachines);
                    break;
                case "ea":
                    moveEast(targetField,miningMachines);
                    break;
            }
        }
        return true;
    }

    public void moveNorth(Field targetField, List<Movable> miningMachines){
        boolean moveAllowed = true;
        if (!(this.positionY == targetField.getHeight() - 1)) {
            for (int j = 0; j < targetField.getWalls().size(); j++) {
                if (this.positionX > targetField.getWalls().get(j).getStartX() && this.positionX < targetField.getWalls().get(j).getEndX() && this.positionY == targetField.getWalls().get(j).getStartY() - 1) {
                    moveAllowed = false;
                    break;
                }
            }
            if (moveAllowed && !willHitAnotherMiningMachine("no",miningMachines)) this.positionY++;
        }
    }

    public void moveSouth(Field targetField, List<Movable> miningMachines){
        boolean moveAllowed = true;
        if (!(this.positionY == 0)) {
            for (int j = 0; j < targetField.getWalls().size(); j++) {
                if (this.positionX > targetField.getWalls().get(j).getStartX() && this.positionX < targetField.getWalls().get(j).getEndX() && this.positionY == targetField.getWalls().get(j).getStartY() + 1) {
                    moveAllowed = false;
                    break;
                }
            }
            if (moveAllowed && !willHitAnotherMiningMachine("so",miningMachines)) this.positionY--;
        }
    }

    public void moveWest(Field targetField, List<Movable> miningMachines){
        boolean moveAllowed = true;
        if (!(this.positionX == 0)) {
            for (int j = 0; j < targetField.getWalls().size(); j++) {
                if (this.positionY > targetField.getWalls().get(j).getStartY() && this.positionY < targetField.getWalls().get(j).getEndY() && this.positionX == targetField.getWalls().get(j).getStartX() + 1) {
                    moveAllowed = false;
                    break;
                }
            }
            if (moveAllowed && !willHitAnotherMiningMachine("we",miningMachines)) this.positionX--;
        }
    }

    public void moveEast(Field targetField, List<Movable> miningMachines){
        boolean moveAllowed = true;
        if(!(this.positionX == targetField.getWidth() - 1)){
            for(int j = 0; j < targetField.getWalls().size(); j++){
                if (this.positionY > targetField.getWalls().get(j).getStartY() && this.positionY < targetField.getWalls().get(j).getEndY() && this.positionX == targetField.getWalls().get(j).getStartX() - 1) {
                    moveAllowed = false;
                    break;
                }
            }
            if(moveAllowed && !willHitAnotherMiningMachine("ea",miningMachines)) this.positionX++;
        }
    }



    public Boolean willHitAnotherMiningMachine(String direction, List<Movable> miningMachines){
        Movable miningMachine;
        for (Movable machine : miningMachines) {
            miningMachine = machine;
            if (miningMachine != null && this.fieldId.equals(miningMachine.getFieldId())) {
                switch (direction) {
                    case "no":
                        if (this.positionX.equals(miningMachine.getX()) && this.positionY.equals(miningMachine.getY() - 1))
                            return true;
                        break;
                    case "so":
                        if (this.positionX.equals(miningMachine.getX()) && this.positionY.equals(miningMachine.getY() + 1))
                            return true;
                        break;
                    case "ea":
                        if (this.positionY.equals(miningMachine.getY()) && this.positionX.equals(miningMachine.getX() - 1))
                            return true;
                        break;
                    case "we":
                        if (this.positionY.equals(miningMachine.getY()) && this.positionX.equals(miningMachine.getX() + 1))
                            return true;
                        break;
                }
            }
        }
        return false;
    }

    public boolean traverse(List<Movable> miningMachines,List<Field> fields) {
        if(isPlaced){
            for(Field field : fields){
                if(field.getFieldId().equals(this.fieldId)){
                    for(Connection connection : field.getConnections()){
                        if(positionX.equals(connection.getSourceX()) && positionY.equals(connection.getSourceY())){
                            for(Movable miningMachine : miningMachines){
                                if(miningMachine.isPlaced() && miningMachine.getFieldId().equals(connection.getDestinationFieldID()) && miningMachine.getX().equals(connection.getDestinationX()) && miningMachine.getY().equals(connection.getDestinationY())){
                                    return false;
                                }
                            }
                            positionX = connection.getDestinationX();
                            positionY = connection.getDestinationY();
                            fieldId = connection.getDestinationFieldID();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public UUID getId() {
        return miningMachineId;
    }

    public UUID getFieldId() {
        return fieldId;
    }

    public void setFieldId(UUID id) {
        this.fieldId = id;
    }

    public void setPosition(Integer newX, Integer newY){
        positionX = newX;
        positionY = newY;
    }

    public Integer getX() {
        return positionX;
    }

    public Integer getY() {
        return positionY;
    }
}
