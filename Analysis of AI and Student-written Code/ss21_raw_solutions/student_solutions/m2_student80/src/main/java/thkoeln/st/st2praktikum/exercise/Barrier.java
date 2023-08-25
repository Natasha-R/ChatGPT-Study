package thkoeln.st.st2praktikum.exercise;

public class Barrier {

    private Coordinate start;
    private Coordinate end;


    public Barrier(Coordinate pos1, Coordinate pos2) {

        if(!pos1.getY().equals(pos2.getY())&&!pos1.getX().equals(pos2.getX())){
            throw new NoDiagonalBarriersAllowed("Es sind keine Diagonalen Barrieren erlaubt");
        }
        if (pos1.getY().equals(pos2.getY())) {
            if (pos1.getX() > pos2.getX()) {
                this.start = pos2;
                this.end = pos1;
            }else {
                this.start = pos1;
                this.end = pos2;
            }
        } else {
            if (pos1.getX().equals(pos2.getX())) {
                if (pos1.getY() > pos2.getY()) {
                    this.start = pos2;
                    this.end = pos1;
                }else {
                    this.start = pos1;
                    this.end = pos2;
                }
            }

        }
    }


    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        var firstChar = Character.getType(barrierString.charAt(1));
        var secondChar = Character.getType(barrierString.charAt(3));
        var thirdChar = Character.getType(barrierString.charAt(7));
        var fourthChar = Character.getType(barrierString.charAt(9));
        Integer compareInt = 1;
        if (barrierString.charAt(0) != '(' ||
                Integer.class.isInstance(firstChar) != Integer.class.isInstance(compareInt) ||
                barrierString.charAt(2) != ',' ||
                Integer.class.isInstance(secondChar) != Integer.class.isInstance(compareInt) ||
                barrierString.charAt(4) != ')'||
                barrierString.charAt(5) != '-'
        ||      barrierString.charAt(6)!= '('
        ||      Integer.class.isInstance(thirdChar) != Integer.class.isInstance(compareInt)
        ||      barrierString.charAt(8) != ','
        ||      Integer.class.isInstance(fourthChar) != Integer.class.isInstance(compareInt)
        ||      barrierString.charAt(10) != ')'
        ) {
            throw new InvalidStringError("Dies ist kein zugelassener String");
        } else {
            barrierString = barrierString.replaceAll("[()]", "");
            String[] barrierCoordinateString = barrierString.split("-");
            for (int i = 0; i < 1; i++) {
                start = new Coordinate("(" + barrierCoordinateString[0] + ")");      //https://stackoverflow.com/questions/27095087/coordinates-string-to-int
                end = new Coordinate("(" + barrierCoordinateString[1] + ")");
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
