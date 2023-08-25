package thkoeln.st.st2praktikum.exercise.space.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties
public class Space extends AbstractEntity{

    @Column
    private Integer height;

    private Integer width;

    private Boolean defaultPositionFree = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @Embedded
    private List<Wall> walls = new ArrayList<>();

    @OneToMany
    private List<Connection> connections = new ArrayList<>();

    public static Space fromShit(Integer height, Integer width){
        return new Space(height, width);
    }

    public Space() {
    }

    private Space(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }

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

    public void addWall(Wall wall){
        if(width<wall.getStart().getX() || width<wall.getEnd().getX() ||
                height<wall.getStart().getY() || height<wall.getEnd().getY()){
            throw new IllegalArgumentException("Wall coordinates are out of bounds");
        }
        walls.add(wall);
    }

    public void addConnection(Connection connection){
        connections.add(connection);
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public List<Connection> getConnections(){
        return connections;
    }

    public Boolean isDefaultPositionFree(){
        return defaultPositionFree;
    }

    public void setDefaultPositionFree(Boolean isFree){
        defaultPositionFree = isFree;
    }

}
