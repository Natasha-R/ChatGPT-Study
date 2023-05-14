package thkoeln.st.st2praktikum.exercise;

public class Wall {

    private Coordinate start;
    private Coordinate end;

    public Wall(Coordinate pos1, Coordinate pos2) {
        checkFormat(pos1, pos2);
    }


    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {



        Coordinate startFromString;
        Coordinate endFromString;

        if(!wallString.contains("-")){
            throw new WrongFormatException("Wrong format for wallString, try (x1,y1)-(x2,y2)");
        }

        String segments[] = wallString.split("-");

        if(segments.length != 2){
            throw new WrongFormatException("Wrong format for wallString, try (x1,y1)-(x2,y2)");
        }

        try{
            startFromString = new Coordinate(segments[0]);
            endFromString = new Coordinate(segments[1]);

        }catch( Exception e){
            throw new WrongFormatException("Wrong format for wallString, try (x1,y1)-(x2,y2)");
        }


        checkFormat(startFromString, endFromString);

        this.start = startFromString;
        this.end = endFromString;



    }

    private void checkFormat(Coordinate start, Coordinate end){
        if(start.getX() > end.getX() || start.getY() > end.getY()){
            Coordinate temp = end;
            end = start;
            start = temp;
        }

        if(start.getX() != end.getX() && start.getY() != end.getY()){
            throw new WrongFormatException("Wrong format for wallString, try (x1,y1)-(x2,y2)");
        }


        this.start = start;
        this.end = end;

    }




    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
