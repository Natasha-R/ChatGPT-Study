package thkoeln.st.st2praktikum.exercise;

public class Wall {

    private String start;

    private String end;

    public Wall(String input) {
        String range = input.replace("(", "").replace(")", "");
        this.start = range.split("-")[0];
        this.end = range.split("-")[1];
    }

    public Integer getStartX(){
        return Integer.parseInt(start.split(",")[0]);
    }

    public Integer getStartY(){
        return Integer.parseInt(start.split(",")[1]);
    }

    public Integer getEndX(){
        return Integer.parseInt(end.split(",")[0]);
    }

    public Integer getEndY(){
        return Integer.parseInt(end.split(",")[1]);
    }

    public Boolean isVertical(){
        return getStartY() == getEndY();
    }

    public Boolean isHorizontal(){
        return getStartX() == getEndX();
    }
}
