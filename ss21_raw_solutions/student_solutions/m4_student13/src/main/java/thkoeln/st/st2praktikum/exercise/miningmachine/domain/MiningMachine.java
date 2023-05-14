package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.field.application.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.Room;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.ConnectionRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MiningMachine {

    private String name;

    @Id
    private UUID uuid;

    @OneToOne
    private Room room;

    private Point point;

    private UUID fieldId;

    public MiningMachine(String name) {
        this.name = name;
        uuid = UUID.randomUUID();
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom(){
        return this.room == null ? null : this.room;
    }

    public UUID getId() {
        return uuid;
    }

    public Point getPoint() {
        return point;
    }

    public UUID getFieldId(){
        return this.fieldId;
    }

    public String getName() {
        return name;
    }

    private String[] determineCoordinates(String instruction) {
        String[] coordinates = instruction
                .substring(1, instruction.length() - 1)
                .split(",");

        return coordinates;
    }


    public Point fetchPoint() {
        //return new Point(this.room.getXPosition(), this.room.getYPosition());
        //return new Point(this.room.getPoint().getX(), this.room.getPoint().getY());
        return new Point(this.room.getPoint().getX(),this.room.getPoint().getY());
    }

    @ElementCollection
    List <Task> historyOfTasks = new ArrayList<>();

    public void cleanHistoryTasks (){
        historyOfTasks.clear();
    }
    public List<Task> getHistoryOfTasks() {
        return historyOfTasks;
    }

    //    public boolean execute(Task task, HashMap<UUID,Field>fieldHashMap, HashMap<UUID,Connection>connectionHashMap){
//        boolean output;
//
//        if(task.getTaskType() != TaskType.ENTER && task.getTaskType() != TaskType.TRANSPORT){
//            output = this.move(task.getTaskType(),task.getNumberOfSteps());
//        }else {
//            UUID fieldID = task.getGridId();
//            Field field = fieldHashMap.get(fieldID);
//            if(task.getTaskType() == TaskType.ENTER){
//                output = this.placeOnField(field);
//            }else{
//                output = this.transport(field, connectionHashMap);
//            }
//        }
//        return output;
//    }
    public boolean execute(Task task, FieldRepository fieldRepository, ConnectionRepository connectionRepository) {
        boolean output;

        historyOfTasks.add(task);

        if (task.getTaskType() != TaskType.ENTER && task.getTaskType() != TaskType.TRANSPORT) {
            output = this.move(task.getTaskType(), task.getNumberOfSteps());

        } else {
            UUID fieldID = task.getGridId();
            Field field = fieldRepository.findById(fieldID).get();

            if (task.getTaskType() == TaskType.ENTER) {
                output = this.placeOnField(field);
            } else {
                output = this.transport(field, connectionRepository);
            }
        }
        if(output == true){
            this.point = fetchPoint();
        }
        return output;
    }


//    public boolean execute(Task task, HashMap<UUID,Field>fieldHashMap, HashMap<UUID,TransportSystem>transportSystemHashMap){
//        boolean output;
//
//        if(task.getTaskType() != TaskType.ENTER && task.getTaskType() != TaskType.TRANSPORT){
//            output = this.move(task.getTaskType(),task.getNumberOfSteps());
//        }else {
//            UUID fieldID = task.getGridId();
//            Field field = fieldHashMap.get(fieldID);
//            if(task.getTaskType() == TaskType.ENTER){
//                output = this.placeOnField(field);
//            }else{
//                output = this.transport(field, transportSystemHashMap);
//            }
//        }
//        return output;
//    }


    private boolean move(TaskType type, int steps) {

        switch (type) {
            case NORTH:
                this.room = this.room.moveNorth(steps);
                this.room.setBlocked(true);
                return true;
            case SOUTH:
                this.room = this.room.moveSouth(steps);
                this.room.setBlocked(true);
                return true;
            case EAST:
                this.room = this.room.moveEast(steps);
                this.room.setBlocked(true);
                return true;
            case WEST:
                this.room = this.room.moveWest(steps);
                this.room.setBlocked(true);
                return true;
        }
        return false;
    }


    private boolean placeOnField(Field field) {

        if (!field.getFieldWithRooms()[0][0].isBlocked()) {
            this.room = field.getFieldWithRooms()[0][0];
            this.room.setBlocked(true);
            this.fieldId = field.getId();
            return true;
        } else {
            return false;
        }
    }
//
//    private boolean transport(Field field, HashMap<UUID, Connection> connectionHashMap) {
//
//        for (Map.Entry<UUID, Connection> entry : connectionHashMap.entrySet()) {
//
//            //mit == überprüfen, ob beide den selben Raum referenzieren
//            if (this.room == entry.getValue().getEntryRoom()) {
//                if (field.getUuid().equals(entry.getValue().getExitRoom().getField().getUuid())) {
//                    this.room = entry.getValue().getExitRoom();
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


//    private boolean transport(Field field, ConnectionRepository connectionRepository) {
//
//        for (Map.Entry<UUID, Connection> entry : connectionHashMap.entrySet()) {
//
//            //mit == überprüfen, ob beide den selben Raum referenzieren
//            if (this.room == entry.getValue().getEntryRoom()) {
//                if (field.getUuid().equals(entry.getValue().getExitRoom().getField().getUuid())) {
//                    this.room = entry.getValue().getExitRoom();
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    private boolean transport (Field field, ConnectionRepository connectionRepository){

        for (Connection connection : connectionRepository.findAll()) {
            if (this.room == connection.getEntryRoom()) {
                if (field.getUuid().equals(connection.getExitRoom().getField().getUuid())) {
                    this.room = connection.getExitRoom();
                    this.fieldId = field.getId();
                    return true;
                }
            }
        }
        return false;
    }


//    private boolean transport(Field field, HashMap<UUID,TransportSystem> transportSystemHashMap) {
//
//    for (Map.Entry<UUID,TransportSystem> entry : transportSystemHashMap.entrySet()) {
//        for(Map.Entry<UUID,Connection> e : entry.getValue().getUuidConnectionHashMap().entrySet()){
//            if (this.room == e.getValue().getEntryRoom()) {
//                if (field.getUuid().equals(e.getValue().getExitRoom().getField().getUuid())) {
//                    this.room = e.getValue().getExitRoom();
//                    return true;
//                }
//            }
//        }
//    }
//        return false;
//
//         }


}