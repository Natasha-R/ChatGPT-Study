package thkoeln.st.st2praktikum.exercise;

public class Wall {

    private Vector2D start;
    private Vector2D end;


    public Wall(Vector2D pos1, Vector2D pos2) {




        if(pos1.getX()== pos2.getX() && pos1.getY()== pos2.getY()){
            throw new RuntimeException("gleiche Vectoren");
        }
        if(pos1.getX()!= pos2.getX() && pos1.getY()!= pos2.getY()){
            throw new RuntimeException("Diagonal");
        }

        this.start = pos1;
        this.end = pos2;

        if(pos1.getX()< pos2.getX() || pos1.getY()< pos2.getY()){

            this.start = pos1;
            this.end =pos2;
        }else{
            this.start=pos2;
            this.end=pos1;
        }

    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {


        String output[]=wallString.split("-");


        this.start=new Vector2D(output[0]);
        this.end=new Vector2D(output[1]);

    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }
}
