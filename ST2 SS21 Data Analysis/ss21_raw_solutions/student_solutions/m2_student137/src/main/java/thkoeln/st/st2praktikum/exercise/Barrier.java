package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public class Barrier {

    private Vector2D start;
    private Vector2D end;

    public Barrier(Vector2D pos1, Vector2D pos2) {

        if(pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY()) {
            throw new UnsupportedOperationException();
        } else {

            if(pos1.getX() < pos2.getX() || pos1.getY() < pos2.getY()){

                this.start = pos1;
                this.end = pos2;

            } else {
                this.start = pos2;
                this.end = pos1;
            }
        }
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {

        // split start and end values at the dash
        String[] Split = barrierString.split("-");
        ArrayList<String> totalSplit = new ArrayList<>();

        // store split values in new arraylist
        totalSplit.add(Split[0]); // starting part with bracket
        totalSplit.add(Split[1]); // end part with bracket

        String firstVector = totalSplit.get(0);
        String secondVector = totalSplit.get(1);


        if (firstVector.isEmpty()  || secondVector.isEmpty()) {

            // throw exception in case any vector is not given
            throw new UnsupportedOperationException();

        }
        else{

            // if no error occures - create new vetors from strings
            Vector2D startVec = new Vector2D(firstVector);
            Vector2D endVec = new Vector2D(secondVector);
            if(startVec.getX() != endVec.getX() && startVec.getY() != endVec.getY()) {
                throw new UnsupportedOperationException();
            } else {

                if(startVec.getX() < endVec.getX() || startVec.getY() < endVec.getY()){

                    this.start = startVec;
                    this.end = endVec;

                } else {
                    this.start = endVec;
                    this.end = startVec;
                }

            }



        }
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }
}
