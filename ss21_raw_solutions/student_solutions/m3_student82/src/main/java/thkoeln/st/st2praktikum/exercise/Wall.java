package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Wall extends AbstractEntity {

    private Coordinate start;
    private Coordinate end;

    private PlumbLine plumbLine;

    @Id
    private UUID wallId = UUID.randomUUID();

    public Wall(Coordinate pos1, Coordinate pos2) throws WrongWallException {
      this.wallCreator(pos1, pos2);

    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        String[] wallItem = wallString.split("-");

        this.wallCreator(new Coordinate(wallItem[0]), new Coordinate(wallItem[1]));


    }

    public void wallCreator(Coordinate pos1, Coordinate pos2){
        if(pos1.getY() == pos2.getY()){
            this.plumbLine = PlumbLine.HORIZONTAL;
            if(pos1.getX() < pos2.getX()) {
                this.start = pos1;
                this.end = pos2;
            }else{
                this.start = pos2;
                this.end = pos1;
            }
        } else if(pos1.getX() == pos2.getX()) {
            this.plumbLine = PlumbLine.VERTICAL;
            if(pos1.getY() < pos2.getY()) {
                this.start = pos1;
                this.end = pos2;
            } else {
                this.start = pos2;
                this.end = pos1;
            }
        }else{
            throw new WrongWallException(pos1.toString() + pos2.toString());
        }
    }


    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
