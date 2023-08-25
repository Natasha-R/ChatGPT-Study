package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    public Wall(Point pos1, Point pos2) {

        //diagonal check
        if((pos1.getX() == pos2.getX() ^ pos1.getY() == pos2.getY() )){
            if(pos1.getY() > pos2.getY()){
                this.start = pos2;
                this.end = pos1;
            } else if(pos1.getX() > pos2.getX()){
                this.start = pos2;
                this.end = pos1;
            }else {
                this.start = pos1;
                this.end = pos2;
            }
        } else {
            throw new WallException("no diagonal walls allowed!");
        }

//        this.start = pos1;
//        this.end = pos2;
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

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
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

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
