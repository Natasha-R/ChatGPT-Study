package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Cell;
import thkoeln.st.st2praktikum.exercise.field.domain.Obstacle;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Field extends AbstractEntity {
    @Id
    private UUID fieldID;
    private int height;
    private int width;

    @ElementCollection
    public List<Cell> map;

    public Field(int height, int width, UUID fieldID){
        this.setHeight(height);
        this.setWidth(width);
        this.setFieldID(fieldID);
        this.initField();
    }

    private void initField(){
        map = new LinkedList<>();
        for (int i = 0; i < this.getWidth() * this.getHeight(); i++){
            map.add(new Cell(false,false,false,false));
        }
    }

    public void addObstacles(Obstacle obstacle){

        int startY = determineRealY(obstacle.getStart().getY());
        int EndY = determineRealY(obstacle.getEnd().getY());

        for (int j = obstacle.getStart().getX(); j < obstacle.getEnd().getX(); j++){
            this.getMap()[startY][j].setWallSouth(true);
            this.getMap()[startY+1][j].setWallNorth(true);
        }

        for (int i = startY; i < EndY; i++){
            this.getMap()[i][obstacle.getStart().getX()].setWallWest(true);
            this.getMap()[i][obstacle.getEnd().getX()-1].setWallEast(true);
        }
    }

    private int determineRealY(int coordinateY){
        return Math.abs(coordinateY-(getMap().length-1));
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public UUID getFieldID() {
        return fieldID;
    }

    public void setFieldID(UUID fieldID) {
        this.fieldID = fieldID;
    }

    public Cell[][] getMap(){
        Cell[][] temporaryMap = new Cell[this.getHeight()][this.getWidth()];
        int index = 0;
        for (int i = 0; i < this.getHeight(); i++){
            for (int j = 0; j < this.getWidth(); j++){
                temporaryMap[i][j] = map.get(index++);
            }
        }
        return temporaryMap;
    }
}
