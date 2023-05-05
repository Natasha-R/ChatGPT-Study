package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
public class Tile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Getter @Setter
    private Boolean north;
    @Getter @Setter
    private Boolean east;
    @Getter @Setter
    private Boolean south;
    @Getter @Setter
    private Boolean west;

    @Getter @Setter
    @Transient
    private transient MiningMachine content;

    @Getter @Setter
    @Transient
    private transient Connection connection;

    public Tile(boolean north, boolean east, boolean south, boolean west){
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    @Override
    public String toString() {
        var returnString = "";

        if(north != null)
            returnString += "_";
        else
            returnString += " ";

        if(west != null)
            returnString += "|";
        else
            returnString += " ";

        if(content != null)
            returnString += "█";
        else
            returnString += "░";

        if(east != null)
            returnString += "|";
        else
            returnString += " ";

        if(south != null)
            returnString += "_";
        else
            returnString += " ";

        return returnString;
    }

    public boolean isOccupied() {
        return (content != null);
    }

    public boolean isNorthWalkable(){
        return north == null;
    }
    public boolean isEastWalkable(){
        return east == null;
    }
    public boolean isSouthWalkable(){
        return south == null;
    }
    public boolean isWestWalkable(){
        return west == null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
