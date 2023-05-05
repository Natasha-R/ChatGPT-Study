package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Field extends AbstractEntity {
    private List<Placable> placables;
    private int height;
    private int width;

    public Field(int height, int width) {
        this.placables = new ArrayList<>();
        placables.add(new Wall(new Rectangle(new Coordinate(0,0),new Dimension(0,width))));
        placables.add(new Wall(new Rectangle(new Coordinate(0,0),new Dimension(height,0))));
        placables.add(new Wall(new Rectangle(new Coordinate(width,0),new Dimension(height,0))));
        placables.add(new Wall(new Rectangle(new Coordinate(0,height),new Dimension(0,width))));
        this.height = height;
        this.width = width;
    }

    void add(Placable placable){
        placables.add(placable);
    }

    void remove(Placable placable){
        placables.remove(placable);
    }

    List<Placable> getItems() {
        return placables;
    }
}