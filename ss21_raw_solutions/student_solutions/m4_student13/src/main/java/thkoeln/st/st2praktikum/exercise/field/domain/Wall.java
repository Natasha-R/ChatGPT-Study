package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Wall {

    @Embedded
    private Point start;

    @Embedded
    private Point end;


    public Wall(Point start, Point end) {
        //diagonal check
        if((start.getX() == end.getX() ^ start.getY() == end.getY() )){
            if(start.getY() > end.getY()){
                this.start = end;
                this.end = start;
            } else if(start.getX() > end.getX()){
                this.start = end;
                this.end = start;
            }else {
                this.start = start;
                this.end = end;
            }
        } else {
            throw new WallException("no diagonal walls allowed!");
        }
        
    }
    
    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public boolean isVertically(){
        return this.start.getX() == this.end.getX();
    }

    private String [] determineCoordinates (String wall){
        String [] coordinates = wall
                .substring(1,wall.length()-1)
                .split(",");

        return coordinates;

    }

    public Wall(String wallString) {

        String regex = "^\\(\\d,\\d\\)-\\(\\d,\\d\\)$";
        boolean matches = wallString.matches(regex);

        if(matches) {

            String[] wall = wallString.split("-");
            String[] firstPair = determineCoordinates(wall[0]);
            String[] secondPair = determineCoordinates(wall[1]);

            Integer firstX = Integer.parseInt(firstPair[0]);
            Integer firstY = Integer.parseInt(firstPair[1]);

            Integer secondX = Integer.parseInt(secondPair[0]);
            Integer secondY = Integer.parseInt(secondPair[1]);

            this.start = new Point(firstX, firstY);
            this.end = new Point(secondX, secondY);


        } else {
            throw new WallException("no valid wall");
        }

        //throw new UnsupportedOperationException();
    }


    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        return new Wall(wallString);
    }
    
}








