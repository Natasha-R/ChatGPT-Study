package thkoeln.st.st2praktikum.exercise;

public class Obstacle {

    private Vector2D start;
    private Vector2D end;


    public Obstacle(Vector2D pos1, Vector2D pos2) {
        if(pos1!=null && pos2!=null) {
            this.start = pos1;
            this.end = pos2;
            sortiere();
            keinFehlerDiagonal();
        }
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        if(obstacleString!=null) {
            String[] twoVectors = obstacleString.split("-");
            if(twoVectors[0]!=null && twoVectors[1]!=null) {
            if(checkBracketsSyntax(twoVectors[0])) {
                start = new Vector2D(twoVectors[0]);
            }
                if(checkBracketsSyntax(twoVectors[1])) {
                    end = new Vector2D(twoVectors[1]);
                }
            }
            else {
                throw new RuntimeException();
            }
        }
        else{
            throw new RuntimeException();
        }
        sortiere();
        keinFehlerDiagonal();
    }

    private void keinFehlerDiagonal(){
            if (!start.getX().equals(end.getX()) && !start.getY().equals(end.getY())) throw new RuntimeException();
    }

    private void sortiere(){
            if(start.getY()>end.getY() || start.getX()>end.getX()){
                Vector2D pZwischenspeicher=start;
                start=end;
                end=pZwischenspeicher;
            }
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    private boolean checkBracketsSyntax(String pCommand){
        if(pCommand.charAt(pCommand.length()-1)==')' && pCommand.charAt(0)=='(')return true;
        throw new RuntimeException();
    }
}
