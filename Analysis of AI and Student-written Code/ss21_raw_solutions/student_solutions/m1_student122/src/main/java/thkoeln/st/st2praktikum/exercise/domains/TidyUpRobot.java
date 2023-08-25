package thkoeln.st.st2praktikum.exercise.domains;


import lombok.*;
import org.modelmapper.internal.Pair;
import thkoeln.st.st2praktikum.exercise.MoveableX;
import thkoeln.st.st2praktikum.exercise.MoveableY;
import thkoeln.st.st2praktikum.exercise.Spawnable;
import thkoeln.st.st2praktikum.exercise.Transportable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TidyUpRobot implements MoveableY, MoveableX, Spawnable, Transportable {
    @Id
    private UUID id = UUID.randomUUID();

    private Room room;
    private String name;
    private Integer locY;
    private Integer locX;

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

    public List<int[]> getNordEdge(){

        int[] firstPoint = {this.getLocX(),this.getLocY()+1};
        int[] secondPoint = {this.getLocX()+1,this.getLocY()+1};
        List<int[]> edge = new ArrayList<>();
        edge.add(firstPoint);
        edge.add(secondPoint);
        return edge;
    }
    public List<int[]> getSouthEdge(){
        int[] firstPoint = {this.getLocX(),this.getLocY()};
        int[] secondPoint = {this.getLocX()+1,this.getLocY()};
        List<int[]> edge = new ArrayList<>();
        edge.add(firstPoint);
        edge.add(secondPoint);
        return edge;
    }
    public List<int[]> getEastEdge(){
        int[] firstPoint = {this.getLocX()+1,this.getLocY()};
        int[] secondPoint = {this.getLocX()+1,this.getLocY()+1};
        List<int[]> edge = new ArrayList<>();
        edge.add(firstPoint);
        edge.add(secondPoint);
        return edge;
    }
    public List<int[]> getWestEdge(){
        int[] firstPoint = {this.getLocX(),this.getLocY()};
        int[] secondPoint = {this.getLocX(),this.getLocY()+1};
        List<int[]> edge = new ArrayList<>();
        edge.add(firstPoint);
        edge.add(secondPoint);
        return edge;
    }

    public String getNordEdgeString(){
        List<int[]> edge = this.getNordEdge();
        return this.getLocY() + 1 + "-(" + edge.get(0)[0] + "," + edge.get(1)[0] + ")";
    }
    public String getSouthEdgeString(){
        List<int[]> edge = this.getSouthEdge();
        return this.getLocY() + "-(" + edge.get(0)[0] + "," + edge.get(1)[0] + ")";
    }
    public String getEastEdgeString(){
        List<int[]> edge = this.getEastEdge();
        return this.getLocX() + 1 + "-(" + edge.get(0)[1] + "," + edge.get(1)[1] + ")";
    }
    public String getWestEdgeString(){
        List<int[]> edge = this.getWestEdge();
        return this.getLocX() + "-(" + edge.get(0)[1] + "," + edge.get(1)[1] + ")";
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
        String[] locArr = connection.getDestinationCoordinate().replace("(","").replace(")","").split(",");
        this.setLocX(Integer.parseInt(locArr[0]));
        this.setLocY(Integer.parseInt(locArr[1]));
    }
}
