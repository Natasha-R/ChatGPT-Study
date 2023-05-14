package thkoeln.st.st2praktikum.exercise.transportcategory.domain;


import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import java.util.UUID;

@Getter
@Setter
public class Connection {

    // @Id
    private UUID connectionID=UUID.randomUUID();

    // @Embedded
    private Point pointSource;
    //@Embedded
    private  Point pointDestination;
    private  UUID idSource;
    private  UUID idDestination;
    private  UUID transportCategoryId;

    public Connection(){}


}
