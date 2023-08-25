package thkoeln.st.st2praktikum.exercise;

public class Wall {

    private Coordinate start;
    private Coordinate end;


    public Wall(Coordinate pos1, Coordinate pos2) {

        if (!pos1.getY().equals(pos2.getY()) && !pos1.getX().equals(pos2.getX())) {
            throw new NoDiagonalBarriersException("Es sind keine diagonalen Wandkoordinaten" +
                    "erlaubt ");
        }


        if (pos1.getY().equals(pos2.getY())) {
            if (pos1.getX() > pos2.getX()) {
                this.start = pos2;
                this.end = pos1;
            } else {
                this.start = pos1;
                this.end = pos2;
            }
        } else {
            if (pos1.getX().equals(pos2.getX())) {
                if (pos1.getY() > pos2.getY()) {
                    this.start = pos2;
                    this.end = pos1;

                } else {
                    this.start = pos1;
                    this.end = pos2;

                }
            }



        }
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        int firstNumber = Character.getNumericValue(wallString.charAt(1));
        int secondNumber = Character.getNumericValue(wallString.charAt(3));
        int thirdNumber = Character.getNumericValue(wallString.charAt(7));
        int fourthNumber = Character.getNumericValue(wallString.charAt(9));
        Integer.class.isInstance(firstNumber);
        int comparenumber = 0;

        if(wallString.charAt(0) != '('
                || Integer.class.isInstance(firstNumber) != Integer.class.isInstance(comparenumber)
                || wallString.charAt(2) != ','
                || Integer.class.isInstance(secondNumber) != Integer.class.isInstance(comparenumber)
                || wallString.charAt(4) != ')'
                || wallString.charAt(5) != '-'
                ||      wallString.charAt(6) != '('
                || Integer.class.isInstance(thirdNumber) != Integer.class.isInstance(comparenumber)
                || wallString.charAt(8) != ','
                || Integer.class.isInstance(fourthNumber) != Integer.class.isInstance(comparenumber)
                || wallString.charAt(10) != ')')
        {
            throw new InvalidStringError("Dies ist kein erlaubter String");
        } else {

        wallString = wallString.replaceAll("[()]", "");
        String[] wallcoordinatestring = wallString.split("-");
        for(int i=0 ; i<1; i++) {
            start = new Coordinate("(" + wallcoordinatestring[0] + ")");
            end = new Coordinate("(" + wallcoordinatestring[1] + ")");
            }
        }
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
