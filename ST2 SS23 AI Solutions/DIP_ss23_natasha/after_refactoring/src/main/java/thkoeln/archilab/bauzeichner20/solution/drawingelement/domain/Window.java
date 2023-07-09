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
public class Window extends DrawingElement {

    private Integer windowNumberOfGlazing = 2;

    public Window( Integer xBottomLeft, Integer yBottomLeft, Integer width, Integer height ) {
        super( xBottomLeft, yBottomLeft, width, height );
    }

}
