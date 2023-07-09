package thkoeln.archilab.bauzeichner20.solution.drawingelement.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.archilab.bauzeichner20.BauzeichnerException;
import thkoeln.archilab.bauzeichner20.solution.canvas.domain.Canvas;
import thkoeln.archilab.bauzeichner20.solution.canvas.domain.DirectionType;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static thkoeln.archilab.bauzeichner20.solution.canvas.domain.DirectionType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Door extends DrawingElement {
    private String doorMaterial = "wood";

    public Door( Integer xBottomLeft, Integer yBottomLeft, Integer width, Integer height ) {
        super( xBottomLeft, yBottomLeft, width, height );
    }


    public void move( DirectionType direction, Integer distance ) {
        super.move( direction, distance );
        if ( xBottomLeft < 100 ) throw new BauzeichnerException( "Door must be 1m away from left canvas edge" );
        if ( canvas != null && (xBottomLeft + width > canvas.getWidth() + 100) )
            throw new BauzeichnerException( "Door must be 1m away from right canvas edge" );
    }
}
