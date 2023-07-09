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
    private List<IDrawingElement> drawingElements = new ArrayList<>();

    public Canvas( Integer w, Integer h ) {
        this.name = CanvasHandler.DEFAULT_CANVAS_NAME;
        this.Width = w;
        this.Height = h;
    }

    @Override
    public void addDrawingElement( IDrawingElement drawingElement ) {
        if ( drawingElement == null ) {
            throw new BauzeichnerException("DrawingElement must not be null");
        }
        else {
            drawingElements.add(drawingElement);
            drawingElement.setCanvas(this);
        }
    }

    @Override
    public List<IDrawingElement> getNeighboursOf( IDrawingElement drawingElement ) {
        if ( drawingElement == null ) throw new BauzeichnerException("drawingElement must not be null");
        List<IDrawingElement> neighbours = new ArrayList<>();
        for ( IDrawingElement element : drawingElements ) {
            if ( element != drawingElement ) {
                neighbours.add( element );
            }
        }
        return neighbours;
    }

    public Integer getWidth() {
        return Width;
    }

    public Integer getHeight() {
        return Height;
    }

    public List<IDrawingElement> getDrawingElements() {
        return drawingElements;
    }
}
