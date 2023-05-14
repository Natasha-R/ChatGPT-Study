package thkoeln.st.st2praktikum.exercise;

public class Obstacle {

    private Vector2D start;
    private Vector2D end;


    public Obstacle(Vector2D pos1, Vector2D pos2) {

        if (pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY())
            throw new IllegalArgumentException("A obstacle must be either vertical or horizontal");

        if (pos1.getX() + pos1.getY() > pos2.getX() + pos2.getY())
        {
            this.start = pos2;
            this.end = pos1;
        }
        else
        {
            this.start = pos1;
            this.end = pos2;
        }
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        if (obstacleString.contains("-"))
        {
            String splitVectors[] = obstacleString.split("-");
            if (splitVectors.length != 2)
                throw new IllegalArgumentException("The obstacleString needs to contain exactly two vectors");
            if (!Vector2D.validString(splitVectors[0]))
                throw new IllegalArgumentException("The first input is not a valid vector");
            if (!Vector2D.validString(splitVectors[1]))
                throw new IllegalArgumentException("The last input is not a valid vector");

            Vector2D firstVector = Vector2D.fromString(splitVectors[0]);
            Vector2D lastVector = Vector2D.fromString(splitVectors[1]);

            if (firstVector.getX() != lastVector.getX() && firstVector.getY() != lastVector.getY())
                throw new IllegalArgumentException("A obstacle must be either vertical or horizontal");

            if (firstVector.getX() + firstVector.getY() > lastVector.getX() + lastVector.getY())
            {
                this.start = lastVector;
                this.end = firstVector;
            }
            else
            {
                this.start = firstVector;
                this.end = lastVector;
            }

        }
        else throw new IllegalArgumentException("The Input is not a valid it needs to be (Integer,Integer)-(Integer,Integer) but was " + obstacleString);
    }

    public Obstacle fromString(String obstacleString)
    {
        if (obstacleString.contains("-"))
        {
            String splitVectors[] = obstacleString.split("-");
            if (splitVectors.length != 2)
                throw new IllegalArgumentException("The obstacleString needs to contain exactly two vectors");
            if (!Vector2D.validString(splitVectors[0]))
                throw new IllegalArgumentException("The first input is not a valid vector");
            if (!Vector2D.validString(splitVectors[1]))
                throw new IllegalArgumentException("The last input is not a valid vector");

            Vector2D firstVector = Vector2D.fromString(splitVectors[0]);
            Vector2D lastVector = Vector2D.fromString(splitVectors[1]);

            return new Obstacle(firstVector, lastVector);
        }
        else throw new IllegalArgumentException("The Input is not a valid it needs to be (Integer,Integer)-(Integer,Integer) but was " + obstacleString);
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }
}
