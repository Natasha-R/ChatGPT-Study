package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;



public class Wall {

    private Vector2D start;
    private Vector2D end;


    public Wall(Vector2D start, Vector2D end) {

        if (!start.getX().equals(end.getX()) && !start.getY().equals(end.getY())) {
            throw new IllegalArgumentException("only vertical or horizontal walls allowed");
        }

        if (end.getX()+end.getY() < start.getX()+start.getY()) {
            this.start = end;
            this.end = start;
        }
        else {
            this.start = start;
            this.end = end;
        }
        
    }
    
    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        if (!wallString.substring(1, 2).equals(wallString.substring(7, 8)) && !wallString.substring(3, 4).equals(wallString.substring(9, 10))) {
            throw new IllegalArgumentException("only vertical or horizontal walls allowed");
        }
        else if (!wallString.substring(5, 6).equals("-")) {
            throw new IllegalArgumentException("not the right format");
        }
        //else {
            //this.start = new Vector2D(Integer.parseInt(wallString.substring(1, 2)), Integer.parseInt(wallString.substring(3, 4)));
            //this.end = new Vector2D(Integer.parseInt(wallString.substring(7, 8)), Integer.parseInt(wallString.substring(9, 10)));
        //}

        return new Wall(new Vector2D(Integer.parseInt(wallString.substring(1, 2)), Integer.parseInt(wallString.substring(3, 4))), new Vector2D(Integer.parseInt(wallString.substring(7, 8)), Integer.parseInt(wallString.substring(9, 10))));
    }
    
}
