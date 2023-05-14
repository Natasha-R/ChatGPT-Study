package thkoeln.st.st2praktikum.exercise;

public class Wall {

    private Point start;
    private Point end;


    public Wall(Point pos1, Point pos2) {
        if(pos1.getX() == pos2.getX() || pos1.getY() == pos2.getY()){
            if(pos1.getX() < pos2.getX() || pos1.getY() < pos2.getY()){
                this.start = pos1;
                this.end = pos2;
            }else{
                this.start = pos2;
                this.end = pos1;
            }
        }else{
            throw new RuntimeException("Walls should be vertical or horizontal");
        }
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        if(wallString.matches("\\({1}\\d+\\,\\d+\\){1}\\-\\({1}\\d+\\,\\d+\\){1}")){
            String[] parts = wallString.split("-");
            Point first = new Point(parts[0]);
            Point second = new Point(parts[1]);
            if(first.getX() == second.getX() || first.getY() == second.getY()){
                if(first.getX() < second.getX() || first.getY() < second.getY()){
                    this.start = first;
                    this.end = second;
                }else{
                    this.start = second;
                    this.end = first;
                }
            }else{
                throw new RuntimeException("Walls should be vertical or horizontal");
            }
        }else{
            throw new RuntimeException("Wrong Syntax");
        }
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
