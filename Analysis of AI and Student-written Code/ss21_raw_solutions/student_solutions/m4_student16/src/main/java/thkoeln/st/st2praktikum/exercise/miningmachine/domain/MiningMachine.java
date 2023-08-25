package thkoeln.st.st2praktikum.exercise.miningmachine.domain;


import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class MiningMachine implements Movable{
    public String name;
    @Id
    private UUID miningMachineId = UUID.randomUUID();
    private UUID fieldId;
    @Embedded
    private Coordinate coordinate;
    private Boolean isPlaced;
    @ElementCollection
    List<Task> tasks = new ArrayList<>();

    public MiningMachine(String name){
        this.name = name;
        this.isPlaced = false;
    }

    public MiningMachine() {
        this.isPlaced = false;
    }

    public boolean executeCommand(Task task, List<Field> fields, List<Movable> miningMachines, List<TransportSystem> transportSystems){
        addTask(task);
        switch (task.getTaskType()){
            case ENTER:
                return enter(miningMachines, task.getGridId());
            case TRANSPORT:
                return transport(miningMachines,fields, transportSystems);
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return move(task.getTaskType(),task.getNumberOfSteps(),fields,miningMachines);
            default: throw new IllegalArgumentException();
        }
    }

    public boolean enter(List<Movable> miningMachines, UUID fieldId){
        if(isPlaced) return false;
        for(Movable miningMachine : miningMachines){
            if(miningMachine.isPlaced() && miningMachine.getFieldId().equals(fieldId) && miningMachine.getCoordinate().getX().equals(0) && miningMachine.getCoordinate().getY().equals(0)) return false;
        }
        this.coordinate = new Coordinate(0,0);
        this.fieldId = fieldId;
        this.isPlaced = true;
        return true;
    }

    public boolean transport(List<Movable> miningMachines, List<Field> fields, List<TransportSystem> transportSystems) {
        if(isPlaced){
            Field field = null;
            for(Field targetField : fields){
                if(targetField.getFieldId().equals(fieldId)) field = targetField;
            }
            if(field == null) throw new NullPointerException();
            for(TransportSystem transportSystem : transportSystems){
                for(Connection connection : transportSystem.getConnections()){
                    if(connection.getSourceFieldId().equals(field.getFieldId()) && coordinate.equals(connection.getSourceCoordinate())){
                        for(Movable machine : miningMachines){
                            if(machine.isBlockingCoordinate(connection.getDestinationFieldID(), connection.getDestinationCoordinate()))
                                return false;
                        }
                        coordinate = new Coordinate(connection.getDestinationCoordinate().getX(),connection.getDestinationCoordinate().getY());
                        fieldId = connection.getDestinationFieldID();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean move(TaskType taskType, Integer steps, List<Field> fields, List<Movable> miningMachines){
        Field field = null;
        for(Field targetField : fields){
            if(targetField.getFieldId().equals(fieldId)) field = targetField;
        }
        if(field == null) throw new NullPointerException();
        for(int i = 0; i < steps; i++){
            switch (taskType) {
                case NORTH:
                    moveNorth(field,miningMachines);
                    break;
                case SOUTH:
                    moveSouth(field,miningMachines);
                    break;
                case WEST:
                    moveWest(field,miningMachines);
                    break;
                case EAST:
                    moveEast(field,miningMachines);
                    break;
            }
        }
        return true;
    }

    private void moveNorth(Field targetField, List<Movable> miningMachines){
        boolean moveAllowed = true;
        if (coordinate.getY() != targetField.getHeight() - 1) {
            for (int j = 0; j < targetField.getWalls().size(); j++) {
                if (targetField.getWalls().get(j).isNorthOf(coordinate)) {
                    moveAllowed = false;
                    break;
                }
            }
            if (moveAllowed && willNotHitAnotherMiningMachine(TaskType.NORTH,miningMachines)) coordinate = new Coordinate(coordinate.getX(), coordinate.getY() + 1);
        }
    }

    private void moveSouth(Field targetField, List<Movable> miningMachines){
        boolean moveAllowed = true;
        if (coordinate.getY() != 0) {
            for (int j = 0; j < targetField.getWalls().size(); j++) {
                if (targetField.getWalls().get(j).isSouthOf(coordinate)) {
                    moveAllowed = false;
                    break;
                }
            }
            if (moveAllowed && willNotHitAnotherMiningMachine(TaskType.SOUTH,miningMachines)) coordinate = new Coordinate(coordinate.getX(),coordinate.getY() - 1);
        }
    }

    private void moveWest(Field targetField, List<Movable> miningMachines){
        boolean moveAllowed = true;
        if (coordinate.getX() != 0) {
            for (int j = 0; j < targetField.getWalls().size(); j++) {
                if (targetField.getWalls().get(j).isWestOf(coordinate)) {
                    moveAllowed = false;
                    break;
                }
            }
            if (moveAllowed && willNotHitAnotherMiningMachine(TaskType.WEST,miningMachines)) coordinate = new Coordinate(coordinate.getX() - 1, coordinate.getY());
        }
    }

    private void moveEast(Field targetField, List<Movable> miningMachines){
        boolean moveAllowed = true;
        if(coordinate.getX() != targetField.getWidth() - 1){
            for(int j = 0; j < targetField.getWalls().size(); j++){
                if (targetField.getWalls().get(j).isEastOf(coordinate)) {
                    moveAllowed = false;
                    break;
                }
            }
            if(moveAllowed && willNotHitAnotherMiningMachine(TaskType.EAST,miningMachines)) coordinate = new Coordinate(coordinate.getX() + 1, coordinate.getY());
        }
    }

    private boolean willNotHitAnotherMiningMachine(TaskType taskType, List<Movable> miningMachines){
        for (Movable machine : miningMachines) {
            if (fieldId.equals(machine.getFieldId())) {
                switch (taskType) {
                    case NORTH:
                        if (isSouthOf(machine)) return false;
                        break;
                    case SOUTH:
                        if (isNorthOf(machine)) return false;
                        break;
                    case EAST:
                        if (isWestOf(machine)) return false;
                        break;
                    case WEST:
                        if (isEastOf(machine)) return false;
                        break;
                }
            }
        }
        return true;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void deleteAllTasks(){
        tasks.clear();
    }

    public boolean isBlockingCoordinate(UUID fieldId, Coordinate coordinate){
        return isPlaced && this.fieldId.equals(fieldId) && this.coordinate.equals(coordinate);
    }

    public boolean isNorthOf(Movable miningMachine){
        return coordinate.getX().equals(miningMachine.getCoordinate().getX()) && coordinate.getY().equals(miningMachine.getCoordinate().getY() + 1);
    }

    public boolean isSouthOf(Movable miningMachine){
        return coordinate.getX().equals(miningMachine.getCoordinate().getX()) && coordinate.getY().equals(miningMachine.getCoordinate().getY() - 1);
    }

    public boolean isWestOf(Movable miningMachine){
        return coordinate.getY().equals(miningMachine.getCoordinate().getY()) && coordinate.getX().equals(miningMachine.getCoordinate().getX() - 1);
    }

    public boolean isEastOf(Movable miningMachine){
        return coordinate.getY().equals(miningMachine.getCoordinate().getY()) && coordinate.getX().equals(miningMachine.getCoordinate().getX() + 1);
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

    public Coordinate getCoordinate(){ return  coordinate; }

}
