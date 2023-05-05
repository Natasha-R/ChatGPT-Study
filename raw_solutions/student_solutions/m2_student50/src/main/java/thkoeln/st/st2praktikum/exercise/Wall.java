package thkoeln.st.st2praktikum.exercise;

public class Wall {

    private Coordinate start;
    private Coordinate end;


    public Wall(Coordinate pos1, Coordinate pos2) {

        if (pos1.getX().equals(pos2.getX()) || pos1.getY().equals(pos2.getY())) {
            if (pos1.getX() + pos1.getY() > pos2.getX() + pos2.getY()) {
                this.start = pos2;
                this.end = pos1;
            } else {
                this.start = pos1;
                this.end = pos2;
            }
        } else throw new RuntimeException("It's not a wall");

       /* int x1 = pos1.getX();
        int x2 = pos2.getX();
        int y1 = pos1.getY();
        int y2 = pos2.getY();

        if(x1 == x2){
            this.start = pos1;
            this.end = pos2;
            if(y1 > y2){
                this.start = pos2;
                this.end = pos1;
            }
        }
        else if(y1 == y2){
            this.start = pos1;
            this.end = pos2;
            if(x1 > x2){
                this.start= pos2;
                this.end = pos1;
            }
        }
        else throw new RuntimeException("It's not a wall");
*/
    }

    private boolean barrierStringChecking(String wallString) {

        if (wallString.length() >= 11) {
            if (wallString.replaceAll("[0-9]", "").equals("(,)-(,)") && wallString.replaceAll("[^0-9]", "").length() >= 4) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {

        Integer x1 = Integer.parseInt(String.valueOf(wallString.charAt(1)));
        Integer x2 = Integer.parseInt(String.valueOf(wallString.charAt(7)));
        Integer y1 = Integer.parseInt(String.valueOf(wallString.charAt(3)));
        Integer y2 = Integer.parseInt(String.valueOf(wallString.charAt(9)));

        if (barrierStringChecking(wallString)) {
            if (x1.equals(x2) || y1.equals(y2)) {
                if ((x1 + y1 < x2 + y2)) {
                    this.start = new Coordinate(x1, y1);
                    this.end = new Coordinate(x2, y2);
                } else {
                    this.start = new Coordinate(x2, y2);
                    this.end = new Coordinate(x1, y1);
                }
            } else throw new RuntimeException();
        } else throw new RuntimeException("It's not a wall");
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

}
