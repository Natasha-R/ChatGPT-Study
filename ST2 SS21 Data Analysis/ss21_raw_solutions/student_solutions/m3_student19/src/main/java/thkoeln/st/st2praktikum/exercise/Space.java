package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Space {

    @Id
    @Getter
    private UUID id;

    @ElementCollection
    private List<Cell> space = new ArrayList<>();

    @Getter
    private int spaceWidth;

    @Getter
    private int spaceHeight;

    public Space() {}

    public Space(int height, int width) {
        this.id = UUID.randomUUID();
        this.spaceHeight = height;
        this.spaceWidth = width;

        for (int i = 0; i < (this.spaceHeight * this.spaceWidth); i++) {
            this.space.add(new Cell());
        }
    }

    /**
     * Method sets walls in a space from a given string
     * @param wall wall
     */
    public void setWall(Wall wall) {
        int startX = wall.getStart().getX();
        int startY = wall.getStart().getY();
        int endX = wall.getEnd().getX();
        int endY = wall.getEnd().getY();

        if ((endX - startX) != 0) {

            for (int i = startX; i < endX; i++) {
                this.space.get(startY * this.spaceWidth + i).setBorderSouth(true);
                this.space.get((startY - 1) * this.spaceWidth + i).setBorderNorth(true);
            }

        } else if ((endY - startY) != 0) {

            for (int i = startY; i < endY; i++) {
                this.space.get(i * this.spaceWidth + startX).setBorderWest(true);
                this.space.get(i * this.spaceWidth + (startX - 1)).setBorderEast(true);
            }

        }
    }

    public Cell getSpaceCell(int x, int y) {
        return this.space.get(y * this.spaceWidth + x);
    }
}
