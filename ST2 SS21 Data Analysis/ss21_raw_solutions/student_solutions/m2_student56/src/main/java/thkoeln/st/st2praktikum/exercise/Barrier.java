package thkoeln.st.st2praktikum.exercise;


public class Barrier {

    private Coordinate start;
    private Coordinate end;



    public Barrier(Coordinate pos1, Coordinate pos2) {
        if (!pos1.getX().equals(pos2.getX()) && !pos1.getY().equals(pos2.getY())){
            throw new RuntimeException("No diagonale barriers allowed.");
        }
        if (pos1.getX() + pos1.getY() > pos2.getX() + pos2.getY()){
            this.start = pos2;
            this.end = pos1;
        }else {
            this.start = pos1;
            this.end = pos2;
        }
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        if (barrierString.length() != 11){
            throw new RuntimeException("Barrierstring has not the requierd length.");
        }
        if (barrierString.charAt(5) != '-'){
            throw new RuntimeException("No valid Barrierstring.");
        }



        this.start = new Coordinate(barrierString.substring(0,5));
        this.end   = new Coordinate(barrierString.substring(6,11));
    }


    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
