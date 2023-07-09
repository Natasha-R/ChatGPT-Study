package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.domainprimitives.core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class TidyUpRobot extends AbstractEntity {
    @Embedded
    Vector2D vector2D;
    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Task> taskList=new ArrayList<>();

    @Getter
    @Setter
    private String name;
    private UUID now_in_room = UUID.randomUUID();
    private int posx ;
    private int posy ;


    public TidyUpRobot(String name) {
        this.name = name;
    }


    public UUID getNow_in_room() {
        return now_in_room;
    }

    public String getCoordinates() {
        return "(" + posx + "," + posy + ")";
    }

    public Vector2D getVector2D() {
        return new Vector2D(posx, posy);
    }

    public boolean clearTaskHistory(){
        taskList=new ArrayList<Task>();
        return true;
    }



    public boolean performTask(Task task , Room room){
        if (room!=null && task!=null)
            taskList.add(task);
        switch (task.getTaskType()) {

            case TRANSPORT:
                return traverse(task.getGridId(), room);

            case NORTH:
                moveNorth(task.getNumberOfSteps(), room);
                return true;
            case EAST:
                moveEast(task.getNumberOfSteps(), room);
                return true;
            case SOUTH:
                moveSouth(task.getNumberOfSteps(), room);
                return true;
            case WEST:
                moveWest(task.getNumberOfSteps(), room);
                return true;
        }
            return false;
    }

    public boolean traverse(UUID roomid, Room room) {

            if (room.getTransportCategory()!=null)
                for (TransportCategory value : room.getTransportCategory())
                    for (Connection val : value.getConnectionList())
                        if (val.getEntranceRoomID().equals(now_in_room) && val.getEntranceCoordinates().equals(getVector2D())) {

                posx = val.getExitCoordinates().getX();
                posy = val.getExitCoordinates().getY();
                now_in_room = roomid;
                return true;
            }

        return false;
    }


    public void en(UUID roomid) {

        now_in_room = roomid;
        posx = 0;
        posy = 0;
        taskList.add(new Task(TaskType.ENTER,roomid));

    }


    public void moveNorth(int moves, Room room) {
        int takenSteps = 0;
        if (moves > room.getGrid()[0].length - 1 - posy) {
            moves = room.getGrid()[0].length - 1 - posy;
        }

        while (takenSteps < moves) {
            if (room.getGrid()[posx][posy + takenSteps] == "Wan1" && room.getGrid()[posx][posy + takenSteps + 1] == "Wan2") {
                posy = posy + takenSteps;

                return;

            }


            if (room.getGrid()[posx][posy + takenSteps] == "Wan2" && room.getGrid()[posx][posy + takenSteps + 1] == "Wan1") {
                posy = posy + takenSteps;
                return;
            } else {
                takenSteps += 1;
            }
        }

        posy = posy + takenSteps;

    }


    public void moveEast(int moves, Room room) {
        int takenSteps = 0;


        if (moves > room.getGrid().length - 1 - posx) {
            moves = room.getGrid().length - 1 - posx;
        }
        while (takenSteps < moves) {
            if (room.getGrid()[posx + takenSteps][posy] == "Wan1" && room.getGrid()[posx + takenSteps + 1][posy] == "Wan2") {
                posx = posx + takenSteps;
                return;

            }
            if (room.getGrid()[posx + takenSteps][posy] == "Wan2" && room.getGrid()[posx + takenSteps + 1][posy] == "Wan1") {
                posx = posx + takenSteps;
                return;

            } else {
                takenSteps += 1;
            }
        }
        posx = posx + takenSteps;

    }


    public void moveSouth(int moves, Room room) {
        int takenSteps = 0;


        if (posy - moves < 0) {
            moves = posy;
        }
        while (takenSteps < moves) {
            if (room.getGrid()[posx][posy - takenSteps] == "Wan1" && room.getGrid()[posx][posy - takenSteps - 1] == "Wan2") {
                posy = posy - takenSteps;
                return;

            }
            if (room.getGrid()[posx][posy - takenSteps] == "Wan2" && room.getGrid()[posx][posy - takenSteps - 1] == "Wan1") {
                posy = posy - takenSteps;
                return;

            } else {
                takenSteps += 1;
            }
        }
        posy = posy - takenSteps;


    }


    public void moveWest(int moves, Room room) {
        int takenSteps = 0;
        if (posx - moves < 0) {
            moves = posx;
        }

        while (takenSteps < moves) {
            if (room.getGrid()[posx - takenSteps][posy] == "Wan1" && room.getGrid()[posx - takenSteps - 1][posy] == "Wan2") {
                posx = posx - takenSteps;
                return;
            }
            if (room.getGrid()[posx - takenSteps][posy] == "Wan2" && room.getGrid()[posx - takenSteps - 1][posy] == "Wan1") {
                posx = posx - takenSteps;
                return;
            } else {
                takenSteps += 1;
            }
        }
        posx = posx - takenSteps;

    }


    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public UUID getRoomId() {
        return now_in_room;
    }

}
