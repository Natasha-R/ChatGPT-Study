package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class Wall extends Tile {

    @Id
    private UUID id = UUID.randomUUID();
    @Embedded
    private Coordinate start;
    @Embedded
    private Coordinate end;

    private void fixStartEndOrder(){
        if( start.getX() == end.getX() ){
            if( end.getY() < start.getY() ){
                Coordinate tempCoordinate = start;
                start = end;
                end = tempCoordinate;
            }
        } else {
            if( start.getY() == end.getY() ){
                if( end.getX() < start.getX() ){
                    Coordinate tempCoordinate = start;
                    start = end;
                    end = tempCoordinate;
                }
            }
        }

    }


    public Wall(Coordinate pos1, Coordinate pos2) {
        this.start = pos1;
        this.end = pos2;
        fixStartEndOrder();

        if( end.getX() > start.getX() && end.getY() > start.getY() ){
            throw new RuntimeException("Walls can not be diagonal");
        }

    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        if( !wallString.matches("^\\(\\d,\\d\\)-\\(\\d,\\d\\)$")){
            throw new RuntimeException("Invalid wall string");
        }
        try {
            String[] wallsString = wallString.split("-");

            start = new Coordinate(wallsString[0]);
            end = new Coordinate(wallsString[1]);
            fixStartEndOrder();
        } catch (Exception exception) {
            throw new RuntimeException("Failed to parse wall string");
        }
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public boolean isConnection() {
        return false;
    }
}
