package thkoeln.archilab.bauzeichner20.solution;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import thkoeln.archilab.bauzeichner20.BauzeichnerException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Canvas implements ICanvas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private Integer Width;
    private Integer Height;

    @OneToMany
    private List<DrawingElement> drawingElements = new ArrayList<>();

    public Canvas( Integer w, Integer h ) {
        this.name = CanvasHandler.DEFAULT_CANVAS_NAME;
        this.Width = w;
        this.Height = h;
    }

    public void addDrawingElement( DrawingElement drawingElement ) {
        if ( drawingElement == null ) {
            throw new BauzeichnerException("DrawingElement must not be null");
        }
        else {
            drawingElements.add(drawingElement);
            drawingElement.setCanvas(this);
        }
    }


    public List<DrawingElement> getNeighboursOf( DrawingElement drawingElement ) {
        if ( drawingElement == null ) throw new BauzeichnerException("drawingElement must not be null");
        List<DrawingElement> neighbours = new ArrayList<>();
        for ( DrawingElement element : drawingElements ) {
            if ( element != drawingElement ) {
                neighbours.add( element );
            }
        }
        return neighbours;
    }

}
