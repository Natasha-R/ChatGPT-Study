package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Field extends AbstractEntity {
    @Id
    private UUID fieldID;
    private int height;
    private int width;
    private transient Cell[][] map;

    public Field(int height, int width, UUID fieldID){
        this.setHeight(height);
        this.setWidth(width);
        this.setFieldID(fieldID);
        this.initField();
    }

    private void initField(){
        Cell[][] temporaryMap = new Cell[this.getHeight()][this.getWidth()];
        for (int i = 0; i < this.getHeight(); i++){
            for (int j = 0; j < this.getWidth(); j++){
                temporaryMap[i][j] = new Cell(false,false,false,false);
            }
        }
        this.setMap(temporaryMap);
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
        return map;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
    }
}
