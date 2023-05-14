package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class TidyUpRobot implements MoveableY, MoveableX, Spawnable, Transportable {
    @Id
    private UUID id = UUID.randomUUID();

    @OneToOne
    private Room room;

    private String name;
    private Integer locY;
    private Integer locX;

    @ElementCollection
    private List<Task> tasks = new ArrayList<>();

    public String getCoordinates() {
        return "("+locX+","+locY+")";
    }

    public TidyUpRobot(String name) {
        this.name = name;
    }

    public void start(){
        this.locY = 0;
        this.locX = 0;
    }
    public Point getPoint(){
        return new Point(getLocX(),getLocY());
    }
    public UUID getRoomId(){
        return this.room.getId();
    }

    public List<Point> getNordEdge(){

        Point firstPoint = new Point(this.getLocX(),this.getLocY()+1);
        Point secondPoint = new Point(this.getLocX()+1,this.getLocY()+1);
        List<Point> edge = new ArrayList<>();
        edge.add(firstPoint);
        edge.add(secondPoint);
        System.out.println("NORD EDGE "+firstPoint+","+secondPoint);
        return edge;
    }
    public List<Point> getSouthEdge(){
        Point firstPoint = new Point(this.getLocX(),this.getLocY());
        Point secondPoint = new Point(this.getLocX()+1,this.getLocY());
        List<Point> edge = new ArrayList<>();
        edge.add(firstPoint);
        edge.add(secondPoint);
        return edge;
    }
    public List<Point> getEastEdge(){
        Point firstPoint = new Point(this.getLocX()+1,this.getLocY());
        Point secondPoint = new Point(this.getLocX()+1,this.getLocY()+1);
        List<Point> edge = new ArrayList<>();
        edge.add(firstPoint);
        edge.add(secondPoint);
        return edge;
    }
    public List<Point> getWestEdge(){
        Point firstPoint = new Point(this.getLocX(),this.getLocY());
        Point secondPoint = new Point(this.getLocX(),this.getLocY()+1);
        List<Point> edge = new ArrayList<>();
        edge.add(firstPoint);
        edge.add(secondPoint);
        return edge;
    }

    public void addTaskToTaskList(Task task){
        tasks.add(task);
    }

    @Override
    public void moveX(Integer steps) {
        this.setLocX(getLocX()+steps);
    }

    @Override
    public void moveY(Integer steps) {
        this.setLocY(getLocY()+steps);
    }

    @Override
    public void spawn(Room room) {
        this.room = room;
        this.start();
    }

    @Override
    public void transport(Connection connection, Room room) {
        this.room = room;
        Point connectionPoint = connection.getDestinationCoordinate();
        this.setLocX(connectionPoint.getX());
        this.setLocY(connectionPoint.getY());
    }
}
