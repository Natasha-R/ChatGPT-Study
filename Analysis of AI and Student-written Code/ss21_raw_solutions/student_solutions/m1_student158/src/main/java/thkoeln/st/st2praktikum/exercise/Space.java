package thkoeln.st.st2praktikum.exercise;

import java.util.*;

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
    public Boolean verticalTileFree(Position position, Integer step){
        Integer x = position.getX()+step;
        Integer y = position.getY();
        if(x==getHeight()||x<0){
            return false;
        }
        for (Wall wall : walls){
            if(wall.isVertical()){
                continue;
            }
            Integer wallStart = wall.getStartY();
            Integer wallEnd = wall.getEndY();
            for (int i=wallStart;i<wallEnd;i++){
                if(x==wall.getStartX() && y==i){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Boolean horizontalTileFree(Position position, Integer step){
        Integer x = position.getX();
        Integer y = position.getY()+step;
        if(y==getWidth() || y<0){
            return false;
        }
        for (Wall wall : walls){
            if(wall.isHorizontal()){
                continue;
            }
            Integer wallStart = wall.getStartX();
            Integer wallEnd = wall.getEndX();
            for (int i=wallStart;i<wallEnd;i++){
                if(y==wall.getStartY() && x==i){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void addWall(Wall wall){
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
