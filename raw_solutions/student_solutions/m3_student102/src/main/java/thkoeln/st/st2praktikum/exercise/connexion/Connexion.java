package thkoeln.st.st2praktikum.exercise.connexion;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

//@Entity
@Getter
@Setter
public class Connexion {

   // @Id
    private  UUID connectionID=UUID.randomUUID();

   // @Embedded
    private Point pointSource;
    //@Embedded
    private  Point pointDestination;
    private  UUID idSource;
    private  UUID idDestination;
    private  UUID transportCategoryId;

    public Connexion(){}

    public Connexion(Point pointSource, Point pointDestination, UUID idSource, UUID idDestination, UUID transportCategoryId) {
        this.pointSource = pointSource;
        this.pointDestination = pointDestination;
        this.idSource = idSource;
        this.idDestination = idDestination;
        this.transportCategoryId = transportCategoryId;
    }
}
