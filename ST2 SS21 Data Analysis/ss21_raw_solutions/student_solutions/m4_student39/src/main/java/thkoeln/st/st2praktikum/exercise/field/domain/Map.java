package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
@Getter
abstract class Map extends AbstractEntity{

    @ElementCollection( targetClass = Vector2D.class, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    protected List<Vector2D> listOfBlockingObjects = new ArrayList<>();
    @Transient
    protected List<TransportTechnology> listOfTransportTechnologies = new ArrayList<>();

    protected Integer height;
    protected Integer width;

    public abstract Connection getConnection(Vector2D sourcePosition, UUID desiredMapId);

    public abstract Integer checkNorthDirection(Integer xFromMovable, Integer yFromMovable, Integer steps);
    public abstract Integer checkWestDirection(Integer xFromMovable, Integer yFromMovable, Integer steps);
    public abstract Integer checkSouthDirection(Integer xFromMovable, Integer yFromMovable, Integer steps);
    public abstract Integer checkEastDirection(Integer xFromMovable, Integer yFromMovable, Integer steps);

    public abstract boolean isNotBlocked(Integer xPosition, Integer yPosition);

    public boolean removeBlocking(Vector2D position) {
        return listOfBlockingObjects.remove(position);
    }
    public boolean addBlockingObject(Vector2D position) {
        if(isNotBlocked(position.getX(), position.getY())) {
            listOfBlockingObjects.add(position);
            return true;
        }
        return false;
    }

    public boolean checkPositionOnMap(Vector2D position){
        return position.getX() < width && position.getY() < height;
    }
    public void addTransportTechnology(TransportTechnology transportTechnology){
        if(!listOfTransportTechnologies.contains(transportTechnology))
            listOfTransportTechnologies.add(transportTechnology);
    }
}
