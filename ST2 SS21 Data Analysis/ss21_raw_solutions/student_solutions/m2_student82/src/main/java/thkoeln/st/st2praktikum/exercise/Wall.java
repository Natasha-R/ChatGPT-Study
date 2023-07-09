package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.WrongWallException;

public class Wall {

    private Coordinate start;
    private Coordinate end;


    public Wall(Coordinate pos1, Coordinate pos2) throws WrongWallException {
        if(pos1.getY() == pos2.getY()){
            if(pos1.getX() < pos2.getX()) {
                this.start = pos1;
                this.end = pos2;
            }else{
                this.start = pos2;
                this.end = pos1;
            }
        } else if(pos1.getX() == pos2.getX()) {
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

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        String[] wallItem = wallString.split("-");
        System.out.println(wallItem[0]);
        System.out.println(wallItem[1]);


        this.start = new Coordinate(wallItem[0]);
        this.end = new Coordinate(wallItem[1]);


    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
