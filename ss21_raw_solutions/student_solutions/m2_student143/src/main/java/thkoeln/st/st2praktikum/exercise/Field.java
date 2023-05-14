package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Field extends AbstractEntity {
    @Getter
    private final List<FieldItem> items;
    private final Dimension dimension;

    public Field(int height, int width) {
        this.items = new ArrayList<>();
        items.add(new Wall(new Coordinate(0,0),   new Coordinate(width,0)));
        items.add(new Wall(new Coordinate(0,0),   new Coordinate(0, height)));
        items.add(new Wall(new Coordinate(width,0),  new Coordinate(width,height)));
        items.add(new Wall(new Coordinate(0, height),new Coordinate(width,height)));

        this.dimension = new Dimension(height,width);
    }

    Dimension getDimension(){ return dimension;}

    void add(FieldItem item){
        if(item.getCoordinate().getX() > this.dimension.getWidth() ||
                item.getCoordinate().getY() > this.dimension.getHeight()){
            throw new IllegalArgumentException("the field item must be placed inside the field boundaries");
        }
        items.add(item);
    }

    void remove(FieldItem item){
        items.remove(item);
    }

}
