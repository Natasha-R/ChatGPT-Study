package thkoeln.st.st2praktikum.exercise;

public class Barrier {

    private Vector2D start;
    private Vector2D end;


    public Barrier(Vector2D pos1, Vector2D pos2) {
        checkIfBarriersAreDiagonal(pos1,pos2);
        if(pos1.getX()+pos1.getY()<=pos2.getX()+pos2.getY()){
            this.start = pos1;
            this.end = pos2;
        }else {
            this.start = pos2;
            this.end = pos1;
        }

    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        if(barrierString.matches("^\\([0-9]+,[0-9]+\\)-\\([0-9]+,[0-9]+\\)$")) {
            String[] barrierParam = barrierString.split("-");
            checkIfBarriersAreDiagonal(new Vector2D(barrierParam[0]),new Vector2D(barrierParam[1]));
            Vector2D pos1 = new Vector2D(barrierParam[0]);
            Vector2D pos2 = new Vector2D(barrierParam[1]);
            if(pos1.getX()+pos1.getY()<=pos2.getX()+pos2.getY()){
                this.start = pos1;
                this.end = pos2;
            }else {
                this.start = pos2;
                this.end = pos1;
            }
        }
        else throw new IllegalArgumentException("barrierString did not match Format. String: " + barrierString);
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    private void checkIfBarriersAreDiagonal(Vector2D start, Vector2D end) {
        if (start.getX() != end.getX() && start.getY() != end.getY()) throw new IllegalArgumentException("Vector2Ds cant be diagonal to each other!");
    }
}
