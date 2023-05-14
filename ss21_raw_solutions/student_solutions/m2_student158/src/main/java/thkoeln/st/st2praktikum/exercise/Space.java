package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Space extends AbstractEntity implements SpaceInterface {

    private Integer height;

    private Integer width;

    private Boolean defaultPositionFree = true;

    private List<Wall> walls = new ArrayList<>();

    private List<Connection> connections = new ArrayList<>();

    public Space(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public Boolean verticalTileFree(Coordinate coordinate, Integer step){
        Integer x = coordinate.getX()+step;
        Integer y = coordinate.getY();
        if(x==getHeight()||x<0){
            return false;
        }
        for (Wall wall : walls){
            if(wall.isVertical()){
                continue;
            }
            Integer wallStart = wall.getStart().getY();
            Integer wallEnd = wall.getEnd().getY();
            for (int i=wallStart;i<wallEnd;i++){
                if(x==wall.getStart().getX() && y==i){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Boolean horizontalTileFree(Coordinate coordinate, Integer step){
        Integer x = coordinate.getX();
        Integer y = coordinate.getY()+step;
        if(y==getWidth() || y<0){
            return false;
        }
        for (Wall wall : walls){
            if(wall.isHorizontal()){
                continue;
            }
            Integer wallStart = wall.getStart().getX();
            Integer wallEnd = wall.getEnd().getX();
            for (int i=wallStart;i<wallEnd;i++){
                if(y==wall.getStart().getY() && x==i){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void addWall(Wall wall){
        if(width<wall.getStart().getX() || width<wall.getEnd().getX() ||
                height<wall.getStart().getY() || height<wall.getEnd().getY()){
            throw new IllegalArgumentException("Wall coordinates are out of bounds");
        }
        walls.add(wall);
    }

    @Override
    public void addConnection(Connection connection){
        connections.add(connection);
    }

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public Integer getWidth() {
        return width;
    }

    @Override
    public List<Connection> getConnections(){
        return connections;
    }

    @Override
    public Boolean isDefaultPositionFree(){
        return defaultPositionFree;
    }

    @Override
    public void setDefaultPositionFree(Boolean isFree){
        defaultPositionFree = isFree;
    }

}
