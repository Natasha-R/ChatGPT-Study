package thkoeln.archilab.bauzeichner20.solution.canvas.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.archilab.bauzeichner20.BauzeichnerException;
import thkoeln.archilab.bauzeichner20.solution.canvas.application.CanvasService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Canvas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private Integer width;
    private Integer height;

    @OneToMany
    private List<Drawable> drawables = new ArrayList<>();

    public Canvas( Integer w, Integer h ) {
        this.name = CanvasService.DEFAULT_CANVAS_NAME;
        this.width = w;
        this.height = h;
    }

    public void addDrawingElement( Drawable drawable ) {
        if ( drawable == null ) {
            throw new BauzeichnerException("DrawingElement must not be null");
        }
        else {
            drawables.add(drawable);
            drawable.setCanvas(this);
        }
    }


    public List<Drawable> getNeighboursOf( Drawable drawable ) {
        if ( drawable == null ) throw new BauzeichnerException("drawingElement must not be null");
        List<Drawable> neighbours = new ArrayList<>();
        for ( Drawable element : drawables ) {
            if ( element != drawable ) {
                neighbours.add( element );
            }
        }
        return neighbours;
    }

}
